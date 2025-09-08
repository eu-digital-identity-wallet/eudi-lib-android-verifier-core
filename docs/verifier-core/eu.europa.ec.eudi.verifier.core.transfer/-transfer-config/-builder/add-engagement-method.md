//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../../index.md)/[TransferConfig](../index.md)/[Builder](index.md)/[addEngagementMethod](add-engagement-method.md)

# addEngagementMethod

[androidJvm]\
fun [addEngagementMethod](add-engagement-method.md)(method: [TransferConfig.EngagementMethod](../-engagement-method/index.md), vararg connectionMethods: MdocConnectionMethod): &lt;Error class: unknown class&gt;

Adds an engagement method with its supported connection methods.

#### Return

This builder instance for method chaining.

#### Parameters

androidJvm

| | |
|---|---|
| method | The engagement method to add. |
| connectionMethods | The connection methods supported by this engagement method. |

[androidJvm]\
fun [addEngagementMethod](add-engagement-method.md)(method: [TransferConfig.EngagementMethod](../-engagement-method/index.md), connectionMethods: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;MdocConnectionMethod&gt;): &lt;Error class: unknown class&gt;

Adds an engagement method with its supported connection methods.

#### Return

This builder instance for method chaining.

#### Parameters

androidJvm

| | |
|---|---|
| method | The engagement method to add. |
| connectionMethods | The list of connection methods supported by this engagement method. |
