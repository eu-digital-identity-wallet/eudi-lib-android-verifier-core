package eu.europa.ec.eudi.verifier.core.transfer

import io.mockk.*
import kotlin.test.*
import android.content.Context
import com.android.identity.android.mdoc.deviceretrieval.VerificationHelper
import com.android.identity.android.mdoc.transport.DataTransportOptions
import eu.europa.ec.eudi.verifier.core.logging.Logger
import java.util.concurrent.Executor
import org.multipaz.cbor.Bstr
import org.multipaz.cbor.Cbor
import org.multipaz.mdoc.request.DeviceRequestGenerator
import org.multipaz.mdoc.request.DeviceRequestParser
import eu.europa.ec.eudi.verifier.core.request.DeviceRequest
import eu.europa.ec.eudi.verifier.core.request.DocRequest
import eu.europa.ec.eudi.verifier.core.request.EU_WRPRC_REQUEST_INFO_KEY
import eu.europa.ec.eudi.verifier.core.request.ReaderAuth
import kotlinx.coroutines.runBlocking
import org.multipaz.asn1.ASN1Integer
import org.multipaz.crypto.Algorithm
import org.multipaz.crypto.Crypto
import org.multipaz.crypto.X500Name
import org.multipaz.crypto.X509Cert
import org.multipaz.crypto.javaX509Certificate
import org.multipaz.securearea.software.SoftwareCreateKeySettings
import org.multipaz.securearea.software.SoftwareSecureArea
import org.multipaz.storage.ephemeral.EphemeralStorage
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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

    @AfterTest
    fun tearDown() {
        unmockkAll()
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

    @Test
    fun `sendRequest repeats the euWrprc in every ItemsRequest`() {
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        val sessionTranscript = Cbor.encode(Bstr(byteArrayOf(1, 2, 3)))
        every { verificationHelper.sessionTranscript } returns sessionTranscript
        val sentBytes = slot<ByteArray>()
        every { verificationHelper.sendRequest(capture(sentBytes)) } just Runs

        val wrprc = "eyJhbGciOiJFUzI1NiJ9.payload.signature".toByteArray()
        val deviceRequest = DeviceRequest(
            docRequests = listOf(
                DocRequest(
                    docType = "org.iso.18013.5.1.mDL",
                    itemsRequest = mapOf("org.iso.18013.5.1" to mapOf("family_name" to false))
                ),
                DocRequest(
                    docType = "eu.europa.ec.eudi.pid.1",
                    itemsRequest = mapOf("eu.europa.ec.eudi.pid.1" to mapOf("family_name" to false))
                )
            ),
            readerAuth = readerAuthFor(Algorithm.ESP256),
            registrationCertificate = wrprc
        )

        transferManager.sendRequest(deviceRequest)

        val parsed = DeviceRequestParser(sentBytes.captured, sessionTranscript).parse()
        assertEquals(2, parsed.docRequests.size)
        parsed.docRequests.forEach { docRequest ->
            val embedded = docRequest.requestInfo[EU_WRPRC_REQUEST_INFO_KEY]
            assertNotNull(embedded, "euWrprc must be present in every ItemsRequest")
            assertContentEquals(wrprc, Cbor.decode(embedded).asBstr)
        }
    }

    @Test
    fun `sendRequest rejects a registration certificate without reader authentication`() {
        val deviceRequest = DeviceRequest(
            docRequests = listOf(
                DocRequest(
                    docType = "org.iso.18013.5.1.mDL",
                    itemsRequest = mapOf("org.iso.18013.5.1" to mapOf("family_name" to false))
                )
            ),
            readerAuth = null,
            registrationCertificate = "eyJhbGciOiJFUzI1NiJ9.payload.signature".toByteArray()
        )

        assertFailsWith<IllegalArgumentException> {
            transferManager.sendRequest(deviceRequest)
        }
    }

    @Test
    fun `sendRequest omits the euWrprc requestInfo when no registration certificate is set`() {
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        val sessionTranscript = Cbor.encode(Bstr(byteArrayOf(1, 2, 3)))
        every { verificationHelper.sessionTranscript } returns sessionTranscript
        val sentBytes = slot<ByteArray>()
        every { verificationHelper.sendRequest(capture(sentBytes)) } just Runs

        val deviceRequest = DeviceRequest(
            listOf(
                DocRequest(
                    docType = "org.iso.18013.5.1.mDL",
                    itemsRequest = mapOf("org.iso.18013.5.1" to mapOf("family_name" to false))
                )
            )
        )

        transferManager.sendRequest(deviceRequest)

        val parsed = DeviceRequestParser(sentBytes.captured, sessionTranscript).parse()
        assertNull(parsed.docRequests.single().requestInfo[EU_WRPRC_REQUEST_INFO_KEY])
    }

    @Test
    fun `sendRequest signs the request so reader authentication verifies for every supported curve`() {
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        val sessionTranscript = Cbor.encode(Bstr(byteArrayOf(1, 2, 3)))
        every { verificationHelper.sessionTranscript } returns sessionTranscript
        val sentBytes = slot<ByteArray>()
        every { verificationHelper.sendRequest(capture(sentBytes)) } just Runs

        for (algorithm in listOf(Algorithm.ESP256, Algorithm.ESP384, Algorithm.ESP512)) {
            val deviceRequest = DeviceRequest(
                docRequests = listOf(
                    DocRequest(
                        docType = "org.iso.18013.5.1.mDL",
                        itemsRequest = mapOf("org.iso.18013.5.1" to mapOf("family_name" to false))
                    )
                ),
                readerAuth = readerAuthFor(algorithm)
            )

            transferManager.sendRequest(deviceRequest)

            val parsed = DeviceRequestParser(sentBytes.captured, sessionTranscript).parse()
            assertTrue(
                parsed.docRequests.single().readerAuthenticated,
                "reader authentication must verify for $algorithm"
            )
        }
    }

    @Test
    fun `sendRequest signs the request and carries the euWrprc together`() {
        transferManager.startQRDeviceEngagement("mdoc:owBjMS4wAYIB2BhYS6QBAiABIVgg0Gzvq_N_tpQNvbj_qUGmRmheJa1vMKi7mMTH2XDAibgiWCAB9U176w_O7UIvb3kKk5ZbrD3UquIlrWNy_lKwRZNO4AKBgwIBowD1AfQKUFJVDpvDxE3zruZUd4NpTLg")

        val sessionTranscript = Cbor.encode(Bstr(byteArrayOf(1, 2, 3)))
        every { verificationHelper.sessionTranscript } returns sessionTranscript
        val sentBytes = slot<ByteArray>()
        every { verificationHelper.sendRequest(capture(sentBytes)) } just Runs

        val wrprc = "eyJhbGciOiJFUzI1NiJ9.payload.signature".toByteArray()
        val deviceRequest = DeviceRequest(
            docRequests = listOf(
                DocRequest(
                    docType = "org.iso.18013.5.1.mDL",
                    itemsRequest = mapOf("org.iso.18013.5.1" to mapOf("family_name" to false))
                )
            ),
            readerAuth = readerAuthFor(Algorithm.ESP256),
            registrationCertificate = wrprc
        )

        transferManager.sendRequest(deviceRequest)

        val parsed = DeviceRequestParser(sentBytes.captured, sessionTranscript).parse()
        val docRequest = parsed.docRequests.single()
        assertTrue(docRequest.readerAuthenticated, "reader authentication must verify")
        assertContentEquals(
            wrprc,
            Cbor.decode(docRequest.requestInfo.getValue(EU_WRPRC_REQUEST_INFO_KEY)).asBstr
        )
    }

    /**
     * Builds a [ReaderAuth] from a freshly generated reader key and a matching self-signed
     * certificate on the given [curve], for exercising the request-signing path.
     */
    @OptIn(ExperimentalTime::class)
    private fun readerAuthFor(algorithm: Algorithm): ReaderAuth = runBlocking {
        val secureArea = SoftwareSecureArea.create(EphemeralStorage())
        val keyInfo = secureArea.createKey(
            alias = "reader",
            createKeySettings = SoftwareCreateKeySettings.Builder().setAlgorithm(algorithm).build()
        )
        val caKey = Crypto.createEcPrivateKey(keyInfo.publicKey.curve)
        val validFrom = Instant.fromEpochSeconds(Clock.System.now().epochSeconds)
        val validUntil = Instant.fromEpochSeconds(validFrom.epochSeconds + 24L * 60 * 60)
        val cert = X509Cert.Builder(
            publicKey = keyInfo.publicKey,
            signingKey = caKey,
            signatureAlgorithm = caKey.curve.defaultSigningAlgorithm,
            serialNumber = ASN1Integer(1),
            subject = X500Name.fromName("CN=Test Reader"),
            issuer = X500Name.fromName("CN=Test Reader CA"),
            validFrom = validFrom,
            validUntil = validUntil
        ).build()
        ReaderAuth(
            secureArea = secureArea,
            keyAlias = "reader",
            certificateChain = listOf(cert.javaX509Certificate)
        )
    }
}