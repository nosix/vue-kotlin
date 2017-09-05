@file:Suppress("unused", "NOTHING_TO_INLINE", "UnsafeCastFromDynamic")

package org.musyozoku.vuekt

import org.w3c.dom.HTMLElement
import kotlin.js.Json

external interface ComponentOptions<V : Vue> {
    // Data
    var data: ObjectOrFactory<V>? // Object | V.() -> Object
    var props: PropListOrPropMap? // Array<String> | { [key: String]: PropOptions | Constructor | Array<Constructor> }
    var propsData: Json? // Object
    var computed: ComputedMap? // { [key: String]: V.() -> Any | ComputedOptions }
    var methods: FunctionMap? // { [key: String]: V.(args: Array<Any>) -> Any }
    var watch: Json // { [key: String]: String | WatchHandler<V, Any> | ({ handler: WatchHandler<V, Any> } & WatchOptions) }
    // DOM
    var el: Any? // String | Element
    var template: String?
    var render: Any? // V.(createElement: CreateElement) -> VNode
    var rendarError: Any? // (createElement: CreateElement, error: Error) -> VNode
    var staticRenderFns: Any? // Array<(createElement: CreateElement) -> VNode>
    // Lifecycle Hooks
    var beforeCreate: LifecycleHookFunction?
    var created: LifecycleHookFunction?
    var beforeMount: LifecycleHookFunction?
    var mounted: LifecycleHookFunction?
    var beforeUpdate: LifecycleHookFunction?
    var updated: LifecycleHookFunction?
    var activated: LifecycleHookFunction?
    var deactivated: LifecycleHookFunction?
    var beforeDestroy: LifecycleHookFunction?
    var destroyed: LifecycleHookFunction?
    // Assets
    var directives: Json? // { [key: String]: DirectiveOptions | DirectiveFunction }
    var components: Json? // { [key: String]: Component | AsyncComponent }
    var transitions: Json? // { [key: String]: Object }
    var filters: FunctionMap? // { [key: String]: Function }
    // Composition
    var provide: Any? // Object | () -> Object
    var inject: Any? // Array<String> | { [key: String]: String | Symbol }
    var parent: Vue?
    var mixins: Array<Any>? // Array<ComponentOptions | typeof Vue>
    var extends: Any? // ComponentOptions | typeof Vue
    // Misc
    var model: ModelOption?
    var name: String?
    var delimiters: Array<String> // [String, String]
    var comments: Boolean?
    var inheritAttrs: Boolean?
}

typealias CreateElement = Function<Any> // ... -> VNode

typealias Constructor = Function<Any>

typealias WatchHandler<T> = (value: T, oldValue: T) -> Unit // V.(value: T, oldValue: T) -> Unit

typealias DirectiveFunction = (el: HTMLElement, binding: VNodeDirective, vnode: VNode, oldVnode: VNode) -> Unit

typealias Component = Any // typeof Vue | ComponentOptions | FunctionalComponentOptions

typealias AsyncComponent = (resolve: (component: Component) -> Unit, reject: (reason: Any?) -> Unit) -> Any // Promise<Component> | Component | Unit

typealias LifecycleHookFunction = () -> Unit // V.() -> Unit

external interface PropOptions {
    var type: Any? // Constructor | Array<Constructor> | null
    var required: Boolean?
    var default: Any?
    var validator: ((value: Any) -> Boolean)?
}

external interface ComputedOptions<T> { // <V : Vue>
    var get: (() -> T)? // V.() -> Any
    var set: ((value: T) -> Unit)? // V.(value: Any) -> Unit
    var cache: Boolean?
}

external interface WatchOptions {
    var deep: Boolean?
    var immediate: Boolean?
}

external interface DirectiveOptions {
    var bind: DirectiveFunction?
    var inserted: DirectiveFunction?
    var update: DirectiveFunction?
    var componentUpdated: DirectiveFunction?
    var unbind: DirectiveFunction?
}

external interface ModelOption {
    var prop: String?
    var event: String?
}

/**
 * T | () -> T
 */
external interface ObjectOrFactory<T>

inline fun <T> ObjectOrFactory(value: T): ObjectOrFactory<T> = value.asDynamic()
inline fun <T> ObjectOrFactory(value: () -> T): ObjectOrFactory<T> = value.asDynamic()

inline fun <T> ObjectOrFactory<T>.toObject(): T = this.asDynamic()
inline fun <T> ObjectOrFactory<T>.toFactory(): () -> T = this.asDynamic()

/**
 * Array<String> | { [propertyName: String]: PropOptions | Constructor | Array<Constructor> }
 */
external interface PropListOrPropMap

inline fun PropsListOrPropsMap(value: Array<String>): PropListOrPropMap = value.asDynamic()
inline fun PropsListOrPropsMap(value: PropMap): PropListOrPropMap = value.asDynamic()

inline fun PropListOrPropMap.toPropList(): Array<String> = this.asDynamic()
inline fun PropListOrPropMap.toPropMap(): PropMap = this.asDynamic()

/**
 * { [propertyName: String]: PropOptions | Constructor | Array<Constructor> }
 */
external interface PropMap

inline operator fun PropMap.get(propertyName: String): PropOptionsOrConstructor = this.asDynamic()[propertyName]
inline operator fun PropMap.set(propertyName: String, value: PropOptionsOrConstructor) {
    this.asDynamic()[propertyName] = value
}

/**
 * PropOptions | Constructor | Array<Constructor>
 */
external interface PropOptionsOrConstructor

inline fun PropOptionsOrConstructor(value: PropOptions): PropOptionsOrConstructor = value.asDynamic()
inline fun PropOptionsOrConstructor(value: Constructor): PropOptionsOrConstructor = value.asDynamic()
inline fun PropOptionsOrConstructor(value: Array<Constructor>): PropOptionsOrConstructor = value.asDynamic()

inline fun PropOptionsOrConstructor.toPropOptions(): PropOptions = this.asDynamic()
inline fun PropOptionsOrConstructor.toConstructor(): Constructor = this.asDynamic()
inline fun PropOptionsOrConstructor.toArrayConstructor(): Array<Constructor> = this.asDynamic()

/**
 * { [propertyName: String]: () -> T | ComputedOptions<T> }
 */
external interface ComputedMap

inline operator fun <T> ComputedMap.get(propertyName: String): ComputedOptionsOrFactory<T>? = this.asDynamic()[propertyName]
inline operator fun <T> ComputedMap.set(propertyName: String, value: ComputedOptionsOrFactory<T>?) {
    this.asDynamic()[propertyName] = value
}

/**
 * ComputedOptions<T> | () -> T
 */
external interface ComputedOptionsOrFactory<T>

inline fun <T> ComputedOptionsOrFactory(value: () -> T): ComputedOptionsOrFactory<T> = value.asDynamic()
inline fun <T> ComputedOptionsOrFactory(value: ComputedOptions<T>): ComputedOptionsOrFactory<T> = value.asDynamic()

inline fun <T> ComputedOptionsOrFactory<T>.toFactory(): () -> T = this.asDynamic()
inline fun <T> ComputedOptionsOrFactory<T>.toComputedOptions(): ComputedOptions<T> = this.asDynamic()

/**
 * { [propertyName: String]: (varargs args: Any) -> Any? }
 */
external interface FunctionMap

inline operator fun FunctionMap.get(propertyName: String): Function<Any?>? = this.asDynamic()[propertyName]
inline operator fun FunctionMap.set(propertyName: String, value: Function<Any?>?) {
    this.asDynamic()[propertyName] = value
}
