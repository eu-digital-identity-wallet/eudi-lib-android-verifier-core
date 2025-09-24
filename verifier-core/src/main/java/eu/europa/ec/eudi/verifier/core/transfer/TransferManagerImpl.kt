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
import eu.europa.ec.eudi.verifier.core.logging.Logger
import eu.europa.ec.eudi.verifier.core.logging.d
import eu.europa.ec.eudi.verifier.core.logging.e
import eu.europa.ec.eudi.verifier.core.request.DeviceRequest
import eu.europa.ec.eudi.verifier.core.request.Request
import eu.europa.ec.eudi.verifier.core.response.DeviceResponse
import org.multipaz.cbor.Cbor
import org.multipaz.crypto.Algorithm
import org.multipaz.mdoc.connectionmethod.MdocConnectionMethod
import org.multipaz.mdoc.request.DeviceRequestGenerator
import org.multipaz.mdoc.response.DeviceResponseParser
import org.multipaz.mdoc.role.MdocRole
import java.util.concurrent.Executor

class TransferManagerImpl(
    private val context: Context,
    private val config: TransferConfig,
    var logger: Logger? = null,
    private val verificationHelperFactory: (Context, VerificationHelper.Listener, Executor, DataTransportOptions) -> VerificationHelper = { ctx, listener, executor, options ->
        VerificationHelper.Builder(ctx, listener, executor)
            .setDataTransportOptions(options)
            .build()
    }
) : TransferManager {

    private var transferEventListener : TransferEvent.Listener? = null

    private var verificationHelper: VerificationHelper? = null

    private val responseListener = object : VerificationHelper.Listener {

        override fun onDeviceConnected() {
            logger?.d(TAG, "Connection Established")
            transferEventListener?.onEvent(TransferEvent.Connected)
        }

        override fun onDeviceDisconnected(transportSpecificTermination: Boolean) {
            logger?.d(TAG, "Device Disconnected")
            transferEventListener?.onEvent(TransferEvent.Disconnected)
        }

        override fun onDeviceEngagementReceived(connectionMethods: List<MdocConnectionMethod>) {
            logger?.d(TAG, "Device Engagement Received")

            transferEventListener?.onEvent(TransferEvent.Connecting)

            val availableMdocConnectionMethods = MdocConnectionMethod.disambiguate(
                connectionMethods,
                MdocRole.MDOC_READER
            )

            if (availableMdocConnectionMethods.isNotEmpty()) {
                verificationHelper?.connect(availableMdocConnectionMethods.first())
            } else {
                onError(IllegalStateException("No mdoc connection method selected"))
                logger?.e(TAG, "No mdoc connection method selected")
            }
        }

        override fun onError(error: Throwable) {
            logger?.e(TAG, "Error: ${error.message}", error)
            transferEventListener?.onEvent(TransferEvent.Error(error))
            stopSession()
        }

        override fun onMoveIntoNfcField() {
            logger?.d(TAG, "Not implemented yet")
        }

        override fun onReaderEngagementReady(readerEngagement: ByteArray) {
            logger?.d(TAG, "Not implemented yet")
        }

        override fun onResponseReceived(deviceResponseBytes: ByteArray) {

            verificationHelper?.let { verification ->
                val parser = DeviceResponseParser(deviceResponseBytes, verification.sessionTranscript)
                parser.setEphemeralReaderKey(verification.eReaderKey)
                try {
                    val deviceResponse = DeviceResponse(
                        parser.parse(),
                        deviceResponseBytes
                    )
                    logger?.d(TAG, "ResponseReceived ${Cbor.toDiagnostics(deviceResponseBytes)}")
                    transferEventListener?.onEvent(
                        TransferEvent.ResponseReceived(deviceResponse)
                    )
                } catch (e: Exception) {
                    onError(e)
                }
            }

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

    /**
     * Starts device engagement using a QR code.
     * @param qrCode The QR code string.
     * @throws IllegalArgumentException if the QR code is invalid.
     */
    override fun startQRDeviceEngagement(qrCode: String) {
        logger?.d(TAG, "QR Engagement Request: $qrCode")

        val options = DataTransportOptions.Builder()
            .setBleUseL2CAP(config.bleUseL2CAP)
            .setBleClearCache(config.bleClearCache)
            .build()

        verificationHelper = verificationHelperFactory(
            context,
            responseListener,
            context.mainExecutor(),
            options
        )

        verificationHelper?.setDeviceEngagementFromQrCode(qrCode)
    }

    override fun enableNFCDeviceEngagement(nfcAdapter: NfcAdapter, activity: Activity) {
        logger?.d(TAG, "Not implemented yet")
    }

    override fun disableNFCDeviceEngagement() {
        logger?.d(TAG, "Not implemented yet")
    }

    override fun sendRequest(request: DeviceRequest) {
        logger?.d(TAG, "About to send Document Request")
        // Use DeviceRequestGenerator to generate a DeviceRequest bytes
        // then send it using verificationHelper.sendRequest()
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
            val deviceRequestBytes = requestGenerator.generate()
            logger?.d(TAG, "Request Doc ${Cbor.toDiagnostics(deviceRequestBytes)}")
            verification.sendRequest(deviceRequestBytes)
            transferEventListener?.onEvent(TransferEvent.RequestSent)
        }
    }

    override fun stopSession() {
        logger?.d(TAG, "Terminating current session")
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
    companion object {
        private const val TAG = "TransferManager"
        private const val RESPONSE = "response"
    }
}