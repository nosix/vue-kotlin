@file:Suppress("unused", "UnsafeCastFromDynamic", "NOTHING_TO_INLINE")

package org.musyozoku.vuekt

inline fun <T : Any> json(): T = js("({})")

fun <T : Any> json(init: T.() -> Unit): T = json<T>().apply(init)

fun <V : Vue> ComponentOptions(init: ComponentOptions<V>.() -> Unit): ComponentOptions<V> = json(init)
fun <T> ComputedOptions(init: ComputedOptions<T>.() -> Unit): ComputedOptions<T> = json(init)
fun ModelOptions(init: ModelOptions.() -> Unit): ModelOptions = json(init)
