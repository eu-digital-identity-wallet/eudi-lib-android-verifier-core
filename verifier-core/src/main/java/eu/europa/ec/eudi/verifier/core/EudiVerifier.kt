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
import eu.europa.ec.eudi.verifier.core.statium.DocumentStatusResolver
import eu.europa.ec.eudi.verifier.core.transfer.TransferManagerFactory
import eu.europa.ec.eudi.wallet.statium.VerifyStatusListTokenSignatureX5c
import kotlinx.datetime.Instant
import org.multipaz.mdoc.response.DeviceResponseParser
import org.multipaz.trustmanagement.TrustManager

/**
 * Main entry point for the EUDI verifier API, combining transfer management and document status resolution.
 *
 * Provides methods to verify device responses and manage transfer events.
 */
interface EudiVerifier : TransferManagerFactory, DocumentStatusResolver, DocumentTrust {

    /** Configuration settings for the verifier. */
    val config: EudiVerifierConfig

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
    override fun isDocumentTrusted(
        document: DeviceResponseParser.Document,
        atTime: Instant
    ): TrustManager.TrustResult {
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

        private var documentStatusResolver: DocumentStatusResolver? = null

        fun withDocumentStatusResolver(documentStatusResolver: DocumentStatusResolver) = apply {
            this.documentStatusResolver = documentStatusResolver
        }

        /**
         * Builds the [EudiVerifier] instance with the current builder settings.
         *
         * @return A new [EudiVerifier] ready for use.
         */
        fun build(): EudiVerifier {
            val trustManager = TrustManager()
            for (trustPoint in config.trustPoints) {
                trustManager.addTrustPoint(trustPoint)
            }
            val verifyStatusListTokenSignature = VerifyStatusListTokenSignatureX5c(trustManager)
            val documentStatusResolverToUse = documentStatusResolver ?: DocumentStatusResolver {
                verifySignature = verifyStatusListTokenSignature
            }

            return EudiVerifierImpl(
                context = context,
                config = config,
                documentStatusResolver = documentStatusResolverToUse,
                trustManager = trustManager
            )
        }
    }
}