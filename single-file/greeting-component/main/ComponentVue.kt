import azadev.kotlin.css.Stylesheet
import org.musyozoku.vuekt.ComponentOptions
import org.musyozoku.vuekt.Vue

typealias StyleBuilder = Stylesheet.() -> Unit
typealias ComponentOptionsBuilder<V> = ComponentOptions<V>.() -> Unit

interface ComponentVue<V : Vue> {
    val name: String
    val template: String
    val scopedStyle: Boolean get() = false
    val style: StyleBuilder
    val script: ComponentOptionsBuilder<V>
}
