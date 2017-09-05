@file:Suppress("unused", "NOTHING_TO_INLINE", "UnsafeCastFromDynamic")

package org.musyozoku.vuekt

import org.w3c.dom.HTMLElement
import kotlin.js.Json
import kotlin.js.Promise

/**
 * `new (varargs args: Any): Any`
 */
typealias Constructor = Function<Any>

/**
 * `typeof Vue | ComponentOptions | FunctionalComponentOptions`
 */
external interface Component

inline fun Component(value: Any): Component = value.asDynamic() // TODO change Any
inline fun <V : Vue> Component(value: ComponentOptions<V>): Component = value.asDynamic()
inline fun Component(value: FunctionalComponentOptions): Component = value.asDynamic()

inline fun Component.toTypeOfVue(): Any = this.asDynamic() // TODO change Any
inline fun <V : Vue> Component.toComponentOptions(): ComponentOptions<V> = this.asDynamic()
inline fun Component.toFunctionalComponentOptions(): FunctionalComponentOptions = this.asDynamic()

typealias AsyncComponent = (resolve: (component: Component) -> Unit, reject: (reason: Any?) -> Unit) -> AsyncComponentResult

/**
 * `Promise<Component> | Component | Unit`
 */
external interface AsyncComponentResult

inline fun AsyncComponentResult(value: Promise<Component>): AsyncComponentResult = value.asDynamic()
inline fun AsyncComponentResult(value: Component): AsyncComponentResult = value.asDynamic()

inline fun AsyncComponentResult.toPromiseComponent(): Promise<Component> = this.asDynamic()
inline fun AsyncComponentResult.toComponent(): Component = this.asDynamic()

external interface ComponentOptions<V : Vue> {
    // Data
    var data: ObjectOrFactory<V>? // Object | V.() -> Object
    var props: Props? // Array<String> | { [key: String]: PropOptions | Constructor | Array<Constructor> }
    var propsData: Json? // Object
    var computed: ComputedMap? // { [key: String]: V.() -> Any | ComputedOptions }
    var methods: FunctionMap? // { [key: String]: V.(args: Array<Any>) -> Any }
    var watch: WatchMap? // { [key: String]: String | WatchHandler<V, Any> | ({ handler: WatchHandler<V, Any> } & WatchOptions) }
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
    var components: ComponentMap? // { [key: String]: Component | AsyncComponent }
    var transitions: Json? // { [key: String]: Object }
    var filters: FunctionMap? // { [key: String]: Function }
    // Composition
    var provide: Any? // Object | () -> Object
    var inject: Any? // Array<String> | { [key: String]: String | Symbol }
    var parent: Vue?
    var mixins: Array<Any>? // Array<ComponentOptions | typeof Vue>
    var extends: Any? // ComponentOptions | typeof Vue
    // Misc
    var model: ModelOptions?
    var name: String?
    var delimiters: Array<String> // [String, String]
    var comments: Boolean?
    var inheritAttrs: Boolean?
}

external interface FunctionalComponentOptions {
    var name: String?
    var props: Any? // Array<String> | { [key: String]: PropOptions | Constructor | Array<Constructor> }
    var functional: Boolean
    fun render(createElement: CreateElement, context: RenderContext): Any // VNode | Unit
}

external interface RenderContext {
    var props: Any
    var children: Array<VNode>
    fun slots(): Any
    var data: VNodeData
    var parent: Vue
    var injections: Any
}

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

/**
 * `V.(value: T, oldValue: T) -> Unit`
 */
typealias WatchHandler<T> = (value: T, oldValue: T) -> Unit

external interface WatchOptions {
    var deep: Boolean?
    var immediate: Boolean?
}

typealias DirectiveFunction = (el: HTMLElement, binding: VNodeDirective, vnode: VNode, oldVnode: VNode) -> Unit

external interface DirectiveOptions {
    var bind: DirectiveFunction?
    var inserted: DirectiveFunction?
    var update: DirectiveFunction?
    var componentUpdated: DirectiveFunction?
    var unbind: DirectiveFunction?
}

/**
 * `T | () -> T`
 */
external interface ObjectOrFactory<T>

inline fun <T> ObjectOrFactory(value: T): ObjectOrFactory<T> = value.asDynamic()
inline fun <T> ObjectOrFactory(value: () -> T): ObjectOrFactory<T> = value.asDynamic()

inline fun <T> ObjectOrFactory<T>.toObject(): T = this.asDynamic()
inline fun <T> ObjectOrFactory<T>.toFactory(): () -> T = this.asDynamic()

/**
 * `Array<String> | { [propertyName: String]: PropOptions | Constructor | Array<Constructor> }`
 */
external interface Props

inline fun Props(value: Array<String>): Props = value.asDynamic()
inline fun Props(value: PropMap): Props = value.asDynamic()

inline fun Props.toList(): Array<String> = this.asDynamic()
inline fun Props.toMap(): PropMap = this.asDynamic()

/**
 * `{ [propertyName: String]: PropOptions | Constructor | Array<Constructor> }`
 */
external interface PropMap

inline operator fun PropMap.get(propertyName: String): PropConfig = this.asDynamic()[propertyName]
inline operator fun PropMap.set(propertyName: String, value: PropConfig) {
    this.asDynamic()[propertyName] = value
}

/**
 * `PropOptions | Constructor | Array<Constructor>`
 */
external interface PropConfig

inline fun PropConfig(value: PropOptions): PropConfig = value.asDynamic()
inline fun PropConfig(value: Constructor): PropConfig = value.asDynamic()
inline fun PropConfig(value: Array<Constructor>): PropConfig = value.asDynamic()

inline fun PropConfig.toOptions(): PropOptions = this.asDynamic()
inline fun PropConfig.toConstructor(): Constructor = this.asDynamic()
inline fun PropConfig.toConstructorList(): Array<Constructor> = this.asDynamic()

/**
 * `{ [propertyName: String]: () -> T | ComputedOptions<T> }`
 */
external interface ComputedMap

inline operator fun <T> ComputedMap.get(propertyName: String): ComputedConfig<T>? = this.asDynamic()[propertyName]
inline operator fun <T> ComputedMap.set(propertyName: String, value: ComputedConfig<T>?) {
    this.asDynamic()[propertyName] = value
}

/**
 * `ComputedOptions<T> | () -> T`
 */
external interface ComputedConfig<T>

inline fun <T> ComputedConfig(value: () -> T): ComputedConfig<T> = value.asDynamic()
inline fun <T> ComputedConfig(value: ComputedOptions<T>): ComputedConfig<T> = value.asDynamic()

inline fun <T> ComputedConfig<T>.toFactory(): () -> T = this.asDynamic()
inline fun <T> ComputedConfig<T>.toOptions(): ComputedOptions<T> = this.asDynamic()

/**
 * `{ [propertyName: String]: (varargs args: Any) -> Any? }`
 */
external interface FunctionMap

inline operator fun FunctionMap.get(propertyName: String): Function<Any?>? = this.asDynamic()[propertyName]
inline operator fun FunctionMap.set(propertyName: String, value: Function<Any?>?) {
    this.asDynamic()[propertyName] = value
}

/**
 * `{ [propertyName: String]: String | WatchHandler<V, Any> | ({ handler: WatchHandler<V, Any> } & WatchOptions) }`
 */
external interface WatchMap

inline operator fun WatchMap.get(propertyName: String): Watcher = this.asDynamic()[propertyName]
inline operator fun WatchMap.set(propertyName: String, value: Watcher) {
    this.asDynamic()[propertyName] = value
}

/**
 * `String | WatchHandler<V, Any> | ({ handler: WatchHandler<V, Any> } & WatchOptions)`
 */
external interface Watcher

inline fun Watcher(value: String): Watcher = value.asDynamic()
inline fun <T> Watcher(value: WatchHandler<T>): Watcher = value.asDynamic()
inline fun <T> Watcher(value: WatchHandlerOptions<T>): Watcher = value.asDynamic()

inline fun Watcher.toName(): String = this.asDynamic()
inline fun <T> Watcher.toHandler(): WatchHandler<T> = this.asDynamic()
inline fun <T> Watcher.toOptions(): WatchHandlerOptions<T> = this.asDynamic()

/**
 * `{ handler: WatchHandler<V, Any> } & WatchOptions`
 */
external interface WatchHandlerOptions<T> : WatchOptions {
    var handler: WatchHandler<T>
}

/**
 * `V.() -> Unit`
 */
typealias LifecycleHookFunction = () -> Unit

/**
 * `{ [propertyName: String]: Component | AsyncComponent }`
 */
external interface ComponentMap

inline operator fun ComponentMap.get(propertyName: String): ComponentOrAsyncComponent? = this.asDynamic()[propertyName]
inline operator fun ComponentMap.set(propertyName: String, value: ComponentOrAsyncComponent?) {
    this.asDynamic()[propertyName] = value
}

/**
 * `Component | AsyncComponent`
 */
external interface ComponentOrAsyncComponent

inline fun ComponentOrAsyncComponent(value: Component): ComponentOrAsyncComponent = value.asDynamic()
inline fun ComponentOrAsyncComponent(value: AsyncComponent): ComponentOrAsyncComponent = value.asDynamic()

inline fun ComponentOrAsyncComponent.toComponent(): Component = this.asDynamic()
inline fun ComponentOrAsyncComponent.toAsyncComponent(): Component = this.asDynamic()

external interface ModelOptions {
    var prop: String?
    var event: String?
}
