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
    data = Data {
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
    data = Data(json = json {
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
        this["propB"] = PropConfig(constructors = arrayOf(js.String, js.Number))
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
    data = Data {
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
    data = Data(json = json {
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
    data = Data(json = json {
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
    methods = json {
        this["update"] = {
            event: Event ->
            val self = thisAs<MyCheckboxComponent>()
            val target = event.target as? HTMLInputElement
            target?.let {
                println(it.checked)
                self.`$emit`("change", it.checked)
            }
        }
    }
    template = """
        <div>
          <input type="checkbox" v-bind:checked="checked" v-on:change="update">
          <span>{{ value }}</span>
        </div>
    """.trimIndent()
}))

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example4Vue(options: ComponentOptions<Example4Vue>) : Vue {
    var foo: Boolean
}

val example4 = Example4Vue(ComponentOptions {
    el = ElementConfig("#example-4")
    data = Data(json = json {
        foo = true
    })
})

// https://vuejs.org/v2/guide/components.html#Single-Slot
// https://jp.vuejs.org/v2/guide/components.html#単一スロット

val example5 = Vue(ComponentOptions {
    el = ElementConfig("#example-5")
    components = json {
        this["my-component"] = ComponentOrAsyncComponent(Component(ComponentOptions<MyComponent> {
            template = """
              <div>
                <h2>I'm the child title</h2>
                <slot>
                  This will only be displayed if there is no content
                  to be distributed.
                </slot>
              </div>
              """
        }))
    }
})

// https://vuejs.org/v2/guide/components.html#Named-Slots
// https://jp.vuejs.org/v2/guide/components.html#名前付きスロット

external class AppLayoutComponent : Vue

val example6 = Vue(ComponentOptions {
    el = ElementConfig("#example-6")
    components = json {
        this["app-layout"] = ComponentOrAsyncComponent(Component(ComponentOptions<AppLayoutComponent> {
            template = """
              <div class="container">
                <header>
                  <slot name="header"></slot>
                </header>
                <main>
                  <slot></slot>
                </main>
                <footer>
                  <slot name="footer"></slot>
                </footer>
              </div>
              """
        }))
    }
})

// https://vuejs.org/v2/guide/components.html#Scoped-Slots
// https://jp.vuejs.org/v2/guide/components.html#スコープ付きスロット

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example7Vue(options: ComponentOptions<Example7Vue>) : Vue {
    var items: Array<AwesomeItem>
}

class AwesomeItem(val text: String)

external class MyAwesomeListComponent : Vue

val example7 = Example7Vue(ComponentOptions {
    el = ElementConfig("#example-7")
    data = Data(json = json {
        items = arrayOf(AwesomeItem("foo"), AwesomeItem("bar"))
    })
    components = json {
        this["my-awesome-list"] = ComponentOrAsyncComponent(Component(ComponentOptions<MyAwesomeListComponent> {
            template = """
                <ul>
                  <slot name="item"
                    v-for="item in items"
                    :text="item.text">
                    <!-- fallback content here -->
                  </slot>
                </ul>
                """
            props = Props(arrayOf("items"))
        }))
    }
})

// https://vuejs.org/v2/guide/components.html#Dynamic-Components
// https://jp.vuejs.org/v2/guide/components.html#動的コンポーネント

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example8Vue(options: ComponentOptions<Example8Vue>) : Vue {
    var currentView: String
}

external class ViewComponent : Vue

val example8 = Example8Vue(ComponentOptions {
    el = ElementConfig("#example-8")
    data = Data(json = json {
        currentView = "home"
    })
    components = json {
        this["home"] = ComponentOrAsyncComponent(Component(ComponentOptions<ViewComponent> {
            template = "<div>Home View</div>"
        }))
        this["posts"] = ComponentOrAsyncComponent(Component(ComponentOptions<ViewComponent> {
            template = "<div>Posts View</div>"
        }))
        this["archive"] = ComponentOrAsyncComponent(Component(ComponentOptions<ViewComponent> {
            template = "<div>Archive View</div>"
        }))
    }
})

// https://vuejs.org/v2/guide/components.html#Child-Component-Refs
// https://jp.vuejs.org/v2/guide/components.html#子コンポーネントの参照

val example9Parent = Vue(ComponentOptions {
    el = ElementConfig("#example-9")
    components = json {
        this["user-profile"] = ComponentOrAsyncComponent(Component(ComponentOptions<MyComponent> {
            template = "<div>User Profile</div>"
        }))
    }
})

val example9Child = example9Parent.`$refs`["profile"]

// Try following code on browser console:
// require('components').example9Child

//  Vue.component('async-example', function (resolve, reject) {
//    setTimeout(function () {
//      // Pass the component definition to the resolve callback
//      resolve({
//        template: '<div>I am async!</div>'
//      })
//    }, 1000)
//  })

val asyncExample = Vue.component("async-example") {
    resolve, _ ->
    window.setTimeout({
        resolve(Component(ComponentOptions<MyComponent> {
            template = "<div>I am async!</div>"
        }))
    }, 1000)
    AsyncComponentResult(void)
}

val example10 = Vue(ComponentOptions {
    el = ElementConfig("#example-10")
})