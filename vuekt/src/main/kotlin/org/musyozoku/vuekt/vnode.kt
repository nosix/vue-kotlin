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
    var key: Any? // String | Number
    var componentOptions: VNodeComponentOptions?
    var componentInstance: Vue?
    var parent: VNode?
    var raw: Boolean?
    var isStatic: Boolean?
    var isRootInsert: Boolean
    var isComment: Boolean
}

external interface VNodeData {
    var key: Any? // String | Number
    var slot: String?
    var scopedSlots: Any? // { [key: String]: ScopedSlot }
    var ref: String?
    var tag: String?
    var staticClass: String?
    var `class`: Any?
    var staticStyle: Any? // { [key: String]: Any }
    var style: Any? // Array<Object> | Object
    var props: Any? // { [key: String]: Any }
    var attrs: Any? // { [key: String]: Any }
    var domProps: Any? // { [key: String]: Any }
    var hook: Any? // { [key: String]: Function }
    var on: Any? // { [key: String]: Function | Array<Function> }
    var nativeOn: Any? // { [key: String]: Function | Array<Function> }
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
    val modifiers: Json // { [key: String]: Boolean }
}

external interface VNodeComponentOptions {
    var Ctor: Any // typeof Vue
    var propsData: Json?
    var listeners: Json?
    var children: VNodeChildren?
    var tag: String?
}

typealias VNodeChildren = Any // VNodeChildrenArrayContents | [ScopedSlot] | String

typealias VNodeChildrenArrayContents = Any // { [x: Number]: VNode | String | VNodeChildren }

typealias ScopedSlot = Any // (props: Any) -> VNodeChildrenArrayContents | String
