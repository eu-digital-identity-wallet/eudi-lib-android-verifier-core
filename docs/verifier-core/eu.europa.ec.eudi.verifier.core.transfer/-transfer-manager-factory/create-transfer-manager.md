//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferManagerFactory](index.md)/[createTransferManager](create-transfer-manager.md)

# createTransferManager

[androidJvm]\
abstract fun [createTransferManager](create-transfer-manager.md)(config: [TransferConfig](../-transfer-config/index.md)): [TransferManager](../-transfer-manager/index.md)

Creates a new [TransferManager](../-transfer-manager/index.md) instance with the specified configuration.

#### Return

A new instance of [TransferManager](../-transfer-manager/index.md).

#### Parameters

androidJvm

| | |
|---|---|
| config | The pre-configured [TransferConfig](../-transfer-config/index.md) to use. |

[androidJvm]\
abstract fun [createTransferManager](create-transfer-manager.md)(configure: [TransferConfig.Builder](../-transfer-config/-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)): [TransferManager](../-transfer-manager/index.md)

Creates a new [TransferManager](../-transfer-manager/index.md) instance using a configuration lambda.

#### Return

A new instance of [TransferManager](../-transfer-manager/index.md).

#### Parameters

androidJvm

| | |
|---|---|
| configure | Lambda with receiver for configuring the [TransferConfig.Builder](../-transfer-config/-builder/index.md). |
