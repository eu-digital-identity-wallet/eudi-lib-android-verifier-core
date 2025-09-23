//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core](../../index.md)/[EudiVerifier](../index.md)/[Builder](index.md)

# Builder

[androidJvm]\
class [Builder](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [EudiVerifierConfig](../../-eudi-verifier-config/index.md))

Builder for creating an [EudiVerifier](../index.md) with custom settings.

## Constructors

| | |
|---|---|
| [Builder](-builder.md) | [androidJvm]<br>constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [EudiVerifierConfig](../../-eudi-verifier-config/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [androidJvm]<br>var [logger](logger.md): [Logger](../../../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)? |

## Functions

| Name | Summary |
|---|---|
| [build](build.md) | [androidJvm]<br>fun [build](build.md)(): [EudiVerifier](../index.md)<br>Builds the [EudiVerifier](../index.md) instance with the current builder settings. |
| [trustedCertificates](trusted-certificates.md) | [androidJvm]<br>fun [trustedCertificates](trusted-certificates.md)(@[RawRes](https://developer.android.com/reference/kotlin/androidx/annotation/RawRes.html)vararg certificateRawIds: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html))<br>Configures the verifier to trust a custom set of certificates provided as raw resource IDs from the app.<br>[androidJvm]<br>fun [trustedCertificates](trusted-certificates.md)(certificatesProvided: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;)<br>Configures the verifier to trust a pre-loaded list of X509Certificates. |
| [withDocumentStatusResolver](with-document-status-resolver.md) | [androidJvm]<br>fun [withDocumentStatusResolver](with-document-status-resolver.md)(documentStatusResolver: [DocumentStatusResolver](../../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md)): &lt;Error class: unknown class&gt; |
| [withLogger](with-logger.md) | [androidJvm]<br>fun [withLogger](with-logger.md)(logger: [Logger](../../../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)): &lt;Error class: unknown class&gt;<br>Configure with the given [Logger](../../../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md) to use for logging. If not set, the default logger will be used which is configured with the [EudiVerifierConfig.configureLogging](../../-eudi-verifier-config/configure-logging.md). |
