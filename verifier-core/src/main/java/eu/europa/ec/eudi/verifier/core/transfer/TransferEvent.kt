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

import eu.europa.ec.eudi.verifier.core.response.DeviceResponse
import eu.europa.ec.eudi.verifier.core.response.Response

interface Event

/**
 * Represents events that occur during the transfer process between verifier and holder devices.
 * Used to notify listeners about the state and results of the transfer session.
 */
sealed interface TransferEvent : Event {
    /**
     * Indicates that device engagement has been successfully completed.
     */
    data object DeviceEngagementCompleted : TransferEvent

    /**
     * Indicates that a connection attempt is in progress.
     */
    data object Connecting : TransferEvent

    /**
     * Indicates that a connection has been established.
     * @param transferManager The manager handling the transfer session.
     */
    data object Connected : TransferEvent

    /**
     * Indicates that a request has been sent to the holder device.
     */
    data object RequestSent : TransferEvent

    /**
     * Indicates that a response has been received from the holder device.
     * @param response The response received.
     */
    data class ResponseReceived(val response: DeviceResponse) : TransferEvent

    /**
     * Indicates that the connection has been disconnected.
     */
    data object Disconnected : TransferEvent

    /**
     * Indicates that an error has occurred during the transfer process.
     * @param error The exception describing the error.
     */
    data class Error(val error: Throwable) : TransferEvent

    /**
     * Listener interface for receiving transfer events.
     */
    fun interface Listener {
        /**
         * Called when a transfer event occurs.
         * @param event The event that occurred.
         */
        fun onEvent(event: TransferEvent)
    }
}

/*
e.g. usage:
data class CustomEvent(val message: String): Event
fun handleEvent(event: Event) {
    when (event) {
        is TransferEvent.Connecting -> println("Connecting")
        is TransferEvent.Error -> println("Error: ${event.error}")
        is CustomEvent -> println("Custom Event: ${event.message}")
        else -> println("Other event: $event")
    }
}
*/