//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[EudiVerifierConfig](index.md)

# EudiVerifierConfig

[androidJvm]\
class [EudiVerifierConfig](index.md)

Configuration for the EUDI verifier.

## Constructors

| | |
|---|---|
| [EudiVerifierConfig](-eudi-verifier-config.md) | [androidJvm]<br>constructor() |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [trustPoints](trust-points.md) | [androidJvm]<br>var [trustPoints](trust-points.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;TrustPoint&gt;<br>List of trust points representing the trusted points used during verification. |

## Functions

| Name | Summary |
|---|---|
| [trustPoints](trust-points.md) | [androidJvm]<br>fun [trustPoints](trust-points.md)(vararg trustPoints: TrustPoint)<br>Sets the list of trusted points using a vararg of TrustPoint.<br>[androidJvm]<br>fun [trustPoints](trust-points.md)(trustPoints: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;TrustPoint&gt;)<br>Sets the list of trusted points. |
