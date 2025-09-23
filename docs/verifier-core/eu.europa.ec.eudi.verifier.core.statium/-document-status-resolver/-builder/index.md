//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../../index.md)/[DocumentStatusResolver](../index.md)/[Builder](index.md)

# Builder

[androidJvm]\
class [Builder](index.md)

Builder for [DocumentStatusResolver](../index.md) It allows to set the parameters for the resolver it builds a [DocumentStatusResolverImpl](../../-document-status-resolver-impl/index.md)

## Constructors

| | |
|---|---|
| [Builder](-builder.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [allowedClockSkew](allowed-clock-skew.md) | [androidJvm]<br>var [allowedClockSkew](allowed-clock-skew.md): [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html)<br>the allowed clock skew for the verification; default is [Duration.ZERO](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/-companion/-z-e-r-o.html) |
| [ktorHttpClient](ktor-http-client.md) | [androidJvm]<br>var [ktorHttpClient](ktor-http-client.md): HttpClient |
| [verifySignature](verify-signature.md) | [androidJvm]<br>var [verifySignature](verify-signature.md): VerifyStatusListTokenJwtSignature<br>a function to verify the status list token signature; default is VerifyStatusListTokenSignature.x5c |

## Functions

| Name | Summary |
|---|---|
| [build](build.md) | [androidJvm]<br>fun [build](build.md)(): [DocumentStatusResolver](../index.md)<br>Builds the [DocumentStatusResolver](../index.md) instance |
| [withAllowedClockSkew](with-allowed-clock-skew.md) | [androidJvm]<br>fun [withAllowedClockSkew](with-allowed-clock-skew.md)(allowedClockSkew: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html)): &lt;Error class: unknown class&gt;<br>Sets the allowed clock skew for the verification |
| [withKtorHttpClientFactory](with-ktor-http-client-factory.md) | [androidJvm]<br>fun [withKtorHttpClientFactory](with-ktor-http-client-factory.md)(ktorHttpClient: HttpClient): &lt;Error class: unknown class&gt;<br>Sets the factory function to create an HttpClient |
| [withVerifySignature](with-verify-signature.md) | [androidJvm]<br>fun [withVerifySignature](with-verify-signature.md)(verifySignature: VerifyStatusListTokenJwtSignature): &lt;Error class: unknown class&gt;<br>Sets the function to verify the status list token signature |
