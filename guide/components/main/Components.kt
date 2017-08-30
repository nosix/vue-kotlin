import org.musyozoku.vuekt.*
import org.w3c.dom.events.Event
import kotlin.browser.window
import kotlin.js.Json


val Child = json<ComponentDefinition> {
    template = "<div>A custom component!</div>"
}

val example = Vue {
    el = "#example"
    components = json {
        set("my-component", Child)
    }
}

val simpleCounter = Vue.component("simple-counter", json<ComponentDefinition> {
    template = """<button v-on:click="counter += 1">{{ counter }}</button>"""
    data = {
        json {
            set("counter", 0)
        }
    }
})

val example2 = Vue {
    el = "#example-2"
}

val child = Vue.component("child", json<ComponentDefinition> {
    // JavaScript ではキャメルケース
    props =  arrayOf("myMessage")
    template =  "<span>{{ myMessage }}</span>"
})

val example3 = Vue {
    el = "#example-3"
    data = json {
        set("parentMsg", "")
    }
}

external interface ButtonCounterModel : Json {
    var counter: Int
}

val buttonCounter = Vue.component("button-counter", json<ComponentDefinition> {
    template = """<button v-on:click="incrementCounter">{{ counter }}</button>"""
    data = {
        json<ButtonCounterModel> {
            counter = 0
        }
    }
    methods = json {
        set("incrementCounter") {
            val self = thisAs<Vue>()
            self.proxyOf<ButtonCounterModel>().counter++
            self.`$emit`("increment")
        }
    }
})

external interface CounterEventExampleModel : Json {
    var total: Int
}

val counterEventExample = Vue {
    el = "#counter-event-example"
    data = json<CounterEventExampleModel> {
        total = 0
    }
    methods = json {
        set("incrementTotal") {
            val self = thisAs<CounterEventExampleModel>()
            self.total++
        }
    }
}

val currencyInput = Vue.component("currency-input", json<ComponentDefinition> {
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
    props = json {
        set("value", json {
            set("type", js("Number"))
            set("default", 0)
        })
        set("label", json {
            set("type", String)
            set("default", "")
        })
    }
    mounted = {
        val self = thisAs<dynamic>()
        self.formatValue()
    }
    methods = json {
        set("updateValue") {
            value: String ->
            val self = thisAs<Vue>()
            val trimmedValue = value.trim()
            val formattedValue = trimmedValue.substring(0,
                    if (value.indexOf('.') == -1) value.length else value.indexOf('.') + 3 )
            if (formattedValue != value) {
                val refs: dynamic = self.`$refs`
                refs.input.value = formattedValue
            }
            self.`$emit`("input", formattedValue.toIntOrNull() ?: "")
        }
        set("formatValue") {
            val self = thisAs<Vue>()
            val refs: dynamic = self.`$refs`
            refs.input.value = self.proxyOf<dynamic>().value
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

external interface AppModel : Json {
    var price: Int
    var shipping: Int
    var handling: Int
    var discount: Int
}

val app = Vue {
    el = "#app"
    data = json<AppModel> {
        price = 0
        shipping = 0
        handling = 0
        discount = 0
    }
    computed = json {
        set("total") {
            val self = thisAs<AppModel>()
            (self.price * 100 + self.shipping * 100 + self.handling * 100 - self.discount * 100) / 100
        }
    }
}