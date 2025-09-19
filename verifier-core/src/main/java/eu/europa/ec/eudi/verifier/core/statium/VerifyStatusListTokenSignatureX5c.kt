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

import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.SignedJWT
import eu.europa.ec.eudi.statium.VerifyStatusListTokenJwtSignature
import eu.europa.ec.eudi.verifier.core.logging.Logger
import eu.europa.ec.eudi.verifier.core.logging.d
import eu.europa.ec.eudi.verifier.core.logging.e
import kotlin.time.Instant
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.multipaz.crypto.X509CertChain
import org.multipaz.crypto.javaX509Certificate
import org.multipaz.trustmanagement.TrustManager
import java.security.interfaces.ECPublicKey
import java.security.interfaces.RSAPublicKey

/**
 * Implementation of [VerifyStatusListTokenSignature] that verifies status list token signatures
 * using X.509 certificate chains from the token's x5c header.
 *
 * This implementation:
 * - Supports JWT format tokens with x5c headers
 * - Verifies signatures using RSA or ECDSA algorithms
 * - Validates the certificate chain against the provided [TrustManager]
 *
 * @property trustManager The trust manager used to validate certificate chains.
 */
class VerifyStatusListTokenSignatureX5c(
    val trustManager: TrustManager,
    var logger: Logger? = null,
) : VerifyStatusListTokenJwtSignature {

    /**
     * Verifies the signature of a status list token.
     *
     * The verification process:
     * 1. Parses the JWT token
     * 2. Extracts the certificate chain from the x5c header
     * 3. Gets the public key from the first certificate
     * 4. Creates an appropriate verifier based on the key type (RSA or ECDSA)
     * 5. Verifies the token's signature
     * 6. Validates the certificate chain using the trust manager
     *
     * @param statusListToken The JWT format status list token to verify
     * @param format The format of the status list token (currently only JWT is supported)
     * @param at The time at which the verification is performed
     * @return A [Result] containing Unit on success, or an exception on failure:
     *         - [IllegalArgumentException] if the format is not JWT
     *         - [IllegalStateException] if the x5c header is missing or for unsupported key types
     *         - [SignatureVerificationError] if signature verification fails or certificate chain is untrusted
     */
    override suspend fun invoke(
        statusListToken: String,
        at: Instant,
    ): Result<Unit> = runCatching {
        // currently only JWT format is supported
        // Add support for CWT format
//        require(format == StatusListTokenFormat.JWT) {
//            "Unsupported format: $format"
//        }

        logger?.d(TAG, "Verifying using JWT")

        val signedJwt = SignedJWT.parse(statusListToken)

        val chain = signedJwt.header?.x509CertChain
            ?.map { it.toString() }
            ?.let { Json.encodeToJsonElement(it) }
            ?.let { X509CertChain.fromX5c(it) }

        val publicKey = chain?.certificates?.firstOrNull()?.javaX509Certificate?.publicKey
            ?: run {
                val exception = IllegalStateException("Missing x5c in JWT header")
                logger?.e(TAG, "Missing x5c in JWT header", exception)
                throw exception
            }

        val verifier = when (publicKey) {
            is ECPublicKey -> ECDSAVerifier(publicKey)
            is RSAPublicKey -> RSASSAVerifier(publicKey)
            else -> {
                val exception = IllegalStateException("Unsupported public key type: ${publicKey.javaClass.name}")
                logger?.e(TAG, "Unsupported public key type", exception)
                throw exception
            }
        }

        val isVerified = signedJwt.verify(verifier)

        if (!isVerified) {
            logger?.e(TAG, "Verification failed", SignatureVerificationError())
            throw SignatureVerificationError()
        }

        val result = trustManager.verify(chain.certificates)
        if (!result.isTrusted) {
            logger?.e(TAG, "Document is Not Trusted", SignatureVerificationError())
            throw SignatureVerificationError()
        }

        if (result.error != null) {
            logger?.e(TAG, "There was an error ", IllegalStateException(result.error))
            throw IllegalStateException(result.error)
        }
    }

    companion object {
        private const val TAG = "StatusListSignature"
    }
}

/**
 * Custom exception for signature verification errors.
 * Thrown when a token's signature cannot be verified or its certificate chain is untrusted.
 */
class SignatureVerificationError : IllegalStateException()