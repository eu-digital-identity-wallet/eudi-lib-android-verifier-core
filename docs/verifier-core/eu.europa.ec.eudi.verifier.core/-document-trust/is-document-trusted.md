//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[DocumentTrust](index.md)/[isDocumentTrusted](is-document-trusted.md)

# isDocumentTrusted

[androidJvm]\
abstract fun [isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: Instant = Clock.System.now()): TrustManager.TrustResult

Verifies if the provided document is trusted at the specified time.

#### Return

A TrustManager.TrustResult indicating the trust status of the document

#### Parameters

androidJvm

| | |
|---|---|
| document | The document to verify trust for |
| atTime | The instant at which to verify trust; defaults to the current system time |
