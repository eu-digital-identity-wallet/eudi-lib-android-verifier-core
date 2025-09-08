//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[DocumentTrust](index.md)

# DocumentTrust

interface [DocumentTrust](index.md)

Interface defining document trust verification operations.

Implementations of this interface provide functionality for verifying the trust status of documentsClaims against a trust framework.

#### Inheritors

| |
|---|
| [EudiVerifier](../-eudi-verifier/index.md) |

## Functions

| Name | Summary |
|---|---|
| [isDocumentTrusted](is-document-trusted.md) | [androidJvm]<br>abstract fun [isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: Instant = Clock.System.now()): TrustManager.TrustResult<br>Verifies if the provided document is trusted at the specified time. |
