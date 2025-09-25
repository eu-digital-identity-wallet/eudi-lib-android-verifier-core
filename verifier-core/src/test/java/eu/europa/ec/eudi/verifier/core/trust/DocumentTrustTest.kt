package eu.europa.ec.eudi.verifier.core.trust

import io.mockk.*
import kotlin.test.*
import org.multipaz.trustmanagement.TrustManager
import org.multipaz.trustmanagement.TrustResult
import org.multipaz.mdoc.response.DeviceResponseParser
import kotlin.time.Instant
import kotlinx.coroutines.test.runTest

class DocumentTrustTest {
    @Test
    fun `TrustManager isDocumentTrusted calls verify with correct arguments`() = runTest {
        val trustManager = mockk<TrustManager>()
        val document = mockk<DeviceResponseParser.Document>()
        val certs = listOf(mockk<org.multipaz.crypto.X509Cert>())
        val atTime = Instant.fromEpochMilliseconds(123456789)
        val expectedResult = mockk<TrustResult>()

        every { document.issuerCertificateChain.certificates } returns certs
        coEvery { trustManager.verify(certs, atTime) } returns expectedResult

        val result = trustManager.isDocumentTrusted(document, atTime)

        coVerify { trustManager.verify(certs, atTime) }
        assertEquals(expectedResult, result)
    }
}