import org.musyozoku.vuekt.*
import kotlin.js.Date

external interface AppModel {
    var message: String
    var reverseMessage: () -> Unit
}

//  var app = new Vue({
//      el: '#app',
//      data: {
//          message: 'Hello Vue!'
//      }
//  })

val app = Vue {
    el = "#app"
    data = JsObject<AppModel> {
        message = "Hello Vue!"
    }
}

//  var app2 = new Vue({
//      el: '#app-2',
//      data: {
//          message: 'You loaded this page on ' + new Date()
//      }
//  })

val app2 = Vue {
    el = "#app-2"
    data = JsObject<AppModel> {
        message = "You loaded this page on ${Date()}"
    }
}

//  var app3 = new Vue({
//      el: '#app-3',
//      data: {
//          seen: true
//      }
//  })

external interface App3Model {
    var seen: Boolean
}

val app3 = Vue {
    el = "#app-3"
    data = JsObject<App3Model> {
        seen = true
    }
}

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

external interface App4Model {
    var todos: Array<Text>
}

class Text(var text: String)

val app4 = Vue {
    el = "#app-4"
    data = JsObject<App4Model> {
        todos = arrayOf(
                Text("Learn JavaScript"),
                Text("Learn Vue"),
                Text("Build something awesome")
        )
    }
}

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

val app5 = Vue {
    el = "#app-5"
    data = JsObject<AppModel> {
        message = "Hello Vue.js!"
    }
    methods = JsObject<AppModel> {
        reverseMessage = {
            val self = JsThis<AppModel>()
            self.message = self.message.split("").reversed().joinToString("")
        }
    }
}

//  var app6 = new Vue({
//      el: '#app-6',
//      data: {
//          message: 'Hello Vue!'
//      }
//  })

val app6 = Vue {
    el = "#app-6"
    data = JsObject<AppModel> {
        message = "Hello Vue!"
    }
}

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

class Item(var id: Int, var text: String)

external interface App7Model {
    var groceryList: Array<Item>
}

val TodoItem = Vue.component("todo-item", JsObject<ComponentDefinition> {
    props = arrayOf("todo")
    template = "<li>{{ todo.text }}</li>"
})

val app7 = Vue {
    el = "#app-7"
    data = JsObject<App7Model> {
        groceryList = arrayOf(
                Item(0, "Vegetables"),
                Item(1, "Cheese"),
                Item(2, "Whatever else humans are supposed to eat")
        )
    }
}