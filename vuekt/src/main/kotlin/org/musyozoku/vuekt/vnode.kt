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

inline fun Key(name: String): Key = name.asDynamic()
inline fun Key(index: Int): Key = index.asDynamic()

inline fun Key.toName(): String = this.asDynamic()
inline fun Key.toIndex(): Int = this.asDynamic()

/**
 * `{ [propertyName: String]: ScopedSlot }`
 */
external interface ScopedSlotMap : JsonOf<ScopedSlot?>

/**
 * `(props: Any) -> VNodeChildrenArrayContents | String`
 */
external interface ScopedSlot

inline fun ScopedSlot(func: (props: Any) -> VNodeChildrenArrayContents): ScopedSlot = func.asDynamic()
inline fun ScopedSlot(value: String): ScopedSlot = value.asDynamic() // TODO change parameter name

inline fun ScopedSlot.toFunction(): (props: Any) -> VNodeChildrenArrayContents = this.asDynamic()
inline fun ScopedSlot.toString(): String = this.asDynamic() // TODO change method name

/**
 * `VNodeChildrenArrayContents | [ScopedSlot] | String`
 */
external interface VNodeChildren

inline fun VNodeChildren(contents: VNodeChildrenArrayContents): VNodeChildren = contents.asDynamic()
inline fun VNodeChildren(scopedSlot: Array<ScopedSlot>): VNodeChildren = scopedSlot.asDynamic()
inline fun VNodeChildren(value: String): VNodeChildren = value.asDynamic() // TODO change parameter name

inline fun VNodeChildren.toContents(): VNodeChildrenArrayContents = this.asDynamic()
inline fun VNodeChildren.toScopedSlot(): Array<ScopedSlot> = this.asDynamic()
inline fun VNodeChildren.toString(): String = this.asDynamic() // TODO change method name

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

inline fun Contents(node: VNode): Contents = node.asDynamic()
inline fun Contents(value: String): Contents = value.asDynamic() // TODO change parameter name
inline fun Contents(children: VNodeChildren): Contents = children.asDynamic()

inline fun Contents.toVNode(): VNode = this.asDynamic()
inline fun Contents.toString(): String = this.asDynamic() // TODO change method name
inline fun Contents.toVNodeChildren(): VNodeChildren = this.asDynamic()
