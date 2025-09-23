//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DocumentStatusResolver](-document-status-resolver/index.md) | [androidJvm]<br>interface [DocumentStatusResolver](-document-status-resolver/index.md)<br>Interface for resolving the status of documentsClaims in a device response. |
| [DocumentStatusResolverImpl](-document-status-resolver-impl/index.md) | [androidJvm]<br>class [DocumentStatusResolverImpl](-document-status-resolver-impl/index.md)(verifySignature: VerifyStatusListTokenJwtSignature, allowedClockSkew: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-duration/index.html), ktorHttpClient: HttpClient) : [DocumentStatusResolver](-document-status-resolver/index.md)<br>Default implementation of [DocumentStatusResolver](-document-status-resolver/index.md) that validates document status against status list services. |
| [SignatureVerificationError](-signature-verification-error/index.md) | [androidJvm]<br>class [SignatureVerificationError](-signature-verification-error/index.md) : [IllegalStateException](https://developer.android.com/reference/kotlin/java/lang/IllegalStateException.html)<br>Custom exception for signature verification errors. Thrown when a token's signature cannot be verified or its certificate chain is untrusted. |
| [VerifyStatusListTokenSignatureX5c](-verify-status-list-token-signature-x5c/index.md) | [androidJvm]<br>class [VerifyStatusListTokenSignatureX5c](-verify-status-list-token-signature-x5c/index.md)(val trustManager: TrustManager, var logger: [Logger](../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)? = null) : VerifyStatusListTokenJwtSignature<br>Implementation of VerifyStatusListTokenSignature that verifies status list token signatures using X.509 certificate chains from the token's x5c header. |
