@file:Suppress("UnsafeCastFromDynamic")

import org.musyozoku.vuekt.json
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.thisAs
import kotlin.js.Json

external interface Model : Json {
    var message: String
    var rawId: Int
    var url: String
}

fun main(args: Array<String>) {
    Vue {
        el = "#example"
        data = json<Model> {
            message = "hello"
            rawId = 1
            url = "https://jp.vuejs.org/"
        }
        filters = json {
            set("capitalize") {
                it: String ->
                it.capitalize()
            }
            set("formatId") {
                it: Int, digits: Int ->
                it.asDynamic().toFixed(digits) // JavaScript の関数を実行
            }
        }
        methods = json {
            set("doSomething") {
                val self = thisAs<Model>()
                self.message = "do something"
            }
        }
    }
}