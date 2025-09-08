//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core](index.md)/[isDocumentTrusted](is-document-trusted.md)

# isDocumentTrusted

[androidJvm]\
fun TrustManager.[isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: Instant = Clock.System.now()): TrustManager.TrustResult

Extension function that checks if a document is trusted using the TrustManager.

#### Return

A TrustManager.TrustResult indicating the trust status of the document

#### Parameters

androidJvm

| | |
|---|---|
| document | The document to verify trust for |
| atTime | The instant at which to verify trust; defaults to the current system time |
