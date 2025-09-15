/*
 * Copyright (c) 2025 European Commission
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.europa.ec.eudi.verifier.core.statium

import eu.europa.ec.eudi.statium.GetStatus
import eu.europa.ec.eudi.statium.GetStatusListToken
import eu.europa.ec.eudi.statium.Status
import eu.europa.ec.eudi.statium.StatusIndex
import eu.europa.ec.eudi.statium.StatusReference
import eu.europa.ec.eudi.statium.TokenStatusListSpec
import eu.europa.ec.eudi.statium.VerifyStatusListTokenJwtSignature
import eu.europa.ec.eudi.verifier.core.response.DeviceResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import org.multipaz.cbor.Cbor
import org.multipaz.cbor.DataItem
import org.multipaz.cbor.Tstr
import org.multipaz.cose.CoseSign1
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Default implementation of [DocumentStatusResolver] that validates document status
 * against status list services.
 *
 * This implementation:
 * - Verifies signatures on status list tokens
 * - Handles HTTP requests to status list endpoints
 * - Processes status information according to the status list specification
 * - Accounts for clock skew in time-based validations
 *
 * @param verifySignature A function to verify status list token signatures
 * @param allowedClockSkew The maximum time difference allowed between local and server clocks
 * @param ktorHttpClient A factory function to create HTTP clients for status list requests
 */
class DocumentStatusResolverImpl(
    internal val verifySignature: VerifyStatusListTokenJwtSignature,
    internal val allowedClockSkew: Duration,
    internal val ktorHttpClient: HttpClient,
) : DocumentStatusResolver {

    /**
     * Resolves the status of all documentsClaims in the given device response.
     *
     * @param response The device response containing CBOR-encoded document data
     * @return A list of [Result] objects with the resolved status of each document
     */
    @OptIn(ExperimentalTime::class)
    override suspend fun resolveStatus(response: DeviceResponse): List<Result<Status>> {
        return withContext(Dispatchers.IO) {
            // Extract the "documents" field from the CBOR-encoded response
            // This contains an array of document entries in the mDL format
            val documents = Cbor.decode(response.deviceResponseBytes)
                .asMap[Tstr("documents")]
                ?: throw IllegalArgumentException("Invalid device response: missing 'documents' field")

            // Extract issuerAuth bytes from each document
            // The issuerAuth field contains the signed COSE data from the document issuer
            // which includes status reference information
            val issuerAuthList = documents.asArray.map { document ->
                document.asMap[Tstr("issuerSigned")]
                    ?.asMap?.get(Tstr("issuerAuth"))
                    ?: throw IllegalArgumentException("Invalid device response: missing 'issuerAuth' in document")
            }

            // For each status reference, retrieve and verify the current status
            issuerAuthList.map { issuerAuth ->
                runCatching {
                    // Parse each issuerAuth to extract status references
                    // Status references contain the URI and index needed to check document status
                    val statusReference = statusReferenceFromIssuerAuth(issuerAuth)

                    // Create a JWT-based status list token retriever with verification capability
                    val getStatusListToken = GetStatusListToken.usingJwt(
                        clock = Clock.System,
                        httpClient = ktorHttpClient,
                        verifyStatusListTokenSignature = verifySignature,
                        allowedClockSkew = allowedClockSkew,
                    )

                    // Retrieve and validate the current status using the GetStatus utility
                    // This will fetch the status list token from the reference URI,
                    // verify its signature, and check the status at the specified index
                    with(GetStatus(getStatusListToken)) {
                        statusReference.currentStatus().getOrThrow()
                    }
                }
            }
        }
    }

    /**
     * Extracts a status reference from issuerAuth COSE_Sign1 data.
     *
     * This method:
     * 1. Parses the COSE_Sign1 structure from CBOR DataItem
     * 2. Extracts the payload and decodes the Mobile Security Object (MSO)
     * 3. Retrieves the status list URI and index from the status section
     *
     * @param issuerAuth The CBOR DataItem containing COSE_Sign1 data from a document's issuerAuth field
     * @return A StatusReference containing the URI and index for status checks
     * @throws IllegalArgumentException if required status information is missing
     */
    private fun statusReferenceFromIssuerAuth(issuerAuth: DataItem): StatusReference {
        val coseSign1 = CoseSign1.fromDataItem(issuerAuth)
        val payload = coseSign1.payload
        requireNotNull(payload) {
            "Missing payload in COSE_Sign1 structure"
        }

        val mso = Cbor.decode(Cbor.decode(payload).asTagged.asBstr)

//        Log.d("After Decode Payload", "$mso")

        val statusList = mso[TokenStatusListSpec.STATUS][TokenStatusListSpec.STATUS_LIST].asMap
        val uri = statusList[Tstr(TokenStatusListSpec.URI)]?.asTstr
            ?: throw IllegalArgumentException("Missing URI in status list")
        val idx = statusList[Tstr(TokenStatusListSpec.IDX)]?.asNumber?.toInt()
            ?: throw IllegalArgumentException("Missing index in status list")

        return StatusReference(
            uri = uri,
            index = StatusIndex(idx),
        )
    }
}