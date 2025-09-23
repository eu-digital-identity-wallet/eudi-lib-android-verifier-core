//[verifier-core](../../../../index.md)/[eu.europa.ec.eudi.verifier.core.transfer](../../index.md)/[TransferConfig](../index.md)/[Builder](index.md)/[build](build.md)

# build

[androidJvm]\
fun [build](build.md)(): [TransferConfig](../index.md)

Builds a [TransferConfig](../index.md) instance with the current configuration.

#### Return

A new [TransferConfig](../index.md) instance.

#### Throws

| | |
|---|---|
| [IllegalStateException](https://developer.android.com/reference/kotlin/java/lang/IllegalStateException.html) | if no engagement methods are configured, or if a BLE connection is configured incorrectly (e.g., no supported modes or missing UUIDs) |
