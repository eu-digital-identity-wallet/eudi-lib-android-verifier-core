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

import eu.europa.ec.eudi.statium.Status
import eu.europa.ec.eudi.statium.VerifyStatusListTokenJwtSignature
import eu.europa.ec.eudi.verifier.core.response.DeviceResponse
import io.ktor.client.HttpClient
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Interface for resolving the status of documentsClaims in a device response.
 *
 * The status resolver performs validation against status list services to
 * determine if documentsClaims are valid, revoked, or suspended.
 */
interface DocumentStatusResolver {

    /**
     * Resolves the status of all documentsClaims in the given device response.
     *
     * This process includes:
     * 1. Extracting document data from the CBOR-encoded response
     * 2. Parsing issuerAuth data to find status references
     * 3. Retrieving and validating status list tokens
     * 4. Determining the current status for each document
     *
     * @param response The device response containing documentsClaims whose status needs to be resolved
     * @return A list of [Result] objects containing the [Status] of each document or an error
     */
    suspend fun resolveStatus(response: DeviceResponse): List<Result<Status>>

    companion object {

        /**
         * Creates an instance of [DocumentStatusResolver]
         *
         * @param ktorHttpClientFactory a factory function to create an [HttpClient]
         * @param verifySignature a function to verify the status list token signature
         * @param allowedClockSkew the allowed clock skew for the verification
         */
        operator fun invoke(
            verifySignature: VerifyStatusListTokenJwtSignature,
            ktorHttpClient: HttpClient = HttpClient(),
            allowedClockSkew: Duration = Duration.ZERO,
        ): DocumentStatusResolver {
            return DocumentStatusResolverImpl(
                verifySignature,
                allowedClockSkew,
                ktorHttpClient
            )
        }

        /**
         * Creates an instance of [DocumentStatusResolver] using a builder
         *
         * @param block a lambda function with a [Builder] as receiver to configure the resolver
         * @return a [DocumentStatusResolver] instance
         */
        operator fun invoke(block: Builder.() -> Unit): DocumentStatusResolver {
            return Builder().apply(block).build()
        }
    }

    /**
     * Builder for [DocumentStatusResolver]
     * It allows to set the parameters for the resolver it builds a [DocumentStatusResolverImpl]
     *
     * @property verifySignature a function to verify the status list token signature; default is [VerifyStatusListTokenSignature.x5c]
     * @property ktorHttpClientFactory a factory function to create an [HttpClient]; default is [HttpClient]
     * @property allowedClockSkew the allowed clock skew for the verification; default is [Duration.ZERO]
     * @property extractor an instance of [StatusReferenceExtractor] to extract the status reference from the document; default is [DefaultStatusReferenceExtractor]
     */
    class Builder {

        @OptIn(ExperimentalTime::class)
        var verifySignature: VerifyStatusListTokenJwtSignature = VerifyStatusListTokenJwtSignature { _, _ ->
            Result.success(Unit)
        }
        var ktorHttpClient: HttpClient = HttpClient()
        var allowedClockSkew: Duration = Duration.ZERO

        /**
         * Sets the function to verify the status list token signature
         * @param verifySignature a function to verify the status list token signature
         * @return the builder instance
         */
        fun withVerifySignature(verifySignature: VerifyStatusListTokenJwtSignature) = apply {
            this.verifySignature = verifySignature
        }

        /**
         * Sets the factory function to create an [HttpClient]
         * @param ktorHttpClientFactory a factory function to create an [HttpClient]
         * @return the builder instance
         */
        fun withKtorHttpClientFactory(ktorHttpClient: HttpClient) = apply {
            this.ktorHttpClient = ktorHttpClient
        }

        /**
         * Sets the allowed clock skew for the verification
         * @param allowedClockSkew the allowed clock skew for the verification
         * @return the builder instance
         */
        fun withAllowedClockSkew(allowedClockSkew: Duration) = apply {
            this.allowedClockSkew = allowedClockSkew
        }

        /**
         * Builds the [DocumentStatusResolver] instance
         */
        fun build(): DocumentStatusResolver {
            return DocumentStatusResolverImpl(
                verifySignature,
                allowedClockSkew,
                ktorHttpClient,
            )
        }
    }
}

