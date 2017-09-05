import org.musyozoku.vuekt.*
import kotlin.js.Json

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var isActive: Boolean
    var error: dynamic // FIXME
    var styleObject: Styles
}

external interface Styles {
    var color: String
    var fontSize: String
}

val vm = ExampleVue(json {
    el = "#example"
    data = ObjectOrFactory(json<ExampleVue> {
        isActive = true
        error = null
        styleObject = json {
            color = "red"
            fontSize = "13px"
        }
    })
    computed = json {
        this["classObject"] = ComputedOptionsOrFactory<Json> {
            val self = thisAs<ExampleVue>()
            json {
                set("active", self.isActive && self.error == null)
                set("text-danger", self.error != null && self.error.type == "fatal")
            }
        }
    }
})
