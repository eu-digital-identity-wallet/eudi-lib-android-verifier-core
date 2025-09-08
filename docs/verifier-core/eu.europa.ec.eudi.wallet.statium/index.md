//[verifier-core](../../index.md)/[eu.europa.ec.eudi.wallet.statium](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [SignatureVerificationError](-signature-verification-error/index.md) | [androidJvm]<br>class [SignatureVerificationError](-signature-verification-error/index.md) : [IllegalStateException](https://developer.android.com/reference/kotlin/java/lang/IllegalStateException.html)<br>Custom exception for signature verification errors. Thrown when a token's signature cannot be verified or its certificate chain is untrusted. |
| [VerifyStatusListTokenSignatureX5c](-verify-status-list-token-signature-x5c/index.md) | [androidJvm]<br>class [VerifyStatusListTokenSignatureX5c](-verify-status-list-token-signature-x5c/index.md)(val trustManager: TrustManager) : VerifyStatusListTokenSignature<br>Implementation of VerifyStatusListTokenSignature that verifies status list token signatures using X.509 certificate chains from the token's x5c header. |
