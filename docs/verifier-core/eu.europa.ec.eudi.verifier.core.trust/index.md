//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core.trust](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DocumentTrust](-document-trust/index.md) | [androidJvm]<br>interface [DocumentTrust](-document-trust/index.md)<br>Interface defining document trust verification operations. |
| [IssuerInfo](-issuer-info/index.md) | [androidJvm]<br>data class [IssuerInfo](-issuer-info/index.md)(val commonName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), val organization: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?, val country: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?)<br>Data class representing issuer information. |

## Properties

| Name | Summary |
|---|---|
| [issuerInfo](issuer-info.md) | [androidJvm]<br>val TrustResult.[issuerInfo](issuer-info.md): [IssuerInfo](-issuer-info/index.md)?<br>Extension property that retrieves the issuer information from the trust chain. |

## Functions

| Name | Summary |
|---|---|
| [isDocumentTrusted](is-document-trusted.md) | [androidJvm]<br>suspend fun TrustManager.[isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: [Instant](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.time/-instant/index.html) = Clock.System.now()): TrustResult<br>Extension function that checks if a document is trusted using the TrustManager. |
