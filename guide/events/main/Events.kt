import org.musyozoku.vuekt.*
import org.w3c.dom.Element
import org.w3c.dom.events.Event

//  var example1 = new Vue({
//    el: '#example-1',
//    data: {
//      counter: 0
//    }
//  })

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example1Vue(options: ComponentOptions<Example1Vue>) : Vue {
    var counter: Int
}

val example1 = Example1Vue(ComponentOptions {
    el = ElementConfig("#example-1")
    data = ObjectOrFactory(json<Example1Vue> {
        counter = 0
    })
})

//  var example2 = new Vue({
//    el: '#example-2',
//    data: {
//      name: 'Vue.js'
//    },
//    // define methods under the `methods` object
//    // `methods` オブジェクトの下にメソッドを定義する
//    methods: {
//      greet: function (event) {
//        // `this` inside methods points to the Vue instance
//        // メソッド内の `this` は、 Vue インスタンスを参照します
//        alert('Hello ' + this.name + '!')
//        // `event` is the native DOM event
//        // `event` は、ネイティブ DOM イベントです
//        if (event) {
//          alert(event.target.tagName)
//        }
//      }
//    }
//  })
//// JavaScript からメソッドを呼び出すこともできます
//example2.greet() // => 'Hello Vue.js!'

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example2Vue(options: ComponentOptions<Example2Vue>) : Vue {
    var name: String
}

external fun alert(message: String)

val example2 = Example2Vue(ComponentOptions {
    el = ElementConfig("#example-2")
    data = ObjectOrFactory(json<Example2Vue> {
        name = "Vue.js"
    })
    methods = json {
        this["greet"] = { event: Event? ->
            val self = thisAs<Example2Vue>()
            alert("Hello ${self.name}!")
            event?.let {
                alert((it.target as Element).tagName)
            }
        }
    }
})

//  new Vue({
//    el: '#example-3',
//    methods: {
//      say: function (message) {
//        alert(message)
//      }
//    }
//    warn: function (message, event) {
//      // now we have access to the native event
//      // ネイティブイベントを参照しています
//      if (event) event.preventDefault()
//      alert(message)
//    }
//  })

val example3 = Vue(ComponentOptions {
    el = ElementConfig("#example-3")
    methods = json {
        this["say"] = { message: String ->
            alert(message)
        }
        this["warn"] = { message: String, event: Event? ->
            event?.preventDefault()
            alert(message)
        }
    }
})

//  // enable v-on:keyup.f1
//  Vue.config.keyCodes.f1 = 112

val example4 = Vue(ComponentOptions {
    el = ElementConfig("#example-4")
    methods = json {
        this["submit"] = {
            alert("submit")
        }
    }
})

fun main(args: Array<String>) {
    Vue.config.keyCodes["f1"] = 112
}