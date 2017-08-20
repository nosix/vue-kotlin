import org.musyozoku.vuekt.json
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.thisAs
import kotlin.js.Json


external interface Model : Json {
    var isActive: Boolean
    var error: dynamic
    var styleObject: Styles
}

external interface Styles : Json {
    var color: String
    var fontSize: String
}

val vm = Vue {
    el = "#example"
    data = json<Model> {
        isActive = true
        error = null
        styleObject = json<Styles> {
            color = "red"
            fontSize = "13px"
        }
    }
    computed = json {
        set("classObject") {
            val self = thisAs<Model>()
            json {
                set("active", self.isActive && self.error == null)
                set("text-danger", self.error != null && self.error.type == "fatal")
            }
        }
    }
}
