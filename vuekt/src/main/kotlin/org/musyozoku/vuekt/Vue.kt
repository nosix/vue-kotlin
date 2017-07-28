// Vue 2.x [https://jp.vuejs.org/v2/api/]

@file:Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic", "unused")

package org.musyozoku.vuekt

import org.w3c.dom.HTMLElement

inline fun <T> this_(): T = js("this")

inline fun <T> JsObject(): T = js("({})")

inline fun <T> JsObject(init: T.() -> Unit): T = JsObject<T>().apply(init)

inline fun Vue(option: VueOption.() -> Unit) = Vue(JsObject(option))

external class Vue(option: VueOption) {

    companion object {
        val config: VueConfig
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

    // プロパティ
    var `$data`: Any // Object
    var `$props`: Any  // Object
    var `$el`: HTMLElement
    var `$options`: Any // Object
    var `$parent`: Vue
    var `$root`: Vue
    var `$children`: Array<Vue>
    var `$slots`: Any // { [name: string]: ?Array<VNode> }
    var `$scopedSlots`: Any // { [name: string]: props => VNode | Array<VNode> }
    var `$refs`: Any // Object
    var `$isServer`: Boolean
    var `$attrs`: Any // { [key: string]: string }
    var `$listeners`: Any // { [key: string]: Function | Array<Function> }

    // データ
    fun `$watch`(expOrFn: Any, callback: Any, options: Any? = definedExternally): () -> Unit // {string | Function} expOrFn, {Function | Object} callback, {Object} [options]
    fun `$set`(target: Any, key: Any, value: Any): Any // {Object | Array} target, {string | number} key, {any} value
    fun `$delete`(target: Any, key: Any) // {Object | Array} target, {string | number} key

    // イベント
    fun `$on`(event: Any, callback: Function<Unit>) // {string | Array<string>} event
    fun `$once`(event: String, callback: Function<Unit>)
    fun `$off`(event: String? = definedExternally, callback: Function<Unit>? = definedExternally)
    fun `$emit`(event: String, vararg args: Any)

    // ライフサイクル
    fun `$mount`(elementOrSelector: Any? = definedExternally, hydrating: Boolean? = definedExternally) // {Element | string} [elementOrSelector]
    fun `$forceUpdate`()
    fun `$nextTick`(callback: Function<Unit>? = definedExternally)
    fun `$destroy`()
}

external interface VueConfig {

    val silent: Boolean
    val optionMergeStrategies: Any // { [key: string]: Function }
    val devtools: Boolean
    val errorHandler: Function<Unit>
    val warnHandler: Function<Unit>
    val ignoredElements: Array<String>
    val keyCodes: Any // { [key: string]: number | Array<number> }
    val performance: Boolean
    val productionTip: Boolean
}

external interface VueOption {
    // データ
    var data: Any // Object | Function
    var props: Any // Array<string> | Object
    var propsData: Any // { [key: string]: any }
    var computed: Any // { [key: string]: Function | { get: Function, set: Function } }
    var methods: Any // { [key: string]: Function }
    var watch: Any // { [key: string]: string | Function | Object }
    // DOM
    var el: String // string | HTMLElement
    var template: String
    var render: Any // (createElement: () => VNode) => VNode
    var rendarError: Any // (createElement: () => VNode, error: Error) => VNode
    // ライフサイクルフック
    var beforeCreate: Any // Function
    var created: Any // Function
    var beforeMount: Any // Function
    var mounted: Any // Function
    var beforeUpdate: Any // Function
    var updated: Any // Function
    var activated: Any // Function
    var deactivated: Any // Function
    var beforeDestroy: Any // Function
    var destroyed: Any // Funtion
    // アセット
    var directives: Any
    var filters: Any
    var components: Any
    // 構成
    var parent: Vue
    var mixins: Array<Any>
    var extends: Any // Object | Function
    var provide: Any // Object | () => Object
    var inject: Any // Array<string> | { [key: string]: string | Symbol }
    // その他
    var name: String
    var delimiters: Array<String>
    var functional: Boolean
    var model: Any // { prop?: string, event?: string }
    var inheritAttrs: Boolean
    var comments: Boolean
}

external interface ComponentDefinition {

    var template: String
    var props: Array<String>
}

