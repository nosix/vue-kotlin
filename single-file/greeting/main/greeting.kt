import org.musyozoku.vuekt.*

@JsModule("greeting-component.vue")
external val GreetingComponent: Component

val vm = Vue(ComponentOptions {
    el = ElementConfig("#app")
    components = json {
        this["greeting"] = ComponentConfig(GreetingComponent)
    }
})