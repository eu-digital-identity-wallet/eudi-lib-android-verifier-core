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
import org.multipaz.mdoc.connectionmethod.MdocConnectionMethodBle

/**
 * Configuration for the transfer process between verifier and holder devices.
 * Defines the available engagement methods and their associated connection methods.
 *
 * @property engagementMethods Map of engagement methods to their supported connection methods.
 */
class TransferConfig private constructor(
    val engagementMethods: Map<EngagementMethod, List<MdocConnectionMethod>>,
    val bleUseL2CAP: Boolean,
    val bleClearCache: Boolean
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
        private var bleUseL2CAP: Boolean = false
        private var bleClearCache: Boolean = false

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
         * Sets the preference for use BLE L2CAP transmission profile.
         * Use L2CAP if supported by the OS and remote mdoc.
         * The default value for this is `false`.
         */
        fun useBleL2CAP(use: Boolean) = apply {
            bleUseL2CAP = use
        }

        /**
         * Sets whether to clear the BLE Service Cache before service discovery when acting as
         * a GATT Client.
         * The default value for this is `false`.
         */
        fun clearBleCacheOnDisconnect(clear: Boolean) = apply {
            bleClearCache = clear
        }

        /**
         * Builds a [TransferConfig] instance with the current configuration.
         * @return A new [TransferConfig] instance.
         * @throws IllegalStateException if no engagement methods are configured,
         * or if a BLE connection is configured incorrectly (e.g., no supported modes or missing UUIDs)
         */
        fun build(): TransferConfig {
            check(engagementMethods.isNotEmpty()) { "No engagement methods configured" }

            engagementMethods.values.flatten().forEach { connectionMethod ->
                if (connectionMethod is MdocConnectionMethodBle) {
                    check(connectionMethod.supportsPeripheralServerMode || connectionMethod.supportsCentralClientMode) {
                        "For BLE connections, at least one mode (Peripheral Server or Central Client) must be supported."
                    }

                    if (connectionMethod.supportsPeripheralServerMode) {
                        check(connectionMethod.peripheralServerModeUuid != null) {
                            "peripheralServerModeUuid must be provided when supportsPeripheralServerMode is true."
                        }
                    }

                    if (connectionMethod.supportsCentralClientMode) {
                        check(connectionMethod.centralClientModeUuid != null) {
                            "centralClientModeUuid must be provided when supportsCentralClientMode is true."
                        }
                    }
                }
            }

            return TransferConfig(
                engagementMethods.toMap(),
                bleUseL2CAP = bleUseL2CAP,
                bleClearCache = bleClearCache
            )
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