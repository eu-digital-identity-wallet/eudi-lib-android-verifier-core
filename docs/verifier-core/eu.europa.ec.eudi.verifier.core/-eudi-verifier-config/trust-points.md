//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[EudiVerifierConfig](index.md)/[trustPoints](trust-points.md)

# trustPoints

[androidJvm]\
fun [trustPoints](trust-points.md)(vararg trustPoints: TrustPoint)

Sets the list of trusted points using a vararg of TrustPoint.

#### Parameters

androidJvm

| | |
|---|---|
| trustPoints | Vararg of trust points to set as trusted points. |

[androidJvm]\
fun [trustPoints](trust-points.md)(trustPoints: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;TrustPoint&gt;)

Sets the list of trusted points.

#### Parameters

androidJvm

| | |
|---|---|
| trustPoints | List of trust points to set as trusted points. |

[androidJvm]\
var [trustPoints](trust-points.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;TrustPoint&gt;

List of trust points representing the trusted points used during verification.
