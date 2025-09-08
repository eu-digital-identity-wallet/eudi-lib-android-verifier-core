//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[DocumentStatusResolver](index.md)

# DocumentStatusResolver

interface [DocumentStatusResolver](index.md)

Interface for resolving the status of documentsClaims in a device response.

The status resolver performs validation against status list services to determine if documentsClaims are valid, revoked, or suspended.

#### Inheritors

| |
|---|
| [EudiVerifier](../../eu.europa.ec.eudi.verifier.core/-eudi-verifier/index.md) |
| [EudiVerifierImpl](../../eu.europa.ec.eudi.verifier.core/-eudi-verifier-impl/index.md) |
| [DocumentStatusResolverImpl](../-document-status-resolver-impl/index.md) |

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>class [Builder](-builder/index.md)<br>Builder for [DocumentStatusResolver](index.md) It allows to set the parameters for the resolver it builds a [DocumentStatusResolverImpl](../-document-status-resolver-impl/index.md) |
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [resolveStatus](resolve-status.md) | [androidJvm]<br>abstract suspend fun [resolveStatus](resolve-status.md)(response: [DeviceResponse](../../eu.europa.ec.eudi.verifier.core.response/-device-response/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;Status&gt;&gt;<br>Resolves the status of all documentsClaims in the given device response. |
