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
package eu.europa.ec.eudi.verifier.core

import android.content.Context
import eu.europa.ec.eudi.verifier.core.internal.LogPrinterImpl
import eu.europa.ec.eudi.verifier.core.logging.Logger
import eu.europa.ec.eudi.verifier.core.statium.DocumentStatusResolver
import eu.europa.ec.eudi.verifier.core.statium.VerifyStatusListTokenSignatureX5c
import eu.europa.ec.eudi.verifier.core.transfer.TransferManagerFactory
import eu.europa.ec.eudi.verifier.core.trust.DocumentTrust
import eu.europa.ec.eudi.verifier.core.trust.isDocumentTrusted
import kotlinx.coroutines.runBlocking
import org.multipaz.crypto.X509Cert
import org.multipaz.mdoc.response.DeviceResponseParser
import org.multipaz.storage.ephemeral.EphemeralStorage
import org.multipaz.trustmanagement.TrustManager
import org.multipaz.trustmanagement.TrustManagerLocal
import org.multipaz.trustmanagement.TrustMetadata
import org.multipaz.trustmanagement.TrustResult
import java.security.cert.X509Certificate
import kotlin.time.Instant
import org.multipaz.util.Logger as MultipazLogger

/**
 * Main entry point for the EUDI verifier API, combining transfer management and document status resolution.
 *
 * Provides methods to verify device responses and manage transfer events.
 */
interface EudiVerifier : TransferManagerFactory, DocumentStatusResolver, DocumentTrust {

    /** Configuration settings for the verifier. */
    val config: EudiVerifierConfig

    /** Custom Logger implementation. */
    val logger: Logger

    /** Manager responsible for trust verification of documentsClaims. */
    val trustManager: TrustManager

    /**
     * Implementation of [DocumentTrust.isDocumentTrusted] that delegates verification
     * to the verifier's [trustManager].
     *
     * @param document The document to verify trust for
     * @param atTime The instant at which to verify trust
     * @return A [TrustManager.TrustResult] indicating the trust status of the document
     */
    override suspend fun isDocumentTrusted(
        document: DeviceResponseParser.Document,
        atTime: Instant
    ): TrustResult {
        return trustManager.isDocumentTrusted(document, atTime)
    }

    companion object {

        private const val TAG = "EudiVerifier"

        /**
         * Creates a new [EudiVerifier] instance with the given context and configuration.
         *
         * @param context Android context for resource access.
         * @param config Configuration for the verifier.
         * @param extraConfiguration Optional lambda to perform additional builder configuration.
         * @return Configured [EudiVerifier] instance.
         */
        operator fun invoke(
            context: Context,
            config: EudiVerifierConfig,
            extraConfiguration: (Builder.() -> Unit)? = null,
        ): EudiVerifier {
            val builder = Builder(context, config)
            extraConfiguration?.invoke(builder)
            return builder.build()
        }
    }

    /**
     * Builder for creating an [EudiVerifier] with custom settings.
     *
     * @property context Android context used by the verifier implementation.
     * @property config Initial configuration values.
     */
    class Builder(
        private val context: Context,
        private val config: EudiVerifierConfig,
    ) {

        var logger: Logger? = null

        private var certificates: List<X509Certificate>? = null

        private var certificatesProvider: CertificatesProvider? = null

        private var documentStatusResolver: DocumentStatusResolver? = null

        /**
         * Configures the verifier to trust a pre-loaded list of X509Certificates.
         */
        fun trustedCertificates(certificatesProvided: List<X509Certificate>) = apply {
            certificates = certificatesProvided
        }

        /**
         * Configures the verifier to trust certificates provided by the given [CertificatesProvider].
         * The provider will be called during the build process to obtain the certificates.
         * If both this and [trustedCertificates] are used, the statically provided certificates
         * will take precedence.
         * @param provider The [CertificatesProvider] to fetch trusted certificates from.
         * @return this [Builder] instance
         */
        fun trustedCertificates(provider: CertificatesProvider) = apply {
            certificatesProvider = provider
        }

        /**
         * Configure with the given [Logger] to use for logging. If not set, the default logger will be used
         * which is configured with the [EudiVerifierConfig.configureLogging].
         *
         * @param logger the logger
         * @return this [Builder] instance
         */
        fun withLogger(logger: Logger) = apply { this.logger = logger }


        fun withDocumentStatusResolver(documentStatusResolver: DocumentStatusResolver) = apply {
            this.documentStatusResolver = documentStatusResolver
        }

        /**
         * Builds the [EudiVerifier] instance with the current builder settings.
         *
         * @return A new [EudiVerifier] ready for use.
         */
        fun build(): EudiVerifier {

            val loggerToUse = (this@Builder.logger ?: Logger(config)).also {
                MultipazLogger.logPrinter = LogPrinterImpl(it)
            }

            val certificatesToUse = certificates ?: runBlocking {
                certificatesProvider?.getCertificates()
            } ?: emptyList()

            val trustManager = TrustManagerLocal(
                storage = EphemeralStorage(),
                identifier = "EudiVerifier",
            ).apply {
                certificatesToUse.forEach { cert ->
                    val customCert = X509Cert(cert.encoded)
                    runBlocking {
                        addX509Cert(
                            certificate = customCert,
                            metadata = TrustMetadata(
                                displayName = customCert.subject.components["CN"]?.value
                                    ?: customCert.subject.name
                            )
                        )
                    }
                }
            }
            val verifyStatusListTokenSignature = VerifyStatusListTokenSignatureX5c(trustManager)
            val documentStatusResolverToUse = documentStatusResolver ?: DocumentStatusResolver {
                verifySignature = verifyStatusListTokenSignature
            }

            return EudiVerifierImpl(
                context = context,
                config = config,
                logger = loggerToUse,
                documentStatusResolver = documentStatusResolverToUse,
                trustManager = trustManager
            )
        }
    }
}