//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core](../../index.md)/[EudiVerifier](../index.md)/[Companion](index.md)/[invoke](invoke.md)

# invoke

[androidJvm]\
operator fun [invoke](invoke.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), config: [EudiVerifierConfig](../../-eudi-verifier-config/index.md), extraConfiguration: [EudiVerifier.Builder](../-builder/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)? = null): [EudiVerifier](../index.md)

Creates a new [EudiVerifier](../index.md) instance with the given context and configuration.

#### Return

Configured [EudiVerifier](../index.md) instance.

#### Parameters

androidJvm

| | |
|---|---|
| context | Android context for resource access. |
| config | Configuration for the verifier. |
| extraConfiguration | Optional lambda to perform additional builder configuration. |
