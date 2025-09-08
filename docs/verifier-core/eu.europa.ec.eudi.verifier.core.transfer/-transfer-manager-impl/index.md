//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferManagerImpl](index.md)

# TransferManagerImpl

[androidJvm]\
class [TransferManagerImpl](index.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [TransferConfig](../-transfer-config/index.md)) : [TransferManager](../-transfer-manager/index.md)

## Constructors

| | |
|---|---|
| [TransferManagerImpl](-transfer-manager-impl.md) | [androidJvm]<br>constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [TransferConfig](../-transfer-config/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addListener](add-listener.md) | [androidJvm]<br>open override fun [addListener](add-listener.md)(listener: [TransferEvent.Listener](../-transfer-event/-listener/index.md))<br>Registers a listener to receive transfer events. |
| [disableNFCDeviceEngagement](disable-n-f-c-device-engagement.md) | [androidJvm]<br>open override fun [disableNFCDeviceEngagement](disable-n-f-c-device-engagement.md)()<br>Disables NFC device engagement. |
| [enableNFCDeviceEngagement](enable-n-f-c-device-engagement.md) | [androidJvm]<br>open override fun [enableNFCDeviceEngagement](enable-n-f-c-device-engagement.md)(nfcAdapter: [NfcAdapter](https://developer.android.com/reference/kotlin/android/nfc/NfcAdapter.html), activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html))<br>Enables NFC device engagement using the provided NFC adapter and activity context. |
| [removeListener](remove-listener.md) | [androidJvm]<br>open override fun [removeListener](remove-listener.md)(listener: [TransferEvent.Listener](../-transfer-event/-listener/index.md))<br>Unregisters a listener from receiving transfer events. |
| [sendRequest](send-request.md) | [androidJvm]<br>open override fun [sendRequest](send-request.md)(request: [Request](../../eu.europa.ec.eudi.verifier.core.request/-request/index.md))<br>Sends a request to the holder device. |
| [startQRDeviceEngagement](start-q-r-device-engagement.md) | [androidJvm]<br>open override fun [startQRDeviceEngagement](start-q-r-device-engagement.md)(qrCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Starts device engagement using a QR code. |
| [stopSession](stop-session.md) | [androidJvm]<br>open override fun [stopSession](stop-session.md)()<br>Stops the current transfer session. |
