@file:Suppress("UnsafeCastFromDynamic")

import org.musyozoku.vuekt.Json
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.thisOf
import kotlin.js.Json

external interface Model : Json {
    var message: String
    var rawId: Int
    var url: String
}

fun main(args: Array<String>) {
    Vue {
        el = "#example"
        data = Json<Model> {
            message = "hello"
            rawId = 1
            url = "https://jp.vuejs.org/"
        }
        filters = Json {
            set("capitalize") {
                it: String ->
                it.capitalize()
            }
            set("formatId") {
                it: Int, digits: Int ->
                it.asDynamic().toFixed(digits) // JavaScript の関数を実行
            }
        }
        methods = Json {
            set("doSomething") {
                val self = thisOf<Model>()
                self.message = "do something"
            }
        }
    }
}