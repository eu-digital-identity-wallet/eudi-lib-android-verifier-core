//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[DocumentStatusResolverImpl](index.md)/[DocumentStatusResolverImpl](-document-status-resolver-impl.md)

# DocumentStatusResolverImpl

[androidJvm]\
constructor(verifySignature: VerifyStatusListTokenJwtSignature, allowedClockSkew: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html), ktorHttpClient: HttpClient)

#### Parameters

androidJvm

| | |
|---|---|
| verifySignature | A function to verify status list token signatures |
| allowedClockSkew | The maximum time difference allowed between local and server clocks |
| ktorHttpClient | A factory function to create HTTP clients for status list requests |
