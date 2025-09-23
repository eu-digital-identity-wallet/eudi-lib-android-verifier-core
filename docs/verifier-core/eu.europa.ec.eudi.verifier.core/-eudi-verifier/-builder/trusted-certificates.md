//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core](../../index.md)/[EudiVerifier](../index.md)/[Builder](index.md)/[trustedCertificates](trusted-certificates.md)

# trustedCertificates

[androidJvm]\
fun [trustedCertificates](trusted-certificates.md)(@[RawRes](https://developer.android.com/reference/kotlin/androidx/annotation/RawRes.html)vararg certificateRawIds: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html))

Configures the verifier to trust a custom set of certificates provided as raw resource IDs from the app.

[androidJvm]\
fun [trustedCertificates](trusted-certificates.md)(certificatesProvided: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;)

Configures the verifier to trust a pre-loaded list of X509Certificates.
