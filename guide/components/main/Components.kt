import org.musyozoku.vuekt.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import kotlin.browser.window

//  var Child = {
//    template: '<div>A custom component!</div>'
//  }

external class MyComponent : Vue

val Child = ComponentOptions<MyComponent> {
    template = "<div>A custom component!</div>"
}

//  new Vue({
//    // ...
//    components: {
//      // <my-component> will only be available in parent's template
//      // <my-component> は親テンプレートでのみ有効になります
//      'my-component': Child
//    }
//  })

val example = Vue(ComponentOptions {
    el = ElementConfig("#example")
    components = json {
        this["my-component"] = ComponentOrAsyncComponent(Component(Child))
    }
})

//  Vue.component('simple-counter', {
//    template: '<button v-on:click="counter += 1">{{ counter }}</button>',
//    // data は技術的には関数なので、Vue は警告を出しません。
//    // しかし、各コンポーネントのインスタンスは
//    // 同じオブジェクトの参照を返します。
//    data: function () {
//        return {
//          counter: 0
//        }
//    }
//  })
//
//  new Vue({
//    el: '#example-2'
//  })

external class SimpleCounterComponent : Vue {
    var counter: Int
}

val simpleCounter = Vue.component("simple-counter", Component(ComponentOptions<SimpleCounterComponent> {
    template = """<button v-on:click="counter += 1">{{ counter }}</button>"""
    data = ObjectOrFactory {
        json<SimpleCounterComponent> {
            counter = 0
        }
    }
}))

val example2 = Vue(ComponentOptions {
    el = ElementConfig("#example-2")
})

//  Vue.component('child', {
//    props: ['myMessage'],
//    template: '<span>{{ myMessage }}</span>'
//  })

external class ChildComponent : Vue

val child = Vue.component("child", Component(ComponentOptions<ChildComponent> {
    props = Props(arrayOf("myMessage"))
    template =  "<span>{{ myMessage }}</span>"
}))

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example1Vue(options: ComponentOptions<Example1Vue>) : Vue {
    var parentMsg: String
}

val example3 = Example1Vue(ComponentOptions {
    el = ElementConfig("#example-3")
    data = ObjectOrFactory(json<Example1Vue> {
        parentMsg = ""
    })
})

//  Vue.component('example', {
//    props: {
//      // 基本な型チェック (`null` はどんな型でも受け付ける)
//      propA: Number,
//      // 複数の受け入れ可能な型
//      propB: [String, Number],
//      // 必須な文字列
//      propC: {
//        type: String,
//        required: true
//      },
//      // デフォルト値
//      propD: {
//        type: Number,
//        default: 100
//      },
//      // オブジェクトと配列のデフォルトはファクトリ関数から返すようにしています
//      propE: {
//        type: Object,
//        default: function () {
//          return { message: 'hello' }
//        }
//      },
//      // カスタムバリデータ関数
//      propF: {
//        validator: function (value) {
//          return value > 10
//        }
//      }
//    }
//  })

external class ExampleComponent : Vue

val exampleComponent = Vue.component("example", Component(ComponentOptions<ExampleComponent> {
    props = Props(propConfig = json {
        this["propA"] = PropConfig(js.Number)
        this["propB"] = PropConfig(arrayOf(js.String, js.Number))
        this["propC"] = PropConfig(PropOptions<String> {
            type = TypeConfig(js.String)
            required = true
        })
        this["propD"] = PropConfig(PropOptions<Int> {
            type = TypeConfig(js.Number)
            default = 100
        })
        this["propE"] = PropConfig(PropOptions<Any> {
            type = TypeConfig(js.Object)
            default = {
                json<JsonOf<String>> {
                    this["message"] = "hello"
                }
            }
        })
        this["propF"] = PropConfig(PropOptions<Int> {
            validator = { value: Int ->
                value > 10
            }
        })
    })
}))

//  Vue.component('button-counter', {
//    template: '<button v-on:click="incrementCounter">{{ counter }}</button>',
//    data: function () {
//      return {
//        counter: 0
//      }
//    },
//    methods: {
//      incrementCounter: function () {
//        this.counter += 1
//        this.$emit('increment')
//      }
//    },
//  })

external class ButtonCounterComponent : Vue {
    var counter: Int
}

val buttonCounter = Vue.component("button-counter", Component(ComponentOptions<ButtonCounterComponent> {
    template = """<button v-on:click="incrementCounter">{{ counter }}</button>"""
    data = ObjectOrFactory {
        json<ButtonCounterComponent> {
            counter = 0
        }
    }
    methods = json {
        this["incrementCounter"] = {
            val self = thisAs<ButtonCounterComponent>()
            self.counter++
            self.`$emit`("increment")
        }
    }
}))

//  new Vue({
//    el: '#counter-event-example',
//    data: {
//      total: 0
//    },
//    methods: {
//      incrementTotal: function () {
//        this.total += 1
//      }
//    }
//  })

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class CounterEventExampleVue(options: ComponentOptions<CounterEventExampleVue>) : Vue {
    var total: Int
}

val counterEventExample = CounterEventExampleVue(ComponentOptions {
    el = ElementConfig("#counter-event-example")
    data = ObjectOrFactory(json<CounterEventExampleVue> {
        total = 0
    })
    methods = json {
        this["incrementTotal"] = {
            val self = thisAs<CounterEventExampleVue>()
            self.total++
        }
    }
})

// https://vuejs.org/v2/guide/components.html#Form-Input-Components-using-Custom-Events
// https://jp.vuejs.org/v2/guide/components.html#カスタムイベントを使用したフォーム入力コンポーネント

external class CurrencyInputComponent : Vue {
    val value: Int
    val label: String
    fun formatValue(): String
}

val currencyInput = Vue.component("currency-input", Component(ComponentOptions<CurrencyInputComponent> {
    template = """
      <div>
        <label v-if="label">{{ label }}</label>
        ${'$'}
        <input
          ref="input"
          v-bind:value="value"
          v-on:input="updateValue(${'$'}event.target.value)"
          v-on:focus="selectAll"
          v-on:blur="formatValue">
      </div>
    """.trimIndent()
    props = Props(propConfig = json {
        this["value"] = PropConfig(PropOptions<Int> {
            type = TypeConfig(js.Number)
            default = 0
        })
        this["label"] = PropConfig(PropOptions<String> {
            type = TypeConfig(js.String)
            default = ""
        })
    })
    mounted = {
        val self = thisAs<CurrencyInputComponent>()
        self.formatValue()
    }
    methods = json {
        this["updateValue"] = {
            value: String ->
            val self = thisAs<CurrencyInputComponent>()
            val trimmedValue = value.trim()
            val formattedValue = trimmedValue.substring(0,
                    if (value.indexOf('.') == -1) value.length else value.indexOf('.') + 3 )
            if (formattedValue != value) {
                self.`$refs`["input"]?.let {
                    val element = it.toHTMLElement()
                    element.nodeValue = formattedValue
                }
            }
            self.`$emit`("input", formattedValue.toIntOrNull() ?: "")
        }
        this["formatValue"] = {
            val self = thisAs<CurrencyInputComponent>()
            self.`$refs`["input"]?.let {
                val element = it.toHTMLElement()
                element.nodeValue = self.value.toString()
            }
        }
        set("selectAll") {
            event: Event ->
            window.setTimeout({
                val target = event.target as? HTMLInputElement
                target?.select()
            }, 0)
        }
    }
}))

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class AppVue(options: ComponentOptions<AppVue>) : Vue {
    var price: Int
    var shipping: Int
    var handling: Int
    var discount: Int
}

val app = AppVue(ComponentOptions {
    el = ElementConfig("#app")
    data = ObjectOrFactory(json<AppVue> {
        price = 0
        shipping = 0
        handling = 0
        discount = 0
    })
    computed = json {
        this["total"] = ComputedConfig {
            val self = thisAs<AppVue>()
            (self.price * 100 + self.shipping * 100 + self.handling * 100 - self.discount * 100) / 100
        }
    }
})

//  Vue.component('my-checkbox', {
//    model: {
//      prop: 'checked',
//      event: 'change'
//    },
//    props: {
//      checked: Boolean,
//      // これによって、 `value` プロパティを別の目的で利用することを許可します。
//      value: String
//    },
//    // ...
//  })

external class MyCheckboxComponent : Vue

val MyCheckbox = Vue.component("my-checkbox", Component(ComponentOptions<MyCheckboxComponent> {
    model = ModelOptions {
        prop = "checked"
        event = "change"
    }
    props = Props(propConfig = json {
        this["checked"] = PropConfig(js.Boolean)
        this["value"] = PropConfig(js.String)
    })
    template = """
        <ul>
          <li>checked: {{ checked }}</li>
          <li>value: {{ value }}</li>
        </ul>
    """.trimIndent()
}))

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example4Vue(options: ComponentOptions<Example4Vue>) : Vue {
    var foo: String
}

val example4 = Example4Vue(ComponentOptions {
    el = ElementConfig("#example-4")
    data = ObjectOrFactory(json<Example4Vue> {
        foo = ""
    })
})