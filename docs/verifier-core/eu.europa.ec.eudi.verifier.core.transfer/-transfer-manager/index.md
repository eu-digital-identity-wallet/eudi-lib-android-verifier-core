//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferManager](index.md)

# TransferManager

interface [TransferManager](index.md)

Interface for managing the transfer process between verifier and holder devices. Provides methods to initiate device engagement, send requests, and manage listeners for transfer events.

#### Inheritors

| |
|---|
| [TransferManagerImpl](../-transfer-manager-impl/index.md) |

## Functions

| Name | Summary |
|---|---|
| [addListener](add-listener.md) | [androidJvm]<br>abstract fun [addListener](add-listener.md)(listener: [TransferEvent.Listener](../-transfer-event/-listener/index.md))<br>Registers a listener to receive transfer events. |
| [disableNFCDeviceEngagement](disable-n-f-c-device-engagement.md) | [androidJvm]<br>abstract fun [disableNFCDeviceEngagement](disable-n-f-c-device-engagement.md)()<br>Disables NFC device engagement. |
| [enableNFCDeviceEngagement](enable-n-f-c-device-engagement.md) | [androidJvm]<br>abstract fun [enableNFCDeviceEngagement](enable-n-f-c-device-engagement.md)(nfcAdapter: [NfcAdapter](https://developer.android.com/reference/kotlin/android/nfc/NfcAdapter.html), activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html))<br>Enables NFC device engagement using the provided NFC adapter and activity context. |
| [removeListener](remove-listener.md) | [androidJvm]<br>abstract fun [removeListener](remove-listener.md)(listener: [TransferEvent.Listener](../-transfer-event/-listener/index.md))<br>Unregisters a listener from receiving transfer events. |
| [sendRequest](send-request.md) | [androidJvm]<br>abstract fun [sendRequest](send-request.md)(request: [Request](../../eu.europa.ec.eudi.verifier.core.request/-request/index.md))<br>Sends a request to the holder device. |
| [startQRDeviceEngagement](start-q-r-device-engagement.md) | [androidJvm]<br>abstract fun [startQRDeviceEngagement](start-q-r-device-engagement.md)(qrCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Starts device engagement using a QR code. |
| [stopSession](stop-session.md) | [androidJvm]<br>abstract fun [stopSession](stop-session.md)()<br>Stops the current transfer session. |
