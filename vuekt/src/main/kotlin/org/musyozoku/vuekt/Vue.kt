// Vue 2.x [https://vuejs.org/v2/api/]

@file:Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic", "unused")

package org.musyozoku.vuekt

import org.w3c.dom.HTMLElement

external interface JsObject

inline fun <T> JsThis(): T = js("this")

inline fun <T: JsObject> JsObject(): T = js("({})")

inline fun <T: JsObject> JsObject(init: T.() -> Unit): T = JsObject<T>().apply(init)

inline fun Vue(option: VueOption.() -> Unit) = Vue(JsObject(option))

external class Vue(option: VueOption) {

    companion object {
        // Global Config
        val config: VueConfig
        // Global API
        fun extend(options: Any)
        fun nextTick(callback: Function<Unit>? = definedExternally, context: Any? = definedExternally)
        fun set(target: Any, key: Any, value: Any) // {Object | Array} target, {string | number} key, {any} value
        fun delete(target: Any, key: Any) // {Object | Array} target, {string | number} key/index
        fun directive(id: String, definition: Any? = definedExternally) // {Function | Object} [definition]
        fun filter(id: String, definition: Any? = definedExternally) // {Function | Object} [definition]
        fun component(id: String, definition: Any? = definedExternally) // {Function | Object} [definition]
        fun use(plugin: Any) // {Object | Function} plugin
        fun mixin(mixin: Any)
        fun compile(template: String)
        val version: String
    }

    // Instance Properties
    var `$data`: JsObject
    var `$props`: JsObject
    var `$el`: HTMLElement
    var `$options`: JsObject
    var `$parent`: Vue
    var `$root`: Vue
    var `$children`: Array<Vue>
    var `$slots`: JsObject // { [name: string]: ?Array<VNode> }
    var `$scopedSlots`: JsObject // { [name: string]: props => VNode | Array<VNode> }
    var `$refs`: JsObject
    var `$isServer`: Boolean
    var `$attrs`: JsObject // { [key: string]: string }
    var `$listeners`: JsObject // { [key: string]: Function | Array<Function> }

    // Instance Methods / Data
    fun `$watch`(expOrFn: Any, callback: Any, options: JsObject? = definedExternally): () -> Unit // {string | Function} expOrFn, {Function | Object} callback, {Object} [options]
    fun `$set`(target: Any, key: Any, value: Any): Any // {Object | Array} target, {string | number} key, {any} value
    fun `$delete`(target: Any, key: Any) // {Object | Array} target, {string | number} key

    // Instance Methods / Event
    fun `$on`(event: Any, callback: Function<Unit>) // {string | Array<string>} event
    fun `$once`(event: String, callback: Function<Unit>)
    fun `$off`(event: String? = definedExternally, callback: Function<Unit>? = definedExternally)
    fun `$emit`(event: String, vararg args: Any)

    // Instance Methods / Lifecycle
    fun `$mount`(elementOrSelector: Any? = definedExternally, hydrating: Boolean? = definedExternally) // {Element | string} [elementOrSelector]
    fun `$forceUpdate`()
    fun `$nextTick`(callback: Function<Unit>? = definedExternally)
    fun `$destroy`()
}

// Global Config
external interface VueConfig: JsObject {

    val silent: Boolean
    val optionMergeStrategies: JsObject // { [key: string]: Function }
    val devtools: Boolean
    val errorHandler: (Any, Vue, String) -> Unit
    val warnHandler: (String, Vue, String) -> Unit
    val ignoredElements: Array<String>
    val keyCodes: JsObject // { [key: string]: number | Array<number> }
    val performance: Boolean
    val productionTip: Boolean
}

// Options
external interface VueOption: JsObject {
    // Data
    var data: Any // Object | Function
    var props: Any // Array<string> | Object
    var propsData: JsObject // { [key: string]: any }
    var computed: JsObject // { [key: string]: Function | { get: Function, set: Function } }
    var methods: JsObject // { [key: string]: Function }
    var watch: JsObject // { [key: string]: string | Function | Object }
    // DOM
    var el: Any // string | HTMLElement
    var template: String
    var render: Any // (createElement: () => VNode) => VNode
    var rendarError: Any // (createElement: () => VNode, error: Error) => VNode
    // Lifecycle Hooks
    var beforeCreate: () -> Unit
    var created: () -> Unit
    var beforeMount: () -> Unit
    var mounted: () -> Unit
    var beforeUpdate: () -> Unit
    var updated: () -> Unit
    var activated: () -> Unit
    var deactivated: () -> Unit
    var beforeDestroy: () -> Unit
    var destroyed: () -> Unit
    // Assets
    var directives: JsObject
    var filters: JsObject
    var components: JsObject
    // Composition
    var parent: Vue
    var mixins: Array<JsObject>
    var extends: Any // Object | Function
    var provide: Any // Object | () => Object
    var inject: Any // Array<string> | { [key: string]: string | Symbol }
    // Misc
    var name: String
    var delimiters: Array<String>
    var functional: Boolean
    var model: ModelOption
    var inheritAttrs: Boolean
    var comments: Boolean
}

external interface ModelOption: JsObject {

    var prop: String
    var event: String
}

external interface ComponentDefinition: JsObject {

    var template: String
    var props: Array<String>
}

