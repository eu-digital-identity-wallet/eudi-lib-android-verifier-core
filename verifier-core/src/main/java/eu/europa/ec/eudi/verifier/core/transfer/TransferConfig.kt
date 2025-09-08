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

package eu.europa.ec.eudi.verifier.core.transfer

import org.multipaz.mdoc.connectionmethod.MdocConnectionMethod

/**
 * Configuration for the transfer process between verifier and holder devices.
 * Defines the available engagement methods and their associated connection methods.
 *
 * @property engagementMethods Map of engagement methods to their supported connection methods.
 */
class TransferConfig private constructor(
    val engagementMethods: Map<EngagementMethod, List<MdocConnectionMethod>>
) {

    /**
     * Enumeration of available device engagement methods.
     */
    enum class EngagementMethod {
        /**
         * QR code engagement method.
         */
        QR,

        /**
         * Near Field Communication engagement method.
         */
        NFC
    }

    /**
     * Builder class for creating instances of [TransferConfig].
     * Provides a fluent interface for configuration.
     */
    class Builder {
        private val engagementMethods = mutableMapOf<EngagementMethod, List<MdocConnectionMethod>>()

        /**
         * Adds an engagement method with its supported connection methods.
         *
         * @param method The engagement method to add.
         * @param connectionMethods The connection methods supported by this engagement method.
         * @return This builder instance for method chaining.
         */
        fun addEngagementMethod(
            method: EngagementMethod,
            vararg connectionMethods: MdocConnectionMethod
        ) = apply {
            engagementMethods.put(method, connectionMethods.toList())
        }

        /**
         * Adds an engagement method with its supported connection methods.
         *
         * @param method The engagement method to add.
         * @param connectionMethods The list of connection methods supported by this engagement method.
         * @return This builder instance for method chaining.
         */
        fun addEngagementMethod(
            method: EngagementMethod,
            connectionMethods: List<MdocConnectionMethod>
        ) = apply {
            engagementMethods.put(method, connectionMethods)
        }

        /**
         * Builds a [TransferConfig] instance with the current configuration.
         *
         * @return A new [TransferConfig] instance.
         * @throws IllegalStateException if no engagement methods are configured.
         */
        fun build(): TransferConfig {
            check(engagementMethods.isNotEmpty()) { "No engagement methods configured" }
            return TransferConfig(engagementMethods.toMap())
        }
    }

    companion object {
        /**
         * Creates a [TransferConfig] instance using the provided configuration lambda.
         *
         * @param configure Lambda with receiver for configuring the [Builder].
         * @return A new [TransferConfig] instance.
         */
        operator fun invoke(configure: Builder.() -> Unit): TransferConfig =
            Builder().apply(configure).build()
    }
}