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
package eu.europa.ec.eudi.verifier.core.response

import eu.europa.ec.eudi.verifier.core.DataElementIdentifier
import eu.europa.ec.eudi.verifier.core.DocType
import eu.europa.ec.eudi.verifier.core.Namespace
import eu.europa.ec.eudi.verifier.core.parse.CborParserUtil
import kotlinx.datetime.Instant
import org.multipaz.mdoc.response.DeviceResponseParser


data class DocumentClaims(
    val docType: DocType,
    val claims: Map<Namespace, Map<DataElementIdentifier, Any?>>
)

enum class DeviceAuthMethod {
    ECDSA, MAC;

    companion object {
        operator fun invoke(signatureUsed: Boolean) = if (signatureUsed) ECDSA else MAC
    }
}

data class DocumentValidity(
    val docType: DocType,
    val isDeviceSignatureValid: Boolean,
    val isIssuerSignatureValid: Boolean,
    val isDataIntegrityIntact: Boolean,
    val deviceAuthMethod: DeviceAuthMethod,
    val msoValidity: MsoValidity,
    val elementMsoResults: List<ElementMsoResult>
) {
    companion object {
        operator fun invoke(document: DeviceResponseParser.Document): DocumentValidity {
            return DocumentValidity(
                docType = document.docType,
                isDeviceSignatureValid = document.deviceSignedAuthenticated,
                isIssuerSignatureValid = document.issuerSignedAuthenticated,
                isDataIntegrityIntact = document.numIssuerEntryDigestMatchFailures == 0,
                deviceAuthMethod = DeviceAuthMethod(document.deviceSignedAuthenticatedViaSignature),
                msoValidity = MsoValidity(
                    signed = document.validityInfoSigned,
                    validFrom = document.validityInfoValidFrom,
                    validUntil = document.validityInfoValidUntil
                ),
                elementMsoResults = document.issuerNamespaces.flatMap { ns ->
                    document.getIssuerEntryNames(ns).map { elem ->
                        ElementMsoResult(
                            identifier = elem,
                            namespace = ns,
                            digestMatched = document.getIssuerEntryDigestMatch(ns, elem)
                        )
                    }
                }
            )
        }
    }
}

data class MsoValidity(
    val signed: Instant,
    val validFrom: Instant,
    val validUntil: Instant
)

data class ElementMsoResult(
    val namespace: String,
    val identifier: String,
    val digestMatched: Boolean
)

/**
 * Represents a generic response in the verifier core system.
 * Used as a marker interface for different types of responses.
 */
interface Response

/**
 * Represents the response received from a device during the verification process.
 *
 * @property deviceResponse The parsed device response object.
 * @property deviceResponseBytes The raw bytes of the device response.
 */
class DeviceResponse(
    val deviceResponse: DeviceResponseParser.DeviceResponse,
    val deviceResponseBytes: ByteArray
) : Response {

    val documentsClaims: List<DocumentClaims> by lazy {
        // Iterate through each Document provided by the DeviceResponseParser
        deviceResponse.documents.map { doc ->
            val docType = doc.docType

            // Build the outer map: Namespace -> (Map of Elements)
            val namespacesMap = doc.issuerNamespaces.associateWith { ns ->

                // Build the inner map: DataElementIdentifier -> Parsed Value
                doc.getIssuerEntryNames(ns).associateWith { elementIdentifier ->

                    // Get the raw bytes for the element
                    val bytes = doc.getIssuerEntryData(ns, elementIdentifier)

                    CborParserUtil.parse(bytes)
                }
            }

            DocumentClaims(docType, namespacesMap)
        }
    }

    val documentsValidity: List<DocumentValidity> by lazy {
        deviceResponse.documents.map { DocumentValidity(it) }
    }
}
