@file:Suppress("unused", "UnsafeCastFromDynamic", "NOTHING_TO_INLINE")

package org.musyozoku.vuekt

// Type is not `Json` because we do not want to have `get` and `set` functions.
inline fun <T : Any> json(): T = js("({})")

fun <T : Any> json(init: T.() -> Unit): T = json<T>().apply(init)

fun <V : Vue> ComponentOptions(init: ComponentOptions<V>.() -> Unit): ComponentOptions<V> = json(init)
fun FunctionalComponentOptions(init: FunctionalComponentOptions.() -> Unit): FunctionalComponentOptions = json(init)
fun RenderContext(init: RenderContext.() -> Unit): RenderContext = json(init)
fun <T> PropOptions(init: PropOptions<T>.() -> Unit): PropOptions<T> = json(init)
fun <T> ComputedOptions(init: ComputedOptions<T>.() -> Unit): ComputedOptions<T> = json(init)
fun WatchOptions(init: WatchOptions.() -> Unit): WatchOptions = json(init)
fun DirectiveOptions(init: DirectiveOptions.() -> Unit): DirectiveOptions = json(init)
fun <T> WatchHandlerOptions(init: WatchHandlerOptions<T>.() -> Unit): WatchHandlerOptions<T> = json(init)
fun ModelOptions(init: ModelOptions.() -> Unit): ModelOptions = json(init)
