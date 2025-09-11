# EUDI Verifier Core library for Android

**Important!** Before you proceed, please read
the [EUDI Wallet Reference Implementation project description](https://github.com/eu-digital-identity-wallet/.github/blob/main/profile/reference-implementation.md)

## Overview

The EUDI Core Verifier SDK for Android provides the resources to develop an application to verify an
mDoc according to the ISO 18013-5 standard.
The library acts as a coordinator by orchestrating the various components that are required to
implement the EUDI Verifier functionality.

The library consists of the following main components:

- EudiVerifier
    - Main entry point for the EUDI verifier API, combining transfer management and document status
      resolution.
    - Provides methods to verify device responses and manage transfer events.
- TransferConfig
    - Configuration for the transfer process between verifier and holder devices.
    - Defines the available engagement methods and their associated connection methods.
- TransferManager
    - Interface for managing the transfer process between verifier and holder devices
    - Provides methods to initiate device engagement, send requests, and manage listeners for
      transfer events

## Disclaimer

The released software is an initial development release version:

- The initial development release is an early endeavor reflecting the efforts of a short timeboxed
  period, and by no means can be considered as the final product.
- The initial development release may be changed substantially over time, might introduce new
  features but also may change or remove existing ones, potentially breaking compatibility with your
  existing code.
- The initial development release is limited in functional scope.
- The initial development release may contain errors or design flaws and other problems that could
  cause system or other failures and data loss.
- The initial development release has reduced security, privacy, availability, and reliability
  standards relative to future releases. This could make the software slower, less reliable, or more
  vulnerable to attacks than mature software.
- The initial development release is not yet comprehensively documented.
- Users of the software must perform sufficient engineering and additional testing in order to
  properly evaluate their application and determine whether any of the open-sourced components is
  suitable for use in that application.
- We strongly recommend not putting this version of the software into production use.
- Only the latest version of the software will be supported

## Requirements

- Android 8 (API level 26) or higher

## Permissions

You need to declare the following permissions in the AndroidManifest and request them at runtime:
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT"/>
<uses-permission android:name="android.permission.BLUETOOTH_SCAN"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE"/>
```

### Dependencies

To use snapshot versions add the following to your project's settings.gradle file:

```groovy

dependencyResolutionManagement {
    repositories {
        // ...
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
            mavenContent { snapshotsOnly() }
        }
        // ...
    }
}
```

To include the library in your project, add the following dependencies to your app's build.gradle
file.

```groovy
dependencies {
    implementation "eu.europa.ec.eudi:eudi-lib-android-verifier-core:0.1.0-SNAPSHOT"
}
```

## How to Use

### Initialize the library

To instantiate a `EudiVerifier` use the `EudiVerifier.Builder` class or the `EudiVerifier.invoke`
method
from the companion object.

You will need to provide a `EudiVerifierConfig` object that contains the configuration for the
verifier.

```kotlin
// Configuring the verifier
lateinit var context: Context
// An instance of EudiVerifier is built through the invoke operator
val eudiVerifier = EudiVerifier(context) {
    // certificates of trusted issuers
    trustedCertificates(
        R.raw.apple_iaca,
        R.raw.credenceid_mdl_iaca_root,
        R.raw.google_mdl_iaca_cert
    )
}
```

### Transfer documents

The library currently supports the following way to transfer documents:

- Offline document transfer between devices over BLE, according to the ISO 18013-5 specification
- Device engagement using QR code

To create TransferManager, use the `EudiVerifier.createTransferManager` method.

```kotlin
// create transfer manager

val connectionMethods = listOf<MdocConnectionMethod>(
    MdocConnectionMethodBle(
        supportsPeripheralServerMode = false,
        supportsCentralClientMode = true,
        peripheralServerModeUuid = null,
        centralClientModeUuid = org.multipaz.util.UUID.randomUUID()
    )
)

val transferManager = eudiVerifier.createTransferManager {
    addEngagementMethod(
        TransferConfig.EngagementMethod.QR,
        connectionMethods
    )
    // Optional configurations, omitting defaults to setting them false
    useBleL2CAP(false)
    clearBleCacheOnDisconnect(false)
}
```

The transfer process is asynchronous. During the transfer, events are emitted that indicate the
current
state of the transfer. The events are represented by the `TransferEvent` sealed class. The following
events
are available:

1. `TransferEvent.DeviceEngagementCompleted`: The Devices have engaged.
2. `TransferEvent.Connecting`: Indicates that a connection attempt is in progress.
3. `TransferEvent.Connected`: Indicates that a connection has been established. This is the point
   where you call `transferManager.sendRequest(DeviceRequest)`
4. `TransferEvent.RequestSent`: A request has been sent to the holder device.
5. `TransferEvent.ResponseReceived`: Indicates that a response has been received from the holder
   device. Access it via `transferEvent.response`.
6. `TransferEvent.Disconnected`: The devices are disconnected.
7. `TransferEvent.Error`: An error occurred. Get the `Throwable` error from `transferEvent.error`.

To receive events from the `EudiVerifier`, you must attach a `TransferEvent.Listener` to the
`TransferManager` and handle the events accordingly.

```kotlin

// attach listener

transferManager.addListener { event ->
    when (event) {
        is TransferEvent.Connected -> println("Connected")
        TransferEvent.Connecting -> println("Connecting")
        TransferEvent.DeviceEngagementCompleted -> println("Device engagement completed")
        TransferEvent.Disconnected -> println("Disconnected")
        is TransferEvent.Error -> println(event.error)
        TransferEvent.RequestSent -> println("Request has been sent")
        is TransferEvent.ResponseReceived -> println("Response received")
    }
}

```

#### Detailed example

The following example demonstrates how to use the library to request the `org.iso.18013.5.1.mDL`
document.

```kotlin 
// Configuring the verifier

lateinit var context: Context
// certificates of trusted issuers

// An instance of EudiVerifier is built through the invoke operator
// Calling trustedCertificates configuration function is mandatory, not calling it causes a compile error 
eudiVerifier = EudiVerifier(context) {
    trustedCertificates(
        R.raw.apple_iaca,
        R.raw.credenceid_mdl_iaca_root,
        R.raw.google_mdl_iaca_cert
    )
}

// create transfer manager

val connectionMethods = listOf<MdocConnectionMethod>(
    MdocConnectionMethodBle(
        supportsPeripheralServerMode = false,
        supportsCentralClientMode = true,
        peripheralServerModeUuid = null,
        centralClientModeUuid = org.multipaz.util.UUID.randomUUID()
    )
)

val transferManager = eudiVerifier.createTransferManager {
    addEngagementMethod(
        TransferConfig.EngagementMethod.QR,
        connectionMethods
    )

    // Optional configurations, omitting defaults to setting them false
    useBleL2CAP(true)
    clearBleCacheOnDisconnect(true)
}

// attach listener

transferManager.addListener { event ->
    when (event) {
        is TransferEvent.Connected -> {
            // set Certificate for reader authentication - currently not supported 
            // val readerAuthCertificate: X509Certificate? = null

            // connected to device. Send request
            val docRequest = DocRequest(
                docType = "org.iso.18013.5.1.mDL",
                itemsRequest = mapOf(
                    "org.iso.18013.5.1" to mapOf(
                        // element identifier to intent to retain flag
                        "portrait" to false,
                        "age_over_18" to false,
                        "family_name" to false,
                        "birth_date" to false
                    )
                ),
                // readerAuthentication is not supported yet
                readerAuthCertificate = null
            )
            // create more docRequests if needed
            // ...

            // create device request
            val deviceRequest = DeviceRequest(docRequests = listOf(docRequest))
            // send device request
            event.transferManager.sendRequest(deviceRequest)
        }

        TransferEvent.Connecting -> println("Connecting")
        TransferEvent.DeviceEngagementCompleted -> println("Device engagement completed")
        TransferEvent.Disconnected -> println("Disconnected")

        is TransferEvent.Error -> {
            // handle error
            println("Error: ${event.error}")
        }

        TransferEvent.RequestSent -> println("Request has been sent")
        is TransferEvent.ResponseReceived -> {
            // cast to DeviceResponse
            val response = event.response as DeviceResponse

            for (doc in response.deviceResponse.documents) {
                // trust if a trust point was found
                val isDocumentTrusted = eudiVerifier.isDocumentTrusted(doc)
                println("${doc.docType} Trusted: ${isDocumentTrusted.isTrusted}")
            }

            // structured objects to access display data
            val documentClaims = response.documentsClaims
            val documentValidity = response.documentsValidity

            for (doc in documentClaims) {
                Log.d("DocType", doc.docType)

                for (nameSpace in doc.claims) {
                    Log.d("NameSpace", nameSpace.key)
                    for (element in nameSpace.value) {
                        Log.d("Element Name-Value", "${element.key}-${element.value}")
                    }
                }

                val validity = documentValidity.find { it.docType == doc.docType }
                Log.d("Device Signature Valid", "${validity?.isDeviceSignatureValid}")
                Log.d("Issuer Signature Valid", "${validity?.isIssuerSignatureValid}")
                Log.d("Authentication Method", "${validity?.deviceAuthMethod}")

                // Whether all MSO element checks passed
                Log.d("Data Integrity Valid", "${validity?.isDataIntegrityIntact}")

                // MSO Validity timestamps
                Log.d("Signed", "${validity?.msoValidity?.signed}")
                Log.d("Valid From", "${validity?.msoValidity?.validFrom}")
                Log.d("Valid Until", "${validity?.msoValidity?.validUntil}")

                // Individual MSO Element checks
                for (msoElement in validity!!.elementMsoResults) {
                    Log.d(
                        "Issuer-ElementName-DigestMatched",
                        "${msoElement.namespace}-${msoElement.identifier}-${msoElement.digestMatched}"
                    )
                }
            }
        }
    }
}

// use scanner library of your choice to scan the QR for device engagement
// when qr content is available, call startQRDeviceEngagement()
val qrText: String =
    "mdoc:owBjMS4wAYIB2BhYS6QBAiABIVggN ... 86vSyDAgGjAPUB9ApQmCMM53MkSc202zKjnzq9LA"
transferManager.startQRDeviceEngagement(qrText)

```

## How to contribute

We welcome contributions to this project. To ensure that the process is smooth for everyone
involved, follow the guidelines found in [CONTRIBUTING.md](CONTRIBUTING.md).

## License

### Third-party component licenses

See [licenses.md](licenses.md) for details.

### License details

Copyright (c) 2023 European Commission

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

