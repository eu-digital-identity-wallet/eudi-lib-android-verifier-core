//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[VerifyStatusListTokenSignatureX5c](index.md)/[invoke](invoke.md)

# invoke

[androidJvm]\
open suspend operator override fun [invoke](invoke.md)(statusListToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), at: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Verifies the signature of a status list token.

The verification process:

1. 
   Parses the JWT token
2. 
   Extracts the certificate chain from the x5c header
3. 
   Gets the public key from the first certificate
4. 
   Creates an appropriate verifier based on the key type (RSA or ECDSA)
5. 
   Verifies the token's signature
6. 
   Validates the certificate chain using the trust manager

#### Return

A [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) containing Unit on success, or an exception on failure:     - [IllegalArgumentException](https://developer.android.com/reference/kotlin/java/lang/IllegalArgumentException.html) if the format is not JWT     - [IllegalStateException](https://developer.android.com/reference/kotlin/java/lang/IllegalStateException.html) if the x5c header is missing or for unsupported key types     - [SignatureVerificationError](../-signature-verification-error/index.md) if signature verification fails or certificate chain is untrusted

#### Parameters

androidJvm

| | |
|---|---|
| statusListToken | The JWT format status list token to verify |
| format | The format of the status list token (currently only JWT is supported) |
| at | The time at which the verification is performed |
