@file:Suppress("UnsafeCastFromDynamic")

import org.musyozoku.vuekt.JsObject
import org.musyozoku.vuekt.Vue

external interface Model: JsObject {
    var message: String
    var rawId: Int
    var capitalize: (String) -> String
    var formatId: (Int, Int) -> String
}

fun main(args: Array<String>) {
    val vm = Vue {
        el = "#example"
        data = JsObject<Model> {
            message = "hello"
            rawId = 1
        }
        filters = JsObject<Model> {
            capitalize = {
                it.capitalize()
            }
            formatId = { it: Int, digits: Int ->
                it.asDynamic().toFixed(digits) // JavaScript の関数を実行
            }
        }
    }
}