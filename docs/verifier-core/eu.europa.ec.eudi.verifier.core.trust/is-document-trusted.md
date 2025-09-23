//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core.trust](index.md)/[isDocumentTrusted](is-document-trusted.md)

# isDocumentTrusted

[androidJvm]\
suspend fun TrustManager.[isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html) = Clock.System.now()): TrustResult

Extension function that checks if a document is trusted using the TrustManager.

#### Return

A TrustResult indicating the trust status of the document

#### Parameters

androidJvm

| | |
|---|---|
| document | The document to verify trust for |
| atTime | The instant at which to verify trust; defaults to the current system time |
