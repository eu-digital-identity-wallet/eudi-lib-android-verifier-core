//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[EudiVerifier](index.md)/[isDocumentTrusted](is-document-trusted.md)

# isDocumentTrusted

[androidJvm]\
open suspend override fun [isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html)): TrustResult

Implementation of [DocumentTrust.isDocumentTrusted](../../eu.europa.ec.eudi.verifier.core.trust/-document-trust/is-document-trusted.md) that delegates verification to the verifier's [trustManager](trust-manager.md).

#### Return

A TrustManager.TrustResult indicating the trust status of the document

#### Parameters

androidJvm

| | |
|---|---|
| document | The document to verify trust for |
| atTime | The instant at which to verify trust |
