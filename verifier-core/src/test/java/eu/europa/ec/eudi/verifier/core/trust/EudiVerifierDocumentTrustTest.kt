package eu.europa.ec.eudi.verifier.core.trust

import io.mockk.*
import kotlin.test.*
import kotlinx.coroutines.test.runTest
import org.multipaz.trustmanagement.TrustManager
import org.multipaz.trustmanagement.TrustResult
import org.multipaz.mdoc.response.DeviceResponseParser
import kotlin.time.Instant
import eu.europa.ec.eudi.verifier.core.EudiVerifier

class EudiVerifierDocumentTrustTest {
    @Test
    fun `isDocumentTrusted delegates to trustManager and returns result`() = runTest {
        val trustManager = mockk<TrustManager>()
        val document = mockk<DeviceResponseParser.Document>()
        val atTime = Instant.fromEpochMilliseconds(123456789)
        val expectedResult = mockk<TrustResult>()

        coEvery { trustManager.isDocumentTrusted(document, atTime) } returns expectedResult

        val verifier = mockk<EudiVerifier> {
            every { this@mockk.trustManager } returns trustManager
            coEvery { isDocumentTrusted(document, atTime) } coAnswers { trustManager.isDocumentTrusted(document, atTime) }
        }

        val result = verifier.isDocumentTrusted(document, atTime)

        coVerify { trustManager.isDocumentTrusted(document, atTime) }
        assertEquals(expectedResult, result)
    }
}