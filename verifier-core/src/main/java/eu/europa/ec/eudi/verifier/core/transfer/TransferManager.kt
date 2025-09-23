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

import android.app.Activity
import android.nfc.NfcAdapter
import eu.europa.ec.eudi.verifier.core.request.DeviceRequest
import eu.europa.ec.eudi.verifier.core.request.Request

/**
 * Interface for managing the transfer process between verifier and holder devices.
 * Provides methods to initiate device engagement, send requests, and manage listeners for transfer events.
 */
interface TransferManager {
    /**
     * Registers a listener to receive transfer events.
     * @param listener The listener to add.
     */
    fun addListener(listener: TransferEvent.Listener)

    /**
     * Unregisters a listener from receiving transfer events.
     * @param listener The listener to remove.
     */
    fun removeListener(listener: TransferEvent.Listener)

    /**
     * Starts device engagement using a QR code.
     * @param qrCode The QR code string to initiate engagement.
     */
    fun startQRDeviceEngagement(qrCode: String)

    /**
     * Enables NFC device engagement using the provided NFC adapter and activity context.
     * @param nfcAdapter The NFC adapter to use.
     * @param activity The activity context for NFC operations.
     */
    fun enableNFCDeviceEngagement(nfcAdapter: NfcAdapter, activity: Activity)

    /**
     * Disables NFC device engagement.
     */
    fun disableNFCDeviceEngagement()

    /**
     * Sends a request to the holder device.
     * @param request The request to send.
     */
    fun sendRequest(request: DeviceRequest)

    /**
     * Stops the current transfer session.
     */
    fun stopSession()
}