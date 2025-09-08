//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferManagerFactoryImpl](index.md)

# TransferManagerFactoryImpl

[androidJvm]\
class [TransferManagerFactoryImpl](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [TransferManagerFactory](../-transfer-manager-factory/index.md)

Implementation of [TransferManagerFactory](../-transfer-manager-factory/index.md) that creates [TransferManagerImpl](../-transfer-manager-impl/index.md) instances.

## Constructors

| | |
|---|---|
| [TransferManagerFactoryImpl](-transfer-manager-factory-impl.md) | [androidJvm]<br>constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Functions

| Name | Summary |
|---|---|
| [createTransferManager](create-transfer-manager.md) | [androidJvm]<br>open override fun [createTransferManager](create-transfer-manager.md)(config: [TransferConfig](../-transfer-config/index.md)): [TransferManager](../-transfer-manager/index.md)<br>Creates a new [TransferManager](../-transfer-manager/index.md) instance with the specified configuration.<br>[androidJvm]<br>open override fun [createTransferManager](create-transfer-manager.md)(configure: [TransferConfig.Builder](../-transfer-config/-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)): [TransferManager](../-transfer-manager/index.md)<br>Creates a new [TransferManager](../-transfer-manager/index.md) instance using a configuration lambda. |
