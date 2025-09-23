//[verifier-core](../../index.md)/[eu.europa.ec.eudi.verifier.core](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CertificateProvider](-certificate-provider/index.md) | [androidJvm]<br>object [CertificateProvider](-certificate-provider/index.md) |
| [DataElementIdentifier](-data-element-identifier/index.md) | [androidJvm]<br>typealias [DataElementIdentifier](-data-element-identifier/index.md) = [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Type alias for the data element identifier string. |
| [DocType](-doc-type/index.md) | [androidJvm]<br>typealias [DocType](-doc-type/index.md) = [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Type alias for the document type string. |
| [EudiVerifier](-eudi-verifier/index.md) | [androidJvm]<br>interface [EudiVerifier](-eudi-verifier/index.md) : [TransferManagerFactory](../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/index.md), [DocumentStatusResolver](../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md), [DocumentTrust](../eu.europa.ec.eudi.verifier.core.trust/-document-trust/index.md)<br>Main entry point for the EUDI verifier API, combining transfer management and document status resolution. |
| [EudiVerifierConfig](-eudi-verifier-config/index.md) | [androidJvm]<br>class [EudiVerifierConfig](-eudi-verifier-config/index.md)<br>Configuration for the EUDI verifier. |
| [EudiVerifierImpl](-eudi-verifier-impl/index.md) | [androidJvm]<br>class [EudiVerifierImpl](-eudi-verifier-impl/index.md) : [EudiVerifier](-eudi-verifier/index.md), [TransferManagerFactory](../eu.europa.ec.eudi.verifier.core.transfer/-transfer-manager-factory/index.md), [DocumentStatusResolver](../eu.europa.ec.eudi.verifier.core.statium/-document-status-resolver/index.md) |
| [Namespace](-namespace/index.md) | [androidJvm]<br>typealias [Namespace](-namespace/index.md) = [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Type alias for the namespace string. |
