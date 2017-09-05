@file:Suppress("unused", "UnsafeCastFromDynamic", "NOTHING_TO_INLINE")

package org.musyozoku.vuekt

import org.w3c.dom.Node
import kotlin.js.Json

external interface VNode {
    var tag: String?
    var data: VNodeData?
    var children: Array<VNode>?
    var text: String?
    var elm: Node?
    var ns: String?
    var context: Vue?
    var key: Key?
    var componentOptions: VNodeComponentOptions?
    var componentInstance: Vue?
    var parent: VNode?
    var raw: Boolean?
    var isStatic: Boolean?
    var isRootInsert: Boolean
    var isComment: Boolean
}

external interface VNodeData {
    var key: Key?
    var slot: String?
    var scopedSlots: ScopedSlotMap?
    var ref: String?
    var tag: String?
    var staticClass: String?
    var `class`: Any?
    var staticStyle: Json? // { [key: String]: Any }
    var style: OneOrMany<Json>? // Array<Object> | Object
    var props: Json? // { [key: String]: Any }
    var attrs: Json? // { [key: String]: Any }
    var domProps: Json? // { [key: String]: Any }
    var hook: JsonOf<Function<Any>>? // { [key: String]: Function }
    var on: JsonOf<OneOrMany<Function<Any>>>? // { [key: String]: Function | Array<Function> }
    var nativeOn: JsonOf<OneOrMany<Function<Any>>>? // { [key: String]: Function | Array<Function> }
    var transition: Json?
    var show: Boolean?
    var inlineTemplate: InlineTemplate?
    var directives: Array<VNodeDirective>?
    var keepAlive: Boolean?
}

external interface InlineTemplate {
    var render: Function<Unit>
    var staticRenderFns: Array<Function<Unit>>
}

external interface VNodeDirective {
    val name: String
    val value: Any
    val oldValue: Any
    val expression: Any
    val arg: String
    val modifiers: JsonOf<Boolean> // { [key: String]: Boolean }
}

external interface VNodeComponentOptions {
    var Ctor: Any // typeof Vue
    var propsData: Json?
    var listeners: Json?
    var children: VNodeChildren?
    var tag: String?
}

/**
 * `String | Number`
 */
external interface Key

inline fun Key(value: String): Key = value.asDynamic()
inline fun Key(value: Int): Key = value.asDynamic()

inline fun Key.toString(): String = this.asDynamic()
inline fun Key.toNumber(): Int = this.asDynamic()

/**
 * `{ [propertyName: String]: ScopedSlot }`
 */
external interface ScopedSlotMap : JsonOf<ScopedSlot?>

/**
 * `(props: Any) -> VNodeChildrenArrayContents | String`
 */
external interface ScopedSlot

inline fun ScopedSlot(value: (props: Any) -> VNodeChildrenArrayContents): ScopedSlot = value.asDynamic()
inline fun ScopedSlot(value: String): ScopedSlot = value.asDynamic()

inline fun ScopedSlot.toFunction(): (props: Any) -> VNodeChildrenArrayContents = this.asDynamic()
inline fun ScopedSlot.toString(): String = this.asDynamic()

/**
 * `VNodeChildrenArrayContents | [ScopedSlot] | String`
 */
external interface VNodeChildren

inline fun VNodeChildren(value: VNodeChildrenArrayContents): VNodeChildren = value.asDynamic()
inline fun VNodeChildren(value: Array<ScopedSlot>): VNodeChildren = value.asDynamic()
inline fun VNodeChildren(value: String): VNodeChildren = value.asDynamic()

inline fun VNodeChildren.toContents(): VNodeChildrenArrayContents = this.asDynamic()
inline fun VNodeChildren.toScopedSlot(): Array<ScopedSlot> = this.asDynamic()
inline fun VNodeChildren.toString(): String = this.asDynamic()

/**
 * `{ [x: Number]: VNode | String | VNodeChildren }`
 */
external interface VNodeChildrenArrayContents

inline operator fun VNodeChildrenArrayContents.get(x: Int): Contents = this.asDynamic()[x]
inline operator fun VNodeChildrenArrayContents.set(x: Int, value: Contents) {
    this.asDynamic()[x] = value
}

/**
 * `VNode | String | VNodeChildren`
 */
external interface Contents

inline fun Contents(value: VNode): Contents = value.asDynamic()
inline fun Contents(value: String): Contents = value.asDynamic()
inline fun Contents(value: VNodeChildren): Contents = value.asDynamic()

inline fun Contents.toVNode(): VNode = this.asDynamic()
inline fun Contents.toString(): String = this.asDynamic()
inline fun Contents.toVNodeChildren(): VNodeChildren = this.asDynamic()
