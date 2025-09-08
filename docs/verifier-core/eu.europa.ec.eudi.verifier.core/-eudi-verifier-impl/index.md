//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core](../index.md)/[EudiVerifierImpl](index.md)

# EudiVerifierImpl

[androidJvm]\
class [EudiVerifierImpl](index.md) : [EudiVerifier](../-eudi-verifier/index.md), [TransferManagerFactory](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/index.md), [DocumentStatusResolver](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md)

## Properties

| Name | Summary |
|---|---|
| [config](config.md) | [androidJvm]<br>open override val [config](config.md): [EudiVerifierConfig](../-eudi-verifier-config/index.md)<br>Configuration settings for the verifier. |
| [documentStatusResolver](document-status-resolver.md) | [androidJvm]<br>val [documentStatusResolver](document-status-resolver.md): [DocumentStatusResolver](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md) |
| [trustManager](trust-manager.md) | [androidJvm]<br>open override val [trustManager](trust-manager.md): TrustManager<br>Manager responsible for trust verification of documentsClaims. |

## Functions

| Name | Summary |
|---|---|
| [createTransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/create-transfer-manager.md) | [androidJvm]<br>open override fun [createTransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/create-transfer-manager.md)(config: [TransferConfig](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-config/index.md)): [TransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager/index.md)<br>Creates a new TransferManager instance with the specified configuration.<br>[androidJvm]<br>open override fun [createTransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/create-transfer-manager.md)(configure: [TransferConfig.Builder](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-config/-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)): [TransferManager](../../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager/index.md)<br>Creates a new TransferManager instance using a configuration lambda. |
| [isDocumentTrusted](../-eudi-verifier/is-document-trusted.md) | [androidJvm]<br>open override fun [isDocumentTrusted](../-eudi-verifier/is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: Instant): TrustManager.TrustResult<br>Implementation of [DocumentTrust.isDocumentTrusted](../-document-trust/is-document-trusted.md) that delegates verification to the verifier's [trustManager](../-eudi-verifier/trust-manager.md). |
| [resolveStatus](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/resolve-status.md) | [androidJvm]<br>open suspend override fun [resolveStatus](../../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/resolve-status.md)(response: [DeviceResponse](../../eu.europa.ec.eudi.verifier.core.response/-device-response/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;Status&gt;&gt;<br>Resolves the status of all documentsClaims in the given device response. |
