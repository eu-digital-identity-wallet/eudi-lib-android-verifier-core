//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[DocumentStatusResolverImpl](index.md)/[resolveStatus](resolve-status.md)

# resolveStatus

[androidJvm]\
open suspend override fun [resolveStatus](resolve-status.md)(response: [DeviceResponse](../../eu.europa.ec.eudi.verifier.core.response/-device-response/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;Status&gt;&gt;

Resolves the status of all documentsClaims in the given device response.

#### Return

A list of [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) objects with the resolved status of each document

#### Parameters

androidJvm

| | |
|---|---|
| response | The device response containing CBOR-encoded document data |
