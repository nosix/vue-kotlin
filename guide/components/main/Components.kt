import org.musyozoku.vuekt.*
import org.w3c.dom.events.Event
import kotlin.browser.window

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var counter: Int
    var parentMsg: String
}

val Child = json<ComponentOptions<ExampleVue>> {
    template = "<div>A custom component!</div>"
}

val example = ExampleVue(json {
    el = "#example"
    components = json {
        this["my-component"] = Child
    }
})

val simpleCounter = Vue.component("simple-counter", json<ComponentOptions<ExampleVue>> {
    template = """<button v-on:click="counter += 1">{{ counter }}</button>"""
    data = ObjectOrFactory {
        json<ExampleVue> {
            counter = 0
        }
    }
})

val example2 = ExampleVue(json {
    el = "#example-2"
})

val child = Vue.component("child", json<ComponentOptions<ExampleVue>> {
    // camel-case in JavaScript / JavaScript ではキャメルケース
    props = PropsListOrPropsMap(arrayOf("myMessage"))
    template =  "<span>{{ myMessage }}</span>"
})

val example3 = ExampleVue(json {
    el = "#example-3"
    data = ObjectOrFactory(json<ExampleVue> {
        parentMsg = ""
    })
})

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ButtonCounterVue(options: ComponentOptions<ButtonCounterVue>) : Vue {
    var counter: Int
}

val buttonCounter = Vue.component("button-counter", json<ComponentOptions<ButtonCounterVue>> {
    template = """<button v-on:click="incrementCounter">{{ counter }}</button>"""
    data = ObjectOrFactory {
        json<ButtonCounterVue> {
            counter = 0
        }
    }
    methods = json {
        this["incrementCounter"] = {
            val self = thisAs<ButtonCounterVue>()
            self.counter++
            self.`$emit`("increment")
        }
    }
})

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class CounterEventExampleVue(options: ComponentOptions<CounterEventExampleVue>) : Vue {
    var total: Int
}

val counterEventExample = CounterEventExampleVue(json {
    el = "#counter-event-example"
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

external class CurrencyInputComponent : Vue {
    val value: Int
    val label: String
    fun formatValue(): String
}

val currencyInput = Vue.component("currency-input", json<ComponentOptions<CurrencyInputComponent>> {
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
    props = PropsListOrPropsMap(json<PropMap> {
        this["value"] = PropOptionsOrConstructor(json<PropOptions> {
            type = js("Number") // FIXME
            default = 0
        })
        this["label"] = PropOptionsOrConstructor(json<PropOptions> {
            type = String
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
                val refs: dynamic = self.`$refs`
                refs.input.value = formattedValue
            }
            self.`$emit`("input", formattedValue.toIntOrNull() ?: "")
        }
        this["formatValue"] = {
            val self = thisAs<CurrencyInputComponent>()
            val element = self.`$refs`["input"].toHTMLElement()
            element.nodeValue = self.value.toString()
        }
        set("selectAll") {
            event: Event ->
            window.setTimeout({
                val target: dynamic = event.target
                target.select()
            }, 0)
        }
    }
})

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class AppVue(options: ComponentOptions<AppVue>) : Vue {
    var price: Int
    var shipping: Int
    var handling: Int
    var discount: Int
}

val app = AppVue(json {
    el = "#app"
    data = ObjectOrFactory(json<AppVue> {
        price = 0
        shipping = 0
        handling = 0
        discount = 0
    })
    computed = json {
        this["total"] = ComputedOptionsOrFactory {
            val self = thisAs<AppVue>()
            (self.price * 100 + self.shipping * 100 + self.handling * 100 - self.discount * 100) / 100
        }
    }
})