//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.trust](../index.md)/[DocumentTrust](index.md)

# DocumentTrust

interface [DocumentTrust](index.md)

Interface defining document trust verification operations.

Implementations of this interface provide functionality for verifying the trust status of documentsClaims against a trust framework.

#### Inheritors

| |
|---|
| [EudiVerifier](../../eu.europa.ec.eudi.verifier.core/-eudi-verifier/index.md) |

## Functions

| Name | Summary |
|---|---|
| [isDocumentTrusted](is-document-trusted.md) | [androidJvm]<br>abstract suspend fun [isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html) = Clock.System.now()): TrustResult<br>Verifies if the provided document is trusted at the specified time. |
