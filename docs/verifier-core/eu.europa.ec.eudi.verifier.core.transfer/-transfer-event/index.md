//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferEvent](index.md)

# TransferEvent

sealed interface [TransferEvent](index.md) : [Event](../-event/index.md)

Represents events that occur during the transfer process between verifier and holder devices. Used to notify listeners about the state and results of the transfer session.

#### Inheritors

| |
|---|
| [DeviceEngagementCompleted](-device-engagement-completed/index.md) |
| [Connecting](-connecting/index.md) |
| [Connected](-connected/index.md) |
| [RequestSent](-request-sent/index.md) |
| [ResponseReceived](-response-received/index.md) |
| [Disconnected](-disconnected/index.md) |
| [Error](-error/index.md) |

## Types

| Name | Summary |
|---|---|
| [Connected](-connected/index.md) | [androidJvm]<br>data object [Connected](-connected/index.md) : [TransferEvent](index.md)<br>Indicates that a connection has been established. |
| [Connecting](-connecting/index.md) | [androidJvm]<br>data object [Connecting](-connecting/index.md) : [TransferEvent](index.md)<br>Indicates that a connection attempt is in progress. |
| [DeviceEngagementCompleted](-device-engagement-completed/index.md) | [androidJvm]<br>data object [DeviceEngagementCompleted](-device-engagement-completed/index.md) : [TransferEvent](index.md)<br>Indicates that device engagement has been successfully completed. |
| [Disconnected](-disconnected/index.md) | [androidJvm]<br>data object [Disconnected](-disconnected/index.md) : [TransferEvent](index.md)<br>Indicates that the connection has been disconnected. |
| [Error](-error/index.md) | [androidJvm]<br>data class [Error](-error/index.md)(val error: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-throwable/index.html)) : [TransferEvent](index.md)<br>Indicates that an error has occurred during the transfer process. |
| [Listener](-listener/index.md) | [androidJvm]<br>fun interface [Listener](-listener/index.md)<br>Listener interface for receiving transfer events. |
| [RequestSent](-request-sent/index.md) | [androidJvm]<br>data object [RequestSent](-request-sent/index.md) : [TransferEvent](index.md)<br>Indicates that a request has been sent to the holder device. |
| [ResponseReceived](-response-received/index.md) | [androidJvm]<br>data class [ResponseReceived](-response-received/index.md)(val response: [Response](../../eu.europa.ec.eudi.verifier.core.response/-response/index.md)) : [TransferEvent](index.md)<br>Indicates that a response has been received from the holder device. |
