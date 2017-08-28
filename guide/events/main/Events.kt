import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.json
import org.musyozoku.vuekt.thisAs
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import kotlin.js.Json

external interface Example1Model : Json {
    var counter: Int
}

val example1 = Vue {
    el = "#example-1"
    data = json<Example1Model> {
        counter = 0
    }
}

external interface Example2Model : Json {
    var name: String
}

external fun alert(message: String)

val EventTarget?.tagName: String
    get() = this.asDynamic().tagName as String

fun EventTarget?.preventDefault(): Unit = this.asDynamic().preventDefault()

val example2 = Vue {
    el = "#example-2"
    data = json<Example2Model> {
        name = "Vue.js"
    }
    methods = json {
        set("greet") { event: Event? ->
            val self = thisAs<Example2Model>()
            alert("Hello ${self.name}!")
            event?.let {
                alert(it.target.tagName)
            }
        }
    }
}

val example3 = Vue {
    el = "#example-3"
    methods = json {
        set("say") { message: String ->
            alert(message)
        }
        set("warn") { message: String, event: EventTarget? ->
            event?.preventDefault()
            alert(message)
        }
    }
}