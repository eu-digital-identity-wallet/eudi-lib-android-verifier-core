//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferManagerFactory](index.md)

# TransferManagerFactory

interface [TransferManagerFactory](index.md)

Factory interface for creating instances of [TransferManager](../-transfer-manager/index.md). Provides methods to create transfer managers with different configuration approaches.

#### Inheritors

| |
|---|
| [EudiVerifier](../../eu.europa.ec.eudi.verifier.core/-eudi-verifier/index.md) |
| [EudiVerifierImpl](../../eu.europa.ec.eudi.verifier.core/-eudi-verifier-impl/index.md) |
| [TransferManagerFactoryImpl](../-transfer-manager-factory-impl/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createTransferManager](create-transfer-manager.md) | [androidJvm]<br>abstract fun [createTransferManager](create-transfer-manager.md)(config: [TransferConfig](../-transfer-config/index.md)): [TransferManager](../-transfer-manager/index.md)<br>Creates a new [TransferManager](../-transfer-manager/index.md) instance with the specified configuration.<br>[androidJvm]<br>abstract fun [createTransferManager](create-transfer-manager.md)(configure: [TransferConfig.Builder](../-transfer-config/-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)): [TransferManager](../-transfer-manager/index.md)<br>Creates a new [TransferManager](../-transfer-manager/index.md) instance using a configuration lambda. |
