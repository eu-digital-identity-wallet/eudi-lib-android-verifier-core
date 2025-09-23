//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.trust](../index.md)/[DocumentTrust](index.md)/[isDocumentTrusted](is-document-trusted.md)

# isDocumentTrusted

[androidJvm]\
abstract suspend fun [isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html) = Clock.System.now()): TrustResult

Verifies if the provided document is trusted at the specified time.

#### Return

A TrustResult indicating the trust status of the document

#### Parameters

androidJvm

| | |
|---|---|
| document | The document to verify trust for |
| atTime | The instant at which to verify trust; defaults to the current system time |
