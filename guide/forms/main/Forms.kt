import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.json
import kotlin.js.Json

external interface Example1Model : Json {
    var message: String
    var checkedNames: Array<String>
}

val example1 = Vue {
    el = "#example-1"
    data = json<Example1Model> {
        message = ""
        checkedNames = emptyArray()
    }
}

class OptionItem(val text: String, val value: String)

external interface Example2Model : Json {
    var selected: String
    var options: Array<OptionItem>
}

val example2 = Vue {
    el = "#example-2"
    data = json<Example2Model> {
        selected = "A"
        options = arrayOf(
                OptionItem("One", "A"),
                OptionItem("Two", "B"),
                OptionItem("Three", "C")
        )
    }
}