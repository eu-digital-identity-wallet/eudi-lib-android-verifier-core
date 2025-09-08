//[verifier-core](../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../index.md)/[TransferConfig](index.md)

# TransferConfig

[androidJvm]\
class [TransferConfig](index.md)

Configuration for the transfer process between verifier and holder devices. Defines the available engagement methods and their associated connection methods.

## Types

| Name | Summary |
|---|---|
| [Builder](-builder/index.md) | [androidJvm]<br>class [Builder](-builder/index.md)<br>Builder class for creating instances of [TransferConfig](index.md). Provides a fluent interface for configuration. |
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |
| [EngagementMethod](-engagement-method/index.md) | [androidJvm]<br>enum [EngagementMethod](-engagement-method/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-enum/index.html)&lt;[TransferConfig.EngagementMethod](-engagement-method/index.md)&gt; <br>Enumeration of available device engagement methods. |

## Properties

| Name | Summary |
|---|---|
| [engagementMethods](engagement-methods.md) | [androidJvm]<br>val [engagementMethods](engagement-methods.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[TransferConfig.EngagementMethod](-engagement-method/index.md), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;MdocConnectionMethod&gt;&gt;<br>Map of engagement methods to their supported connection methods. |
