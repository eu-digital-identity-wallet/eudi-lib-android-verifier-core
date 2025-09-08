//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DataElementIdentifier](-data-element-identifier/index.md) | [androidJvm]<br>typealias [DataElementIdentifier](-data-element-identifier/index.md) = [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Type alias for the data element identifier string. |
| [DocType](-doc-type/index.md) | [androidJvm]<br>typealias [DocType](-doc-type/index.md) = [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Type alias for the document type string. |
| [DocumentTrust](-document-trust/index.md) | [androidJvm]<br>interface [DocumentTrust](-document-trust/index.md)<br>Interface defining document trust verification operations. |
| [EudiVerifier](-eudi-verifier/index.md) | [androidJvm]<br>interface [EudiVerifier](-eudi-verifier/index.md) : [TransferManagerFactory](../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/index.md), [DocumentStatusResolver](../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md), [DocumentTrust](-document-trust/index.md)<br>Main entry point for the EUDI verifier API, combining transfer management and document status resolution. |
| [EudiVerifierConfig](-eudi-verifier-config/index.md) | [androidJvm]<br>class [EudiVerifierConfig](-eudi-verifier-config/index.md)<br>Configuration for the EUDI verifier. |
| [EudiVerifierImpl](-eudi-verifier-impl/index.md) | [androidJvm]<br>class [EudiVerifierImpl](-eudi-verifier-impl/index.md) : [EudiVerifier](-eudi-verifier/index.md), [TransferManagerFactory](../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/index.md), [DocumentStatusResolver](../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md) |
| [IssuerInfo](-issuer-info/index.md) | [androidJvm]<br>data class [IssuerInfo](-issuer-info/index.md)(val commonName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), val organization: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?, val country: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?)<br>Data class representing issuer information. |
| [Namespace](-namespace/index.md) | [androidJvm]<br>typealias [Namespace](-namespace/index.md) = [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Type alias for the namespace string. |
| [ValidityInfoResult](-validity-info-result/index.md) | [androidJvm]<br>sealed interface [ValidityInfoResult](-validity-info-result/index.md) |

## Properties

| Name | Summary |
|---|---|
| [issuerInfo](issuer-info.md) | [androidJvm]<br>val TrustManager.TrustResult.[issuerInfo](issuer-info.md): [IssuerInfo](-issuer-info/index.md)?<br>Extension property that retrieves the issuer information from the trust chain. |

## Functions

| Name | Summary |
|---|---|
| [checkValidityInfo](check-validity-info.md) | [androidJvm]<br>fun DeviceResponseParser.Document.[checkValidityInfo](check-validity-info.md)(atTime: Instant = Clock.System.now()): [ValidityInfoResult](-validity-info-result/index.md) |
| [isDocumentTrusted](is-document-trusted.md) | [androidJvm]<br>fun TrustManager.[isDocumentTrusted](is-document-trusted.md)(document: DeviceResponseParser.Document, atTime: Instant = Clock.System.now()): TrustManager.TrustResult<br>Extension function that checks if a document is trusted using the TrustManager. |
