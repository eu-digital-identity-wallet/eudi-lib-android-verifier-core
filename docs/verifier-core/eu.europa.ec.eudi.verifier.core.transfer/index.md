//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Event](-event/index.md) | [androidJvm]<br>interface [Event](-event/index.md) |
| [TransferConfig](-transfer-config/index.md) | [androidJvm]<br>class [TransferConfig](-transfer-config/index.md)<br>Configuration for the transfer process between verifier and holder devices. Defines the available engagement methods and their associated connection methods. |
| [TransferEvent](-transfer-event/index.md) | [androidJvm]<br>sealed interface [TransferEvent](-transfer-event/index.md) : [Event](-event/index.md)<br>Represents events that occur during the transfer process between verifier and holder devices. Used to notify listeners about the state and results of the transfer session. |
| [TransferManager](-transfer-manager/index.md) | [androidJvm]<br>interface [TransferManager](-transfer-manager/index.md)<br>Interface for managing the transfer process between verifier and holder devices. Provides methods to initiate device engagement, send requests, and manage listeners for transfer events. |
| [TransferManagerFactory](-transfer-manager-factory/index.md) | [androidJvm]<br>interface [TransferManagerFactory](-transfer-manager-factory/index.md)<br>Factory interface for creating instances of [TransferManager](-transfer-manager/index.md). Provides methods to create transfer managers with different configuration approaches. |
| [TransferManagerFactoryImpl](-transfer-manager-factory-impl/index.md) | [androidJvm]<br>class [TransferManagerFactoryImpl](-transfer-manager-factory-impl/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), logger: [Logger](../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)) : [TransferManagerFactory](-transfer-manager-factory/index.md)<br>Implementation of [TransferManagerFactory](-transfer-manager-factory/index.md) that creates [TransferManagerImpl](-transfer-manager-impl/index.md) instances. |
| [TransferManagerImpl](-transfer-manager-impl/index.md) | [androidJvm]<br>class [TransferManagerImpl](-transfer-manager-impl/index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [TransferConfig](-transfer-config/index.md), var logger: [Logger](../eu.europa.ec.eudi.verifier.core.logging/-logger/index.md)? = null) : [TransferManager](-transfer-manager/index.md) |
