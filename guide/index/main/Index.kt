import org.musyozoku.vuekt.*
import kotlin.js.Date

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class AppVue(options: ComponentOptions<AppVue>) : Vue {
    var message: String
}

//  https://vuejs.org/v2/guide/#Declarative-Rendering
//
//  var app = new Vue({
//      el: '#app',
//      data: {
//          message: 'Hello Vue!'
//      }
//  })

val app = AppVue(ComponentOptions {
    el = ElementConfig("#app")
    data = ObjectOrFactory(json<AppVue> {
        message = "Hello Vue!"
    })
})

// Try: input `index.app.message = 'Hi'` on browser console.

//  var app2 = new Vue({
//      el: '#app-2',
//      data: {
//          message: 'You loaded this page on ' + new Date()
//      }
//  })

val app2 = AppVue(ComponentOptions {
    el = ElementConfig("#app-2")
    data = ObjectOrFactory(json<AppVue> {
        message = "You loaded this page on ${Date()}"
    })
})

// Try: input `index.app2.message = 'some new message'` on browser console.

//  var app3 = new Vue({
//      el: '#app-3',
//      data: {
//          seen: true
//      }
//  })

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class App3Vue(options: ComponentOptions<App3Vue>) : Vue {
    var seen: Boolean
}

val app3 = App3Vue(ComponentOptions {
    el = ElementConfig("#app-3")
    data = ObjectOrFactory(json<App3Vue> {
        seen = true
    })
})

// Try: input `index.app3.seen = false` on browser console.

//  var app4 = new Vue({
//      el: '#app-4',
//      data: {
//          todos: [
//              { text: 'Learn JavaScript' },
//              { text: 'Learn Vue' },
//              { text: 'Build something awesome' }
//          ]
//      }
//  })

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class App4Vue(options: ComponentOptions<App4Vue>) : Vue {
    var todos: Array<Text>
}

class Text(var text: String)

val app4 = App4Vue(ComponentOptions {
    el = ElementConfig("#app-4")
    data = ObjectOrFactory(json<App4Vue> {
        todos = arrayOf(
                Text("Learn JavaScript"),
                Text("Learn Vue"),
                Text("Build something awesome")
        )
    })
})

// Try: input `index.app4.todos.push({ text: 'New item' })` on browser console.

//  var app5 = new Vue({
//      el: '#app-5',
//      data: {
//          message: 'Hello Vue.js!'
//      },
//      methods: {
//          reverseMessage: function () {
//              this.message = this.message.split('').reverse().join('')
//          }
//      }
//  })

val app5 = AppVue(ComponentOptions {
    el = ElementConfig("#app-5")
    data = ObjectOrFactory(json<AppVue> {
        message = "Hello Vue.js!"
    })
    methods = json {
        this["reverseMessage"] = {
            val self = thisAs<AppVue>()
            self.message = self.message.split("").reversed().joinToString("")
        }
    }
})

//  var app6 = new Vue({
//      el: '#app-6',
//      data: {
//          message: 'Hello Vue!'
//      }
//  })

val app6 = AppVue(ComponentOptions {
    el = ElementConfig("#app-6")
    data = ObjectOrFactory(json<AppVue> {
        message = "Hello Vue!"
    })
})

//  Vue.component('todo-item', {
//      props: ['todo'],
//      template: '<li>{{ todo.text }}</li>'
//  })
//
//  var app7 = new Vue({
//      el: '#app-7',
//      data: {
//          groceryList: [
//              { id: 0, text: 'Vegetables' },
//              { id: 1, text: 'Cheese' },
//              { id: 2, text: 'Whatever else humans are supposed to eat' }
//          ]
//      }
//  })

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class App7Vue(options: ComponentOptions<App7Vue>) : Vue {
    var groceryList: Array<Item>
}

class Item(var id: Int, var text: String)

val TodoItem = Vue.component("todo-item", Component(ComponentOptions<App7Vue> {
    props = Props(arrayOf("todo"))
    template = "<li>{{ todo.text }}</li>"
}))

val app7 = App7Vue(ComponentOptions {
    el = ElementConfig("#app-7")
    data = ObjectOrFactory(json<App7Vue> {
        groceryList = arrayOf(
                Item(0, "Vegetables"),
                Item(1, "Cheese"),
                Item(2, "Whatever else humans are supposed to eat")
        )
    })
})