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
import android.content.Context
import android.nfc.NfcAdapter
import android.os.Build
import androidx.core.content.ContextCompat
import com.android.identity.android.mdoc.deviceretrieval.VerificationHelper
import com.android.identity.android.mdoc.transport.DataTransportOptions
import eu.europa.ec.eudi.verifier.core.request.DeviceRequest
import eu.europa.ec.eudi.verifier.core.request.Request
import eu.europa.ec.eudi.verifier.core.response.DeviceResponse
import org.multipaz.crypto.Algorithm
import org.multipaz.mdoc.connectionmethod.MdocConnectionMethod
import org.multipaz.mdoc.request.DeviceRequestGenerator
import org.multipaz.mdoc.response.DeviceResponseParser
import org.multipaz.mdoc.role.MdocRole
import java.util.concurrent.Executor

class TransferManagerImpl(
    private val context: Context,
    private val config: TransferConfig,
) : TransferManager {

    private var transferEventListener : TransferEvent.Listener? = null

    private var verificationHelper: VerificationHelper? = null

    private val responseListener = object : VerificationHelper.Listener {

        override fun onDeviceConnected() {
            transferEventListener?.onEvent(TransferEvent.Connected)
        }

        override fun onDeviceDisconnected(transportSpecificTermination: Boolean) {
            transferEventListener?.onEvent(TransferEvent.Disconnected)
            //TODO maybe we need to cleanup
        }

        override fun onDeviceEngagementReceived(connectionMethods: List<MdocConnectionMethod>) {

            transferEventListener?.onEvent(TransferEvent.Connecting)

            val availableMdocConnectionMethods = MdocConnectionMethod.disambiguate(
                connectionMethods,
                MdocRole.MDOC_READER
            )

            if (availableMdocConnectionMethods.isNotEmpty()) {
                verificationHelper?.connect(availableMdocConnectionMethods.first())
            } else {
                // not sure when this happens or how to handle it
                throw IllegalStateException("No mdoc connection method selected.")
            }
        }


        override fun onError(error: Throwable) {
            transferEventListener?.onEvent(TransferEvent.Error(error))
            stopSession()
        }

        override fun onMoveIntoNfcField() {
            TODO("Not yet implemented")
        }

        override fun onReaderEngagementReady(readerEngagement: ByteArray) {
            TODO("Not yet implemented")
        }

        override fun onResponseReceived(deviceResponseBytes: ByteArray) {
            verificationHelper?.let { verification ->
                val parser = DeviceResponseParser(deviceResponseBytes, verification.sessionTranscript)
                parser.setEphemeralReaderKey(verification.eReaderKey)
                transferEventListener?.onEvent(
                    TransferEvent.ResponseReceived(
                        DeviceResponse(
                            parser.parse(),
                            deviceResponseBytes
                        )
                    )
                )
            } ?: throw IllegalStateException("Verification is null")

            stopSession()
        }
    }

    /**
     * Registers a listener to receive transfer events.
     * @param listener The listener to add.
     */
    override fun addListener(listener: TransferEvent.Listener) {
        transferEventListener = listener
    }

    /**
     * Unregisters a listener from receiving transfer events.
     * @param listener The listener to remove.
     */
    override fun removeListener(listener: TransferEvent.Listener) {
        transferEventListener = null
    }

    override fun startQRDeviceEngagement(qrCode: String) {
        verificationHelper = VerificationHelper.Builder(
            context,
            responseListener,
            context.mainExecutor()
        ).setDataTransportOptions(
            DataTransportOptions.Builder()
                .setBleUseL2CAP(config.bleUseL2CAP)
                .setBleClearCache(config.bleClearCache)
                .build()
        ).build()
        // This is not required ove BLE !! this will be set in enableNFCDeviceEngagement
        // Αν αφορα τον 1 απο τους 2 τροπους NFC, Negotiated or Static
//            .setNegotiatedHandoverConnectionMethods(
//                config.engagementMethods.get(TransferConfig.EngagementMethod.QR)!!
//            ).build()
        verificationHelper?.setDeviceEngagementFromQrCode(qrCode)
    }

    override fun enableNFCDeviceEngagement(nfcAdapter: NfcAdapter, activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun disableNFCDeviceEngagement() {
        TODO("Not yet implemented")
    }

    override fun sendRequest(request: Request) {
        // Use DeviceRequestGenerator to generate a DeviceRequest bytes
        // then send it using verificationHelper.sendRequest()
        if (request is DeviceRequest) {
            verificationHelper?.let { verification ->
                val requestGenerator = DeviceRequestGenerator(verification.sessionTranscript).apply {
                    request.docRequests.forEach { doc ->
                        addDocumentRequest(
                            docType = doc.docType,
                            itemsToRequest = doc.itemsRequest,
                            readerKeyCertificateChain = null,
                            requestInfo = null,
                            readerKey = null,
                            signatureAlgorithm = Algorithm.UNSET
                        )
                    }
                }
                verification.sendRequest(requestGenerator.generate())
                transferEventListener?.onEvent(TransferEvent.RequestSent)
            }
        } else {
            throw IllegalArgumentException("Unsupported request type: ${request::class.simpleName}")
        }
    }

    override fun stopSession() {
        verificationHelper?.disconnect()
        verificationHelper = null
        transferEventListener = null
    }

    private fun Context.mainExecutor(): Executor {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mainExecutor
        } else {
            ContextCompat.getMainExecutor(context)
        }
    }
}