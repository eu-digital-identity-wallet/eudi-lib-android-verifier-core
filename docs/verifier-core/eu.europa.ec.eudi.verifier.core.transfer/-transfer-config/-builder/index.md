//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../../index.md)/[TransferConfig](../index.md)/[Builder](index.md)

# Builder

[androidJvm]\
class [Builder](index.md)

Builder class for creating instances of [TransferConfig](../index.md). Provides a fluent interface for configuration.

## Constructors

| | |
|---|---|
| [Builder](-builder.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [addEngagementMethod](add-engagement-method.md) | [androidJvm]<br>fun [addEngagementMethod](add-engagement-method.md)(method: [TransferConfig.EngagementMethod](../-engagement-method/index.md), vararg connectionMethods: MdocConnectionMethod): &lt;Error class: unknown class&gt;<br>fun [addEngagementMethod](add-engagement-method.md)(method: [TransferConfig.EngagementMethod](../-engagement-method/index.md), connectionMethods: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;MdocConnectionMethod&gt;): &lt;Error class: unknown class&gt;<br>Adds an engagement method with its supported connection methods. |
| [build](build.md) | [androidJvm]<br>fun [build](build.md)(): [TransferConfig](../index.md)<br>Builds a [TransferConfig](../index.md) instance with the current configuration. |
