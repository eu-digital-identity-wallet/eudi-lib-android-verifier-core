package eu.europa.ec.eudi.verifier.core.statium

import eu.europa.ec.eudi.statium.VerifyStatusListTokenJwtSignature
import io.ktor.client.HttpClient
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class DocumentStatusResolverBuilderTest {

    private lateinit var mockVerifySignature: VerifyStatusListTokenJwtSignature
    private lateinit var mockHttpClient: HttpClient

    @Before
    fun setup() {
        mockVerifySignature = mockk()
        mockHttpClient = mockk()
    }

    @Test
    fun `builder should initialize with default values`() {
        val builder = DocumentStatusResolver.Builder()

        assertIs<VerifyStatusListTokenJwtSignature>(builder.verifySignature)
        assertEquals(Duration.ZERO, builder.allowedClockSkew)
    }

    @Test
    fun `builder should apply custom values using individual setters`() {
        val builder = DocumentStatusResolver.Builder()

        builder.verifySignature = mockVerifySignature
        builder.ktorHttpClient = mockHttpClient
        builder.allowedClockSkew = 5.minutes

        assertSame(mockVerifySignature, builder.verifySignature)
        assertSame(mockHttpClient, builder.ktorHttpClient)
        assertEquals(5.minutes, builder.allowedClockSkew)
    }

    @Test
    fun `builder should apply custom values using fluent API`() {
        val builder = DocumentStatusResolver.Builder()
            .withVerifySignature(mockVerifySignature)
            .withKtorHttpClient(mockHttpClient)
            .withAllowedClockSkew(10.minutes)

        assertSame(mockVerifySignature, builder.verifySignature)
        assertSame(mockHttpClient, builder.ktorHttpClient)
        assertEquals(10.minutes, builder.allowedClockSkew)
    }

    @Test
    fun `build should create DocumentStatusResolverImpl with correct parameters`() {
        val resolver = DocumentStatusResolver.Builder()
            .withVerifySignature(mockVerifySignature)
            .withKtorHttpClient(mockHttpClient)
            .withAllowedClockSkew(15.minutes)
            .build()

        assertIs<DocumentStatusResolverImpl>(resolver)
        assertSame(mockVerifySignature, resolver.verifySignature)
        assertSame(mockHttpClient, resolver.ktorHttpClient)
        assertEquals(15.minutes, resolver.allowedClockSkew)
    }

    @Test
    fun `invoke with lambda should create properly configured resolver`() = runTest {
        val resolver = DocumentStatusResolver {
            verifySignature = mockVerifySignature
            ktorHttpClient = mockHttpClient
            allowedClockSkew = 5.minutes
        }

        assertIs<DocumentStatusResolverImpl>(resolver)
        assertSame(mockVerifySignature, resolver.verifySignature)
        assertSame(mockHttpClient, resolver.ktorHttpClient)
        assertEquals(5.minutes, resolver.allowedClockSkew)
    }

    @Test
    fun `invoke factory method should create resolver with default values`() {
        val resolver = DocumentStatusResolver(
            verifySignature = mockVerifySignature
        )

        assert(resolver is DocumentStatusResolverImpl)
    }

    @Test
    fun `invoke factory method should create resolver with custom values`() {
        val resolver = DocumentStatusResolver(
            verifySignature = mockVerifySignature,
            ktorHttpClient = mockHttpClient,
            allowedClockSkew = 3.minutes
        )

        assertIs<DocumentStatusResolverImpl>(resolver)
        assertSame(mockVerifySignature, resolver.verifySignature)
        assertSame(mockHttpClient, resolver.ktorHttpClient)
        assertEquals(3.minutes, resolver.allowedClockSkew)
    }
}