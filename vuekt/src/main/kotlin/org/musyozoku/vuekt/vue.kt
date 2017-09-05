// Vue 2.x [https://vuejs.org/v2/api/]

@file:Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic", "unused")

package org.musyozoku.vuekt

import org.w3c.dom.HTMLElement
import kotlin.js.*

external val `this`: dynamic

inline fun <T : Any> thisAs(): T = `this`

/**
 * `??? -> VNode`
 */
typealias CreateElement = Function<VNode>

/**
 * Example:
 *
 * ```
 * @JsModule("vue")
 * @JsNonModule
 * @JsName("Vue")
 * external class MyVue(options: ComponentOptions<MyVue>) : Vue {
 *     var message: String
 * }
 * ```
 */
@JsModule("vue")
@JsNonModule
external open class Vue(options: ComponentOptions<Vue>? = definedExternally) {

    companion object {
        // Global Config
        val config: VueConfig
        // Global API
        fun extend(options: Any /* ComponentOptions<Vue> | FunctionalComponentOptions */): Any // typeof Vue

        fun nextTick(callback: () -> Unit, context: Array<Any>? = definedExternally)
        fun nextTick(): Promise<Unit>
        fun <T> set(target: Json, key: String, value: T): T
        fun <T> set(target: Array<T>, key: Int, value: T): T
        fun delete(target: Json, key: String)
        fun <T> delete(target: Array<T>, key: Int)
        fun directive(id: String, definition: Any? /* DirectiveOption | DirectiveFunction */ = definedExternally): DirectiveOptions
        fun filter(id: String, definition: Function<Unit>? = definedExternally): Function<Unit>
        fun component(id: String, definition: Component? = definedExternally): Any // typeof Vue
        fun component(id: String, definition: AsyncComponent? = definedExternally): Any // typeof Vue
        fun <T> use(plugin: Any /* PluginObject | PluginFunction */, options: T?)
        fun mixin(mixin: Any /* typeof Vue | ComponentOptions */)
        fun compile(template: String)
        val version: String
    }

    // Instance Properties
    var `$data`: Vue
    val `$el`: HTMLElement
    val `$options`: ComponentOptions<Vue>
    val `$parent`: Vue
    val `$root`: Vue
    val `$children`: Array<Vue>
    val `$refs`: RefMap // { [key: String]: Vue | Element | Array<Vue> | Array<Element>}
    val `$slots`: Json // { [key: String]: Array<VNode> }
    val `$scopedSlots`: Json // { [key: String]: ScopedSlot }
    val `$isServer`: Boolean
    val `$ssrContext`: Any
    val `$props`: Any
    val `$vnode`: VNode
    val `$attrs`: Any // { [key: String]: String } | Unit
    val `$listeners`: Any // { [key: String]: Function | Array<Function> } | Unit

    var `$createElement`: CreateElement

    // Instance Methods / Data
    fun <T> `$watch`(
            exp: String,
            callback: WatchHandler<T>,
            options: WatchOptions? = definedExternally): () -> Unit

    fun <T> `$watch`(
            fn: () -> T,
            callback: WatchHandler<T>,
            options: WatchOptions? = definedExternally): () -> Unit

    fun <T> `$set`(target: Json, key: String, value: T): T
    fun <T> `$set`(target: Array<T>, key: Int, value: T): T
    fun `$delete`(target: Json, key: String)
    fun <T> `$delete`(target: Array<T>, key: Int)

    // Instance Methods / Event
    fun `$on`(event: String, callback: Function<Unit>): Vue // -> this

    fun `$on`(event: Array<String>, callback: Function<Unit>): Vue // -> this
    fun `$once`(event: String, callback: Function<Unit>): Vue // -> this
    fun `$off`(event: String? = definedExternally, callback: Function<Unit>? = definedExternally): Vue // -> this
    fun `$off`(event: Array<String>? = definedExternally, callback: Function<Unit>? = definedExternally): Vue // -> this
    fun `$emit`(event: String, vararg args: Any): Vue // -> this

    // Instance Methods / Lifecycle
    fun `$mount`(elementOrSelector: Any? /* Element | String */ = definedExternally,
                 hydrating: Boolean? = definedExternally): Vue // -> this
    fun `$forceUpdate`()
    fun `$destroy`()
    fun `$nextTick`(callback: () -> Unit) // V.() -> Unit
    fun `$nextTick`(): Promise<Unit>
}

external interface VueConfig {

    val silent: Boolean
    val optionMergeStrategies: Any // { [key: String]: Function }
    val devtools: Boolean
    val productionTip: Boolean
    val performance: Boolean
    val errorHandler: (err: Error, vm: Vue, info: String) -> Unit
    val warnHandler: (msg: String, vm: Vue, trace: String) -> Unit
    val ignoredElements: Array<String>
    val keyCodes: Json // { [key: String]: Number }
}

external interface CompileResult {
    fun render(createElement: Any /* typeof Vue.prototype.$createElement */): VNode
    var staticRenderFns: Array<() -> VNode>
}

/**
 * `{ [propertyName: String]: Vue | Element | Array<Vue> | Array<Element> }`
 */
external interface RefMap

inline operator fun RefMap.get(propertyName: String): Ref? = this.asDynamic()[propertyName]
inline operator fun RefMap.set(propertyName: String, value: Ref?) {
    this.asDynamic()[propertyName] = value
}

/**
 * `Vue | Element | Array<Vue> | Array<Element>`
 */
external interface Ref

inline fun Ref(value: Vue): Ref = value.asDynamic()
inline fun Ref(value: HTMLElement): Ref = value.asDynamic()
inline fun Ref(value: Array<Vue>): Ref = value.asDynamic()
inline fun Ref(value: Array<HTMLElement>): Ref = value.asDynamic()

inline fun Ref.toVue(): Vue = this.asDynamic()
inline fun Ref.toHTMLElement(): HTMLElement = this.asDynamic()
inline fun Ref.toVueList(): Array<Vue> = this.asDynamic()
inline fun Ref.toHTMLElementList(): Array<HTMLElement> = this.asDynamic()
