import org.musyozoku.vuekt.*
import kotlin.js.Json

//  data: {
//    isActive: true,
//    hasError: false
//  }

//  data: {
//    isActive: true,
//    error: null
//  },
//  computed: {
//    classObject: function () {
//      return {
//        active: this.isActive && !this.error,
//        'text-danger': this.error && this.error.type === 'fatal',
//      }
//    }
//  }

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example1Vue(options: ComponentOptions<Example1Vue>) : Vue {
    var isActive: Boolean
    var hasError: Boolean
    var error: dynamic // FIXME
}

val example1 = Example1Vue(ComponentOptions {
    el = ElementConfig("#example1")
    data = ObjectOrFactory(json<Example1Vue> {
        isActive = true
        hasError = false
        error = null
    })
    computed = json {
        this["classObject"] = ComputedConfig<Json> {
            val self = thisAs<Example1Vue>()
            json {
                this["active"] = self.isActive && self.error == null
                this["text-danger"] = self.error != null && self.error.type == "fatal"
            }
        }
    }
})

//  data: {
//    activeClass: 'active',
//    errorClass: 'text-danger'
//  }

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example2Vue(options: ComponentOptions<Example2Vue>) : Vue {
    var isActive: Boolean
    var activeClass: String
    var errorClass: String
}

val example2 = Example2Vue(ComponentOptions {
    el = ElementConfig("#example2")
    data = ObjectOrFactory(json<Example2Vue> {
        isActive = false
        activeClass = "active"
        errorClass = "text-danger"
    })
})

//  Vue.component('my-component', {
//    template: '<p class="foo bar">Hi</p>'
//  })

external class MyComponent : Vue

val myComponent = Vue.component("my-component", Component(ComponentOptions<MyComponent> {
    template = """<p class="foo bar">Hi, my component</p>"""
}))

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example3Vue(options: ComponentOptions<Example3Vue>) : Vue {
    var isActive: Boolean
}

val example3 = Example3Vue(ComponentOptions {
    el = ElementConfig("#example3")
    data = ObjectOrFactory(json<Example3Vue> {
        isActive = true
    })
})

//  data: {
//    activeColor: 'red',
//    fontSize: 30
//  }

//  data: {
//    styleObject: {
//      color: 'red',
//      fontSize: '13px'
//    }
//  }

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example4Vue(options: ComponentOptions<Example4Vue>) : Vue {
    var activeColor: String
    var fontSize: Int
    var styleObject: Styles
    var baseStyles: Styles
    var overridingStyles: Styles
}

external interface Styles {
    var color: String
    var fontSize: String
}

val example4 = Example4Vue(ComponentOptions {
    el = ElementConfig("#example4")
    data = ObjectOrFactory(json<Example4Vue> {
        activeColor = "red"
        fontSize = 30
        styleObject = json {
            color = "red"
            fontSize = "13px"
        }
        baseStyles = json {
            color = "#777777"
        }
        overridingStyles = json {
            fontSize = "25px"
        }
    })
})