@file:Suppress("unused", "UnsafeCastFromDynamic", "NOTHING_TO_INLINE")

package org.musyozoku.vuekt

import org.w3c.dom.HTMLElement
import kotlin.js.Json
import kotlin.js.Promise

/**
 * `new (varargs args: Any): Any`
 */
typealias Constructor = Function<Any>

object js {
    val String: Constructor = js("String")
    val Number: Constructor = js("Number")
    val Boolean: Constructor = js("Boolean")
    val Function: Constructor = js("Function")
    val Object: Constructor = js("Object")
    val Array: Constructor = js("Array")
    val Symbol: Constructor = js("Symbol")
}

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
    var props: Props?
    var propsData: Json?
    var computed: JsonOf<ComputedConfig<*>>? // { [key: String]: V.() -> Any | ComputedOptions }
    var methods: JsonOf<Function<Any?>?>? // { [key: String]: V.(args: Array<Any>) -> Any }
    var watch: JsonOf<Watcher?>? // { [key: String]: String | WatchHandler<V, Any> | ({ handler: WatchHandler<V, Any> } & WatchOptions) }
    // DOM
    var el: ElementConfig?
    var template: String?
    var render: RenderFunction? // V.(createElement: CreateElement) -> VNode
    var renderError: RenderErrorFunction?
    var staticRenderFns: Array<RenderFunction>?
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
    var directives: JsonOf<DirectiveConfig?>? // { [key: String]: DirectiveOptions | DirectiveFunction }
    var components: JsonOf<ComponentOrAsyncComponent?>? // { [key: String]: Component | AsyncComponent }
    var transitions: JsonOf<Json>? // { [key: String]: Object }
    var filters: JsonOf<Function<Any>?>? // { [key: String]: Function }
    // Composition
    var provide: ObjectOrFactory<Json>? // Object | () -> Object
    var inject: Any? // Array<String> | { [key: String]: String | Symbol }
    var parent: Vue?
    var mixins: Array<Any>? // Array<ComponentOptions | typeof Vue>
    var extends: Any? // ComponentOptions | typeof Vue
    // Misc
    var model: ModelOptions?
    var name: String?
    var delimiters: Delimiter?
    var comments: Boolean?
    var inheritAttrs: Boolean?
}

external interface FunctionalComponentOptions {
    var name: String?
    var props: Props?
    var functional: Boolean
    var render: (createElement: CreateElement, context: RenderContext) -> VNode? // VNode | Unit
}

external interface RenderContext {
    var props: Any
    var children: Array<VNode>
    var slots: () -> Any
    var data: VNodeData
    var parent: Vue
    var injections: Any
}

external interface PropOptions<T> {
    var type: TypeConfig?
    var required: Boolean?
    var default: T?
    var validator: ((value: T) -> Boolean)?
}

external interface ComputedOptions<T> {
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

inline fun <T> ObjectOrFactory(json: T): ObjectOrFactory<T> = json.asDynamic()
inline fun <T> ObjectOrFactory(factory: () -> T): ObjectOrFactory<T> = factory.asDynamic()

inline fun <T> ObjectOrFactory<T>.toObject(): T = this.asDynamic()
inline fun <T> ObjectOrFactory<T>.toFactory(): () -> T = this.asDynamic()

/**
 * `Array<String> | { [propertyName: String]: PropOptions | Constructor | Array<Constructor> }`
 */
external interface Props

inline fun Props(propNames: Array<String>): Props = propNames.asDynamic()
inline fun Props(propConfig: JsonOf<PropConfig?>): Props = propConfig.asDynamic()

inline fun Props.toNames(): Array<String> = this.asDynamic()
inline fun Props.toConfig(): JsonOf<PropConfig?> = this.asDynamic()

/**
 * `PropOptions | Constructor | Array<Constructor>`
 */
external interface PropConfig

inline fun <T> PropConfig(options: PropOptions<T>): PropConfig = options.asDynamic()
inline fun PropConfig(constructor: Constructor): PropConfig = constructor.asDynamic()
inline fun PropConfig(constructors: Array<Constructor>): PropConfig = constructors.asDynamic()

inline fun <T> PropConfig.toOptions(): PropOptions<T> = this.asDynamic()
inline fun PropConfig.toConstructor(): Constructor = this.asDynamic()
inline fun PropConfig.toConstructorList(): Array<Constructor> = this.asDynamic()

/**
 * `ComputedOptions<T> | () -> T`
 */
external interface ComputedConfig<T>

inline fun <T> ComputedConfig(factory: () -> T): ComputedConfig<T> = factory.asDynamic()
inline fun <T> ComputedConfig(options: ComputedOptions<T>): ComputedConfig<T> = options.asDynamic()

inline fun <T> ComputedConfig<T>.toFactory(): () -> T = this.asDynamic()
inline fun <T> ComputedConfig<T>.toOptions(): ComputedOptions<T> = this.asDynamic()

/**
 * `String | WatchHandler<V, Any> | ({ handler: WatchHandler<V, Any> } & WatchOptions)`
 */
external interface Watcher

inline fun Watcher(methodName: String): Watcher = methodName.asDynamic()
inline fun <T> Watcher(handler: WatchHandler<T>): Watcher = handler.asDynamic()
inline fun <T> Watcher(options: WatchHandlerOptions<T>): Watcher = options.asDynamic()

inline fun Watcher.toMethodName(): String = this.asDynamic()
inline fun <T> Watcher.toHandler(): WatchHandler<T> = this.asDynamic()
inline fun <T> Watcher.toOptions(): WatchHandlerOptions<T> = this.asDynamic()

/**
 * `{ handler: WatchHandler<V, Any> } & WatchOptions`
 */
external interface WatchHandlerOptions<T> : WatchOptions {
    var handler: WatchHandler<T>
}

/**
 * `String | HTMLElement`
 */
external interface ElementConfig

inline fun ElementConfig(selector: String): ElementConfig = selector.asDynamic()
inline fun ElementConfig(element: HTMLElement): ElementConfig = element.asDynamic()

inline fun ElementConfig.toSelector(): String = this.asDynamic()
inline fun ElementConfig.toElement(): HTMLElement = this.asDynamic()

/**
 * `(createElement: CreateElement) -> VNode`
 */
typealias RenderFunction = (createElement: CreateElement) -> VNode

/**
 * `(createElement: CreateElement, error: Error) -> VNode`
 */
typealias RenderErrorFunction = (createElement: CreateElement, error: Error) -> VNode

/**
 * `V.() -> Unit`
 */
typealias LifecycleHookFunction = () -> Unit

/**
 * `DirectiveOptions | DirectiveFunction`
 */
external interface DirectiveConfig

inline fun DirectiveConfig(options: DirectiveOptions): DirectiveConfig = options.asDynamic()
inline fun DirectiveConfig(function: DirectiveFunction): DirectiveConfig = function.asDynamic()

inline fun DirectiveConfig.toOptions(): DirectiveOptions = this.asDynamic()
inline fun DirectiveConfig.toFunction(): DirectiveFunction = this.asDynamic()

/**
 * `Component | AsyncComponent`
 */
external interface ComponentOrAsyncComponent

inline fun ComponentOrAsyncComponent(component: Component): ComponentOrAsyncComponent = component.asDynamic()
inline fun ComponentOrAsyncComponent(component: AsyncComponent): ComponentOrAsyncComponent = component.asDynamic()

inline fun ComponentOrAsyncComponent.toComponent(): Component = this.asDynamic()
inline fun ComponentOrAsyncComponent.toAsyncComponent(): Component = this.asDynamic()

external interface ModelOptions {
    var prop: String?
    var event: String?
}

/**
 * `[String, String]`
 */
typealias Delimiter = Array<String>

inline fun Delimiter(begin: String, end: String) = arrayOf(begin, end)

/**
 * `Constructor | Array<Constructor> | null`
 */
external interface TypeConfig

inline fun TypeConfig(constructor: Constructor): TypeConfig = constructor.asDynamic()
inline fun TypeConfig(constructors: Array<Constructor>): TypeConfig = constructors.asDynamic()

inline fun TypeConfig.toConstructor(): Constructor = this.asDynamic()
inline fun TypeConfig.toConstructorList(): Array<Constructor> = this.asDynamic()
