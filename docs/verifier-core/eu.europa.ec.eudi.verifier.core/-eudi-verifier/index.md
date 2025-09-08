//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[EudiVerifier](index.md)

# EudiVerifier

interface [EudiVerifier](index.md) : [TransferManagerFactory](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/index.md), [DocumentStatusResolver](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md), [DocumentTrust](../-document-trust/index.md)

Main entry point for the EUDI verifier API, combining transfer management and document status resolution.

Provides methods to verify device responses and manage transfer events.

#### Inheritors

| |
|---|
| [EudiVerifierImpl](../-eudi-verifier-impl/index.md) |

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>class [Builder](-builder/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [EudiVerifierConfig](../-eudi-verifier-config/index.md))<br>Builder for creating an [EudiVerifier](index.md) with custom settings. |
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [config](config.md) | [androidJvm]<br>abstract val [config](config.md): [EudiVerifierConfig](../-eudi-verifier-config/index.md)<br>Configuration settings for the verifier. |
| [trustManager](trust-manager.md) | [androidJvm]<br>abstract val [trustManager](trust-manager.md): TrustManager<br>Manager responsible for trust verification of documentsClaims. |

## Functions

| Name | Summary |
|---|---|
| [createTransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/create-transfer-manager.md) | [androidJvm]<br>abstract fun [createTransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/create-transfer-manager.md)(config: [TransferConfig](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-config/index.md)): [TransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager/index.md)<br>Creates a new [TransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager/index.md) instance with the specified configuration.<br>[androidJvm]<br>abstract fun [createTransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/create-transfer-manager.md)(configure: [TransferConfig.Builder](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-config/-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)): [TransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager/index.md)<br>Creates a new [TransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager/index.md) instance using a configuration lambda. |
| [isDocumentTrusted](is-document-trusted.md) | [androidJvm]<br>open override fun [isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: Instant): TrustManager.TrustResult<br>Implementation of [DocumentTrust.isDocumentTrusted](../-document-trust/is-document-trusted.md) that delegates verification to the verifier's [trustManager](trust-manager.md). |
| [resolveStatus](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/resolve-status.md) | [androidJvm]<br>abstract suspend fun [resolveStatus](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/resolve-status.md)(response: [DeviceResponse](../../eu.europa.ec.eudi.verifier.core.response/-device-response/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;Status&gt;&gt;<br>Resolves the status of all documentsClaims in the given device response. |
