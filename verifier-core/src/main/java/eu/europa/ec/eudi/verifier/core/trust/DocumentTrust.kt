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

package eu.europa.ec.eudi.verifier.core.trust

import kotlin.time.Clock
import kotlin.time.Instant
import org.multipaz.crypto.X509Cert
import org.multipaz.mdoc.response.DeviceResponseParser
import org.multipaz.trustmanagement.TrustManager
import org.multipaz.trustmanagement.TrustResult

/**
 * Interface defining document trust verification operations.
 *
 * Implementations of this interface provide functionality for verifying
 * the trust status of documentsClaims against a trust framework.
 */
interface DocumentTrust {
    /**
     * Verifies if the provided document is trusted at the specified time.
     *
     * @param document The document to verify trust for
     * @param atTime The instant at which to verify trust; defaults to the current system time
     * @return A [TrustResult] indicating the trust status of the document
     */
    suspend fun isDocumentTrusted(
        document: DeviceResponseParser.Document,
        atTime: Instant = Clock.System.now()
    ): TrustResult
}

/**
 * Extension function that checks if a document is trusted using the TrustManager.
 *
 * @param document The document to verify trust for
 * @param atTime The instant at which to verify trust; defaults to the current system time
 * @return A [TrustResult] indicating the trust status of the document
 */
suspend fun TrustManager.isDocumentTrusted(
    document: DeviceResponseParser.Document,
    atTime: Instant = Clock.System.now()
): TrustResult {
    return verify(document.issuerCertificateChain.certificates, atTime)
}

/**
 * Data class representing issuer information.
 *
 * @property commonName The common name of the issuer
 * @property organization The organization of the issuer
 * @property country The country of the issuer
 */
data class IssuerInfo(
    val commonName: String,
    val organization: String?,
    val country: String?
) {
    companion object {
        /**
         * Creates an IssuerInfo object from an X509Cert.
         *
         * @param certificate The X509Cert from which to extract issuer information
         * @return An IssuerInfo object containing the extracted issuer information
         */
        operator fun invoke(certificate: X509Cert): IssuerInfo {
            val cn = certificate.subject.components["CN"]?.value ?: ""
            val o = certificate.subject.components["O"]?.value
            val c = certificate.subject.components["C"]?.value
            return IssuerInfo(commonName = cn, organization = o, country = c)
        }
    }
}

/**
 * Extension property that retrieves the issuer information from the trust chain.
 *
 * @return An IssuerInfo object containing the extracted issuer information, or null if no issuer information is available
 */
val TrustResult.issuerInfo: IssuerInfo?
    get() = trustChain?.certificates?.first()?.let { IssuerInfo(it) }
