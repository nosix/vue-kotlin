@file:Suppress("unused", "NOTHING_TO_INLINE", "UnsafeCastFromDynamic")

package org.musyozoku.vuekt

typealias PluginFunction<T> = (Vue: Any /* typeof Vue */, options: T?) -> Unit

external interface PluginObject<T> : TypedMap<Any?> {
    var install: PluginFunction<T>
}

/**
 * `PluginObject | PluginFunction`
 */
external interface PluginConfig

inline fun <T> PluginConfig(value: PluginObject<T>): PluginConfig = value.asDynamic()
inline fun <T> PluginConfig(value: PluginFunction<T>): PluginConfig = value.asDynamic()

inline fun <T> PluginConfig.toObject(): PluginObject<T> = this.asDynamic()
inline fun <T> PluginConfig.toFunction(): PluginFunction<T> = this.asDynamic()
