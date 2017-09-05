@file:Suppress("UnsafeCastFromDynamic")

import org.musyozoku.vuekt.*

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var message: String
    var rawId: Int
    var url: String
}

fun main(args: Array<String>) {
    ExampleVue(json {
        el = "#example"
        data = ObjectOrFactory(json<ExampleVue> {
            message = "hello"
            rawId = 1
            url = "https://jp.vuejs.org/"
        })
        filters = json {
            set("capitalize") {
                it: String ->
                it.capitalize()
            }
            set("formatId") {
                it: Int, digits: Int ->
                it.asDynamic().toFixed(digits) // FIXME: JavaScript の関数を実行
            }
        }
        methods = json {
            set("doSomething") {
                val self = thisAs<ExampleVue>()
                self.message = "do something"
            }
        }
    })
}