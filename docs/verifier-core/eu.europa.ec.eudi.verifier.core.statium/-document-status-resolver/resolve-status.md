//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.statium](../index.md)/[DocumentStatusResolver](index.md)/[resolveStatus](resolve-status.md)

# resolveStatus

[androidJvm]\
abstract suspend fun [resolveStatus](resolve-status.md)(response: [DeviceResponse](../../eu.europa.ec.eudi.verifier.core.response/-device-response/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;Status&gt;&gt;

Resolves the status of all documentsClaims in the given device response.

This process includes:

1. 
   Extracting document data from the CBOR-encoded response
2. 
   Parsing issuerAuth data to find status references
3. 
   Retrieving and validating status list tokens
4. 
   Determining the current status for each document

#### Return

A list of [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) objects containing the Status of each document or an error

#### Parameters

androidJvm

| | |
|---|---|
| response | The device response containing documentsClaims whose status needs to be resolved |
