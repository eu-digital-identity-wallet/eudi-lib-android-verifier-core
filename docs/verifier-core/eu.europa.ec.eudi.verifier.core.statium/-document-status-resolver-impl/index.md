//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[DocumentStatusResolverImpl](index.md)

# DocumentStatusResolverImpl

class [DocumentStatusResolverImpl](index.md)(verifySignature: VerifyStatusListTokenJwtSignature, allowedClockSkew: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html), ktorHttpClient: HttpClient) : [DocumentStatusResolver](../-document-status-resolver/index.md)

Default implementation of [DocumentStatusResolver](../-document-status-resolver/index.md) that validates document status against status list services.

This implementation:

- 
   Verifies signatures on status list tokens
- 
   Handles HTTP requests to status list endpoints
- 
   Processes status information according to the status list specification
- 
   Accounts for clock skew in time-based validations

#### Parameters

androidJvm

| | |
|---|---|
| verifySignature | A function to verify status list token signatures |
| allowedClockSkew | The maximum time difference allowed between local and server clocks |
| ktorHttpClient | A factory function to create HTTP clients for status list requests |

## Constructors

| | |
|---|---|
| [DocumentStatusResolverImpl](-document-status-resolver-impl.md) | [androidJvm]<br>constructor(verifySignature: VerifyStatusListTokenJwtSignature, allowedClockSkew: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html), ktorHttpClient: HttpClient) |

## Functions

| Name | Summary |
|---|---|
| [resolveStatus](resolve-status.md) | [androidJvm]<br>open suspend override fun [resolveStatus](resolve-status.md)(response: [DeviceResponse](../../eu.europa.ec.eudi.verifier.core.response/-device-response/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;Status&gt;&gt;<br>Resolves the status of all documentsClaims in the given device response. |
