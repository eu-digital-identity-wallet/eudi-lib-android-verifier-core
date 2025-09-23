//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[CertificateProvider](index.md)/[loadCertificates](load-certificates.md)

# loadCertificates

[androidJvm]\
fun [loadCertificates](load-certificates.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), certificateRawIds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;

Loads X.509 certificates from the raw resources of the consuming application.

#### Return

A list of X509Certificate objects.

#### Parameters

androidJvm

| | |
|---|---|
| context | The application context. |
| certificateRawIds | A list of raw resource IDs from the app's R file. |
