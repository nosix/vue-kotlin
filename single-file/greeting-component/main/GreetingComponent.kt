import org.musyozoku.vuekt.ComponentOptions
import org.musyozoku.vuekt.Data
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.json

external class GreetingComponent : Vue {
    var greeting: String
}

val options = ComponentOptions<GreetingComponent> {
    data = Data {
        json<GreetingComponent> {
            greeting = "Hello"
        }
    }
}
