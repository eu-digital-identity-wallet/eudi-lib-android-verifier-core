package eu.europa.ec.eudi.verifier.core.transfer

import io.mockk.*
import kotlin.test.*
import android.content.Context
import com.android.identity.android.mdoc.deviceretrieval.VerificationHelper
import com.android.identity.android.mdoc.transport.DataTransportOptions
import eu.europa.ec.eudi.verifier.core.logging.Logger
import java.util.concurrent.Executor
import org.multipaz.mdoc.request.DeviceRequestGenerator
import eu.europa.ec.eudi.verifier.core.request.DeviceRequest
import eu.europa.ec.eudi.verifier.core.request.DocRequest

class TransferManagerImplTest {
    private lateinit var context: Context
    private lateinit var config: TransferConfig
    private lateinit var logger: Logger
    private lateinit var verificationHelper: VerificationHelper
    private lateinit var transferManager: TransferManagerImpl
    private lateinit var executor: Executor
    private lateinit var listener: TransferEvent.Listener
    private lateinit var verificationHelperFactory: (Context, VerificationHelper.Listener, Executor, DataTransportOptions) -> VerificationHelper
    private lateinit var capturedVerificationListener: VerificationHelper.Listener

    @BeforeTest
    fun setup() {
        context = mockk(relaxed = true)
        config = mockk {
            every { bleUseL2CAP } returns false
            every { bleClearCache } returns false
        }
        logger = mockk(relaxed = true)
        verificationHelper = mockk(relaxed = true)
        executor = mockk(relaxed = true)
        listener = mockk(relaxed = true)

        // Capture the VerificationHelper.Listener for use in tests
        verificationHelperFactory = { _, l, _, _ ->
            capturedVerificationListener = l
            verificationHelper
        }

        transferManager = TransferManagerImpl(
            context = context,
            config = config,
            logger = logger,
            verificationHelperFactory = verificationHelperFactory
        )
    }

    @Test
    fun `startQRDeviceEngagement creates verificationHelper and sets QR code`() {
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        verify {
            verificationHelper.setDeviceEngagementFromQrCode("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")
        }
    }

    @Test
    fun `stopSession cleans up resources`() {
        transferManager.addListener(listener)
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        transferManager.stopSession()

        verify {
            verificationHelper.disconnect()
        }

        // Simulate an event after stopSession
        capturedVerificationListener.onDeviceConnected()

        // Verify listener is not called after stopSession
        verify(exactly = 0) { listener.onEvent(any()) }
    }

    @Test
    fun `addListener sets transfer event listener`() {
        transferManager.addListener(listener)
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        // Simulate the callback from VerificationHelper
        capturedVerificationListener.onDeviceConnected()

        verify {
            listener.onEvent(any())
        }
    }

    @Test
    fun `removeListener removes transfer event listener`() {
        transferManager.addListener(listener)
        transferManager.removeListener(listener)
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        // Simulate the callback from VerificationHelper
        capturedVerificationListener.onDeviceConnected()

        verify(exactly = 0) {
            listener.onEvent(any())
        }
    }

    @Test
    fun `responseListener handles device connected event`() {
        transferManager.addListener(listener)
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        // Simulate the callback from VerificationHelper
        capturedVerificationListener.onDeviceConnected()

        verify {
            listener.onEvent(TransferEvent.Connected)
        }
    }

    @Test
    fun `responseListener handles device disconnected event`() {
        transferManager.addListener(listener)
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        capturedVerificationListener.onDeviceDisconnected(false)

        verify {
            listener.onEvent(TransferEvent.Disconnected)
        }
    }

    @Test
    fun `startQRDeviceEngagement with valid QR code does not throw`() {
        val validQr = "mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg"
        every { verificationHelper.setDeviceEngagementFromQrCode(validQr) } just Runs

        try {
            transferManager.startQRDeviceEngagement(validQr)
        } catch (e: Throwable) {
            fail("Expected no exception, but got: $e")
        }
    }

    @Test
    fun `startQRDeviceEngagement with garbage QR code triggers error event`() {
        transferManager.addListener(listener)
        val garbageQr = "hYS6QBAiABIVgg0Gzvq_N_tpQNv"

        val thrown = runCatching { transferManager.startQRDeviceEngagement(garbageQr) }.exceptionOrNull()
        assertNull(thrown, "startQRDeviceEngagement should not throw for invalid QR; error is async")

        // Simulate what the underlying helper would do
        val ex = IllegalArgumentException("Invalid QR")
        capturedVerificationListener.onError(ex)

        verify {
            listener.onEvent(match { it is TransferEvent.Error && it.error == ex })
        }
    }

    @Test
    fun `sendRequest sends correct bytes and triggers event`() {

        transferManager.addListener(listener)
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")
        val sessionTranscript = byteArrayOf(42, 24)
        val deviceRequestBytes = byteArrayOf(0xA0.toByte()) // valid CBOR: empty map
        val docRequest = mockk<DocRequest> {
            every { docType } returns "testDoc"
            every { itemsRequest } returns mapOf()
        }
        val deviceRequest = DeviceRequest(listOf(docRequest))
        every { verificationHelper.sessionTranscript } returns sessionTranscript

        mockkConstructor(DeviceRequestGenerator::class)
        every { anyConstructed<DeviceRequestGenerator>().generate() } returns deviceRequestBytes

        every { verificationHelper.sendRequest(deviceRequestBytes) } just Runs

        transferManager.sendRequest(deviceRequest)

        verify { verificationHelper.sendRequest(deviceRequestBytes) }
        verify { listener.onEvent(TransferEvent.RequestSent) }
    }
}