import org.musyozoku.vuekt.*

//  new Vue({
//    // ...
//    filters: {
//      capitalize: function (value) {
//        if (!value) return ''
//        value = value.toString()
//        return value.charAt(0).toUpperCase() + value.slice(1)
//      }
//    }
//  })

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var message: String
    var rawId: Int
    var arg2: String
}

val vm = ExampleVue(ComponentOptions {
    el = ElementConfig("#example")
    data = Data(json = json {
        message = "hello"
        rawId = 1
        arg2 = ".txt"
    })
    filters = json {
        this["capitalize"] = {
            it: String ->
            it.capitalize()
        }
        this["formatId"] = {
            it: Int, digits: Int ->
            it.asDynamic().toFixed(digits) // FIXME: JavaScript の関数を実行
        }
        this["filterA"] = {
            it: String, arg1: String, arg2: String ->
            "$arg1$it$arg2"
        }
    }
})