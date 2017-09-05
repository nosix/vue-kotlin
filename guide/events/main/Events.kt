import org.musyozoku.vuekt.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class Example1Vue(options: ComponentOptions<Example1Vue>) : Vue {
    var counter: Int
}

val example1 = Example1Vue(json {
    el = "#example-1"
    data = ObjectOrFactory(json<Example1Vue> {
        counter = 0
    })
})

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class Example2Vue(options: ComponentOptions<Example2Vue>) : Vue {
    var name: String
}

external fun alert(message: String)

val EventTarget?.tagName: String
    get() = this.asDynamic().tagName as String

fun EventTarget?.preventDefault(): Unit = this.asDynamic().preventDefault()

val example2 = Example2Vue(json {
    el = "#example-2"
    data = ObjectOrFactory(json<Example2Vue> {
        name = "Vue.js"
    })
    methods = json {
        this["greet"] = { event: Event? ->
            val self = thisAs<Example2Vue>()
            alert("Hello ${self.name}!")
            event?.let {
                alert(it.target.tagName)
            }
        }
    }
})

val example3 = Example2Vue(json {
    el = "#example-3"
    methods = json {
        this["say"] = { message: String ->
            alert(message)
        }
        this["warn"] = { message: String, event: EventTarget? ->
            event?.preventDefault()
            alert(message)
        }
    }
})