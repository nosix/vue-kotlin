import org.musyozoku.vuekt.*

@JsModule("greeting-component.vue")
external val GreetingComponent: Component = definedExternally

@JsModule("vue")
@JsName("Vue")
external class AppVue(options: ComponentOptions<AppVue>) : Vue {
    var message: String
}

val vm = AppVue(ComponentOptions {
    el = ElementConfig("#app")
    data = Data(json<AppVue> {
        message = "Vue & Kotlin"
    })
    components = json {
        this["greeting"] = ComponentConfig(GreetingComponent)
    }
})