//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[DocumentStatusResolverImpl](index.md)/[DocumentStatusResolverImpl](-document-status-resolver-impl.md)

# DocumentStatusResolverImpl

[androidJvm]\
constructor(verifySignature: VerifyStatusListTokenSignature, allowedClockSkew: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html), ktorHttpClientFactory: () -&gt; HttpClient)

#### Parameters

androidJvm

| | |
|---|---|
| verifySignature | A function to verify status list token signatures |
| allowedClockSkew | The maximum time difference allowed between local and server clocks |
| ktorHttpClientFactory | A factory function to create HTTP clients for status list requests |
