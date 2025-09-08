//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core.request](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DeviceRequest](-device-request/index.md) | [androidJvm]<br>class [DeviceRequest](-device-request/index.md)(val docRequests: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[DocRequest](-doc-request/index.md)&gt;) : [Request](-request/index.md)<br>Represents a request sent to a device, containing a list of document requests. |
| [DocRequest](-doc-request/index.md) | [androidJvm]<br>data class [DocRequest](-doc-request/index.md)(val docType: [DocType](../eu.europa.ec.eudi.verifier.core/-doc-type/index.md), var itemsRequest: [ItemsRequest](-items-request/index.md), var readerAuthCertificate: [X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)?)<br>Represents a request for a specific document type, including requested items and reader authentication. |
| [IntentToRetain](-intent-to-retain/index.md) | [androidJvm]<br>typealias [IntentToRetain](-intent-to-retain/index.md) = [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)<br>Type alias for the intent to retain boolean value. |
| [ItemsRequest](-items-request/index.md) | [androidJvm]<br>typealias [ItemsRequest](-items-request/index.md) = [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[Namespace](../eu.europa.ec.eudi.verifier.core/-namespace/index.md), [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[DataElementIdentifier](../eu.europa.ec.eudi.verifier.core/-data-element-identifier/index.md), [IntentToRetain](-intent-to-retain/index.md)&gt;&gt;<br>Type alias for the items request map structure. |
| [Request](-request/index.md) | [androidJvm]<br>interface [Request](-request/index.md)<br>Represents a generic request in the verifier core system. Used as a marker interface for different types of requests. |
