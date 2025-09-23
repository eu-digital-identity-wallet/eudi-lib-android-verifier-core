//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[VerifyStatusListTokenSignatureX5c](index.md)

# VerifyStatusListTokenSignatureX5c

[androidJvm]\
class [VerifyStatusListTokenSignatureX5c](index.md)(val trustManager: TrustManager, var logger: [Logger](../../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)? = null) : VerifyStatusListTokenJwtSignature

Implementation of VerifyStatusListTokenSignature that verifies status list token signatures using X.509 certificate chains from the token's x5c header.

This implementation:

- 
   Supports JWT format tokens with x5c headers
- 
   Verifies signatures using RSA or ECDSA algorithms
- 
   Validates the certificate chain against the provided TrustManager

## Constructors

| | |
|---|---|
| [VerifyStatusListTokenSignatureX5c](-verify-status-list-token-signature-x5c.md) | [androidJvm]<br>constructor(trustManager: TrustManager, logger: [Logger](../../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)? = null) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [androidJvm]<br>var [logger](logger.md): [Logger](../../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)? |
| [trustManager](trust-manager.md) | [androidJvm]<br>val [trustManager](trust-manager.md): TrustManager<br>The trust manager used to validate certificate chains. |

## Functions

| Name | Summary |
|---|---|
| [invoke](invoke.md) | [androidJvm]<br>open suspend operator override fun [invoke](invoke.md)(statusListToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), at: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Verifies the signature of a status list token. |
