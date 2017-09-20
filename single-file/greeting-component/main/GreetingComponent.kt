import azadev.kotlin.css.dimens.em
import azadev.kotlin.css.fontSize
import azadev.kotlin.css.textAlign
import org.musyozoku.vuekt.Data
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.js2vue.ComponentOptionsBuilder
import org.musyozoku.vuekt.js2vue.ComponentVue
import org.musyozoku.vuekt.js2vue.StyleBuilder
import org.musyozoku.vuekt.js2vue.translate
import org.musyozoku.vuekt.json

external class GreetingComponent : Vue {
    var greeting: String
}

class GreetingComponentVue : ComponentVue<GreetingComponent> {

    override val template: String = """<p>{{ greeting }} World!</p>"""

    override val style: StyleBuilder = {
        p {
            fontSize = 2.em
            textAlign = center
        }
    }

    override val script: ComponentOptionsBuilder<GreetingComponent> = {
        data = Data {
            json<GreetingComponent> {
                greeting = "Hello"
            }
        }
    }
}

@Suppress("unused")
val options = translate(GreetingComponentVue())