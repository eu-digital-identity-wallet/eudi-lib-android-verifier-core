//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.request](../index.md)/[DocRequest](index.md)

# DocRequest

[androidJvm]\
data class [DocRequest](index.md)(val docType: [DocType](../../eu.europa.ec.eudi.verifier.core/-doc-type/index.md), var itemsRequest: [ItemsRequest](../-items-request/index.md), var readerAuthCertificate: [X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)?)

Represents a request for a specific document type, including requested items and reader authentication.

## Constructors

| | |
|---|---|
| [DocRequest](-doc-request.md) | [androidJvm]<br>constructor(docType: [DocType](../../eu.europa.ec.eudi.verifier.core/-doc-type/index.md), itemsRequest: [ItemsRequest](../-items-request/index.md), readerAuthCertificate: [X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)?) |

## Properties

| Name | Summary |
|---|---|
| [docType](doc-type.md) | [androidJvm]<br>val [docType](doc-type.md): [DocType](../../eu.europa.ec.eudi.verifier.core/-doc-type/index.md)<br>The type of document being requested. |
| [itemsRequest](items-request.md) | [androidJvm]<br>var [itemsRequest](items-request.md): [ItemsRequest](../-items-request/index.md)<br>The map of namespaces to data element identifiers and their intent to retain. |
| [readerAuthCertificate](reader-auth-certificate.md) | [androidJvm]<br>var [readerAuthCertificate](reader-auth-certificate.md): [X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)?<br>The certificate used for reader authentication, if available. |
