@file:Suppress("UnsafeCastFromDynamic")

import org.musyozoku.vuekt.Json
import org.musyozoku.vuekt.Vue
import kotlin.js.Json

external interface Model : Json {
    var message: String
    var rawId: Int
}

fun main(args: Array<String>) {
    val vm = Vue {
        el = "#example"
        data = Json<Model> {
            message = "hello"
            rawId = 1
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
    }
}