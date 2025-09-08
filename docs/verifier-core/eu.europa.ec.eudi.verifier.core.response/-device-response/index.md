//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.response](../index.md)/[DeviceResponse](index.md)

# DeviceResponse

[androidJvm]\
class [DeviceResponse](index.md)(val deviceResponse: DeviceResponseParser.DeviceResponse, val deviceResponseBytes: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-byte-array/index.html)) : [Response](../-response/index.md)

Represents the response received from a device during the verification process.

## Constructors

| | |
|---|---|
| [DeviceResponse](-device-response.md) | [androidJvm]<br>constructor(deviceResponse: DeviceResponseParser.DeviceResponse, deviceResponseBytes: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-byte-array/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [deviceResponse](device-response.md) | [androidJvm]<br>val [deviceResponse](device-response.md): DeviceResponseParser.DeviceResponse<br>The parsed device response object. |
| [deviceResponseBytes](device-response-bytes.md) | [androidJvm]<br>val [deviceResponseBytes](device-response-bytes.md): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-byte-array/index.html)<br>The raw bytes of the device response. |
| [documentsClaims](documents-claims.md) | [androidJvm]<br>val [documentsClaims](documents-claims.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DocumentClaims](../-document-claims/index.md)&gt; |
| [documentsValidity](documents-validity.md) | [androidJvm]<br>val [documentsValidity](documents-validity.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DocumentValidity](../-document-validity/index.md)&gt; |
