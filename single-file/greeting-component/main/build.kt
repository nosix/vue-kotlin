import org.musyozoku.vuekt.ComponentOptions
import org.musyozoku.vuekt.js2vue.translate

val vue = GreetingComponentVue()

val options = ComponentOptions(vue.script)

fun main(args: Array<String>) {
    translate(vue)
}