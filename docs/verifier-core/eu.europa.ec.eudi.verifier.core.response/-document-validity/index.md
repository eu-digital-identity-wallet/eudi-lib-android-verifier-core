//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.response](../index.md)/[DocumentValidity](index.md)

# DocumentValidity

[androidJvm]\
data class [DocumentValidity](index.md)(val docType: [DocType](../../eu.europa.ec.eudi.verifier.core/-doc-type/index.md), val isDeviceSignatureValid: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), val isIssuerSignatureValid: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), val isDataIntegrityIntact: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), val deviceAuthMethod: [DeviceAuthMethod](../-device-auth-method/index.md), val msoValidity: [MsoValidity](../-mso-validity/index.md), val elementMsoResults: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ElementMsoResult](../-element-mso-result/index.md)&gt;)

## Constructors

| | |
|---|---|
| [DocumentValidity](-document-validity.md) | [androidJvm]<br>constructor(docType: [DocType](../../eu.europa.ec.eudi.verifier.core/-doc-type/index.md), isDeviceSignatureValid: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), isIssuerSignatureValid: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), isDataIntegrityIntact: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html), deviceAuthMethod: [DeviceAuthMethod](../-device-auth-method/index.md), msoValidity: [MsoValidity](../-mso-validity/index.md), elementMsoResults: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ElementMsoResult](../-element-mso-result/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [deviceAuthMethod](device-auth-method.md) | [androidJvm]<br>val [deviceAuthMethod](device-auth-method.md): [DeviceAuthMethod](../-device-auth-method/index.md) |
| [docType](doc-type.md) | [androidJvm]<br>val [docType](doc-type.md): [DocType](../../eu.europa.ec.eudi.verifier.core/-doc-type/index.md) |
| [elementMsoResults](element-mso-results.md) | [androidJvm]<br>val [elementMsoResults](element-mso-results.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ElementMsoResult](../-element-mso-result/index.md)&gt; |
| [isDataIntegrityIntact](is-data-integrity-intact.md) | [androidJvm]<br>val [isDataIntegrityIntact](is-data-integrity-intact.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [isDeviceSignatureValid](is-device-signature-valid.md) | [androidJvm]<br>val [isDeviceSignatureValid](is-device-signature-valid.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [isIssuerSignatureValid](is-issuer-signature-valid.md) | [androidJvm]<br>val [isIssuerSignatureValid](is-issuer-signature-valid.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [msoValidity](mso-validity.md) | [androidJvm]<br>val [msoValidity](mso-validity.md): [MsoValidity](../-mso-validity/index.md) |
