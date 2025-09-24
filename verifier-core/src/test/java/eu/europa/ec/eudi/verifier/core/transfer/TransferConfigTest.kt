package eu.europa.ec.eudi.verifier.core.transfer

import io.mockk.every
import io.mockk.mockk
import org.multipaz.mdoc.connectionmethod.MdocConnectionMethod
import org.multipaz.mdoc.connectionmethod.MdocConnectionMethodBle
import org.multipaz.util.UUID
import kotlin.test.*

class TransferConfigTest {

    @Test
    fun `test builder with single engagement method`() {
        val mockConnectionMethod = mockk<MdocConnectionMethod>()
        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, mockConnectionMethod)
        }

        assertEquals(1, config.engagementMethods.size)
        assertTrue(config.engagementMethods.containsKey(TransferConfig.EngagementMethod.QR))
        assertEquals(1, config.engagementMethods[TransferConfig.EngagementMethod.QR]?.size)
    }

    @Test
    fun `test builder with multiple engagement methods`() {
        val mockConnectionMethod1 = mockk<MdocConnectionMethod>()
        val mockConnectionMethod2 = mockk<MdocConnectionMethod>()

        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, mockConnectionMethod1)
            addEngagementMethod(TransferConfig.EngagementMethod.NFC, mockConnectionMethod2)
        }

        assertEquals(2, config.engagementMethods.size)
        assertTrue(config.engagementMethods.containsKey(TransferConfig.EngagementMethod.QR))
        assertTrue(config.engagementMethods.containsKey(TransferConfig.EngagementMethod.NFC))
    }

    @Test
    fun `test builder with multiple connection methods for single engagement`() {
        val mockConnectionMethod1 = mockk<MdocConnectionMethod>()
        val mockConnectionMethod2 = mockk<MdocConnectionMethod>()

        val config = TransferConfig {
            addEngagementMethod(
                TransferConfig.EngagementMethod.QR,
                mockConnectionMethod1,
                mockConnectionMethod2
            )
        }

        assertEquals(1, config.engagementMethods.size)
        assertEquals(2, config.engagementMethods[TransferConfig.EngagementMethod.QR]?.size)
    }

    @Test
    fun `test BLE settings configuration`() {
        val mockBle = mockk<MdocConnectionMethodBle>()
        every { mockBle.supportsPeripheralServerMode } returns true
        every { mockBle.supportsCentralClientMode } returns true
        every { mockBle.peripheralServerModeUuid } returns UUID.randomUUID()
        every { mockBle.centralClientModeUuid } returns UUID.randomUUID()

        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, mockBle)
            useBleL2CAP(true)
            clearBleCacheOnDisconnect(true)
        }

        assertTrue(config.bleUseL2CAP)
        assertTrue(config.bleClearCache)
    }

    @Test
    fun `test default BLE settings`() {
        val mockBle = mockk<MdocConnectionMethodBle>()
        every { mockBle.supportsPeripheralServerMode } returns true
        every { mockBle.supportsCentralClientMode } returns true
        every { mockBle.peripheralServerModeUuid } returns UUID.randomUUID()
        every { mockBle.centralClientModeUuid } returns UUID.randomUUID()

        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, mockBle)
        }

        assertFalse(config.bleUseL2CAP)
        assertFalse(config.bleClearCache)
    }

    @Test
    fun `test empty engagement methods throws exception`() {
        assertFailsWith<IllegalStateException>("No engagement methods configured") {
            TransferConfig { }
        }
    }

    @Test
    fun `test invalid BLE configuration throws exception`() {
        val invalidBleMock = mockk<MdocConnectionMethodBle>()
        every { invalidBleMock.supportsPeripheralServerMode } returns false
        every { invalidBleMock.supportsCentralClientMode } returns false

        assertFailsWith<IllegalStateException>(
            "For BLE connections, at least one mode (Peripheral Server or Central Client) must be supported."
        ) {
            TransferConfig {
                addEngagementMethod(TransferConfig.EngagementMethod.QR, invalidBleMock)
            }
        }
    }

    @Test
    fun `test peripheral server mode without UUID throws exception`() {
        val invalidBleMock = mockk<MdocConnectionMethodBle>()
        every { invalidBleMock.supportsPeripheralServerMode } returns true
        every { invalidBleMock.supportsCentralClientMode } returns false
        every { invalidBleMock.peripheralServerModeUuid } returns null

        assertFailsWith<IllegalStateException>(
            "peripheralServerModeUuid must be provided when supportsPeripheralServerMode is true."
        ) {
            TransferConfig {
                addEngagementMethod(TransferConfig.EngagementMethod.QR, invalidBleMock)
            }
        }
    }

    @Test
    fun `test central client mode without UUID throws exception`() {
        val invalidBleMock = mockk<MdocConnectionMethodBle>()
        every { invalidBleMock.supportsPeripheralServerMode } returns false
        every { invalidBleMock.supportsCentralClientMode } returns true
        every { invalidBleMock.centralClientModeUuid } returns null

        assertFailsWith<IllegalStateException>(
            "centralClientModeUuid must be provided when supportsCentralClientMode is true."
        ) {
            TransferConfig {
                addEngagementMethod(TransferConfig.EngagementMethod.QR, invalidBleMock)
            }
        }
    }

    @Test
    fun `test valid BLE configuration with both modes`() {
        val validBleMock = mockk<MdocConnectionMethodBle>()
        every { validBleMock.supportsPeripheralServerMode } returns true
        every { validBleMock.supportsCentralClientMode } returns true
        every { validBleMock.peripheralServerModeUuid } returns UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        every { validBleMock.centralClientModeUuid } returns UUID.fromString("987fcdeb-89ab-12d3-a456-426614174000")

        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, validBleMock)
        }

        assertNotNull(config.engagementMethods[TransferConfig.EngagementMethod.QR])
    }

    @Test
    fun `test overwriting engagement method`() {
        val mockMethod1 = mockk<MdocConnectionMethod>()
        val mockMethod2 = mockk<MdocConnectionMethod>()

        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, mockMethod1)
            addEngagementMethod(TransferConfig.EngagementMethod.QR, mockMethod2)
        }

        assertEquals(1, config.engagementMethods.size)
        assertEquals(1, config.engagementMethods[TransferConfig.EngagementMethod.QR]?.size)
        assertSame(mockMethod2, config.engagementMethods[TransferConfig.EngagementMethod.QR]?.first())
    }

    @Test
    fun `test addEngagementMethod with List parameter`() {
        val connectionMethods = listOf<MdocConnectionMethod>(mockk(), mockk())

        val config = TransferConfig {
            addEngagementMethod(TransferConfig.EngagementMethod.QR, connectionMethods)
        }

        assertEquals(2, config.engagementMethods[TransferConfig.EngagementMethod.QR]?.size)
    }
}
