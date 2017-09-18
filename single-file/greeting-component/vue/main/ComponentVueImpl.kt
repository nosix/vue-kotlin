import azadev.kotlin.css.Stylesheet
import azadev.kotlin.css.dimens.em
import azadev.kotlin.css.fontSize
import azadev.kotlin.css.textAlign

interface ComponentVue {
    val name: String
    val template: String
    val scopedStyle: Boolean get() = false
    val style: Stylesheet.() -> Unit
    val script: String get() = "${name}_main.js"
}

class ComponentVueImpl : ComponentVue {

    override val name: String = "greeting-component"

    override val template: String = """<p>{{ greeting }} World!</p>"""

    override val style: Stylesheet.() -> Unit = {
        p {
            fontSize = 2.em
            textAlign = center
        }
    }
}
