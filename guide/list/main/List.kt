import org.musyozoku.vuekt.*

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var parentMessage: String
    var items: Array<Item>
    var `object`: Person
}

class Item(val message: String)

//  var example1 = new Vue({
//    el: '#example-1',
//    data: {
//      items: [
//        { message: 'Foo' },
//        { message: 'Bar' }
//      ]
//    }
//  })

val example1 = ExampleVue(ComponentOptions {
    el = ElementConfig("#example-1")
    data = Data(json = json {
        items = arrayOf(Item("Foo"), Item("Bar"))
    })
})

//  var example2 = new Vue({
//    el: '#example-2',
//    data: {
//      parentMessage: 'Parent',
//      items: [
//        { message: 'Foo' },
//        { message: 'Bar' }
//      ]
//    }
//  })

val example2 = ExampleVue(ComponentOptions {
    el = ElementConfig("#example-2")
    data = Data(json = json {
        parentMessage = "Parent"
        items = arrayOf(Item("Foo"), Item("Bar"))
    })
})

//  new Vue({
//    el: '#v-for-object',
//    data: {
//      object: {
//        firstName: 'John',
//        lastName: 'Doe',
//        age: 30
//      }
//    }
//  })

class Person(
        val firstName: String,
        val lastName: String,
        val age: Int)

val repeatObjectVue = ExampleVue(ComponentOptions {
    el = ElementConfig("#repeat-object")
    data = Data(json = json {
        `object` = Person("John", "Doe", 30)
    })
})

// https://vuejs.org/v2/guide/list.html#Mutation-Methods
// https://jp.vuejs.org/v2/guide/list.html#変更メソッド

fun push() {
    println(example1.items.push(Item("Baz"), Item("Boo")))
}

fun pop() {
    println(example1.items.pop()?.message)
}

fun shift() {
    println(example1.items.shift()?.message)
}

fun unshift() {
    println(example1.items.unshift(Item("FooBar"), Item("FooBoo")))
}

fun splice1() {
    println(example1.items.splice(1))
}

fun splice2() {
    println(example1.items.splice(1, 1))
}

fun splice3() {
    println(example1.items.splice(1, 3, Item("BarBaz"), Item("BarBoo")))
}

fun sort() {
    println(arrayOf("Foo", "Bar", "Boo").sort())
}

fun sortWithFunction() {
    println(example1.items.sort { a, b -> if (a.message == b.message) 0 else if (a.message > b.message) 1 else -1 })
}

fun reverse() {
    println(example1.items.reverse())
}

fun replace() {
    example1.items = example1.items.filter { it.message.contains("Foo") }.toTypedArray()
}

// Try following code on browser console:
// m = require('list')
// m.push()
// m.pop()
// m.shift()
// m.unshift()
// m.sort()
// m.sortWithFunction()
// m.reverse()
// m.splice3()
// m.splice2()
// m.splice1()
// m.replace()

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example3Vue(options: ComponentOptions<Example3Vue>) : Vue {
    var userProfile: UserProfile
}

class UserProfile(var name: String)

//  var vm = new Vue({
//    data: {
//      userProfile: {
//        name: 'Anika'
//      }
//    }
//  })

val example3 = Example3Vue(ComponentOptions {
    el = ElementConfig("#example3")
    data = Data(json = json {
        userProfile = UserProfile("Anika")
    })
})

fun resetUserProfile() {
    example3.userProfile = UserProfile("Anika")
}

fun setAge1() {
    Vue.set(example3.userProfile, "age", 27)
}

fun setAge2() {
    example3.`$set`(example3.userProfile, "age", 27)
}

// Try following code on browser console:
// m = require('list')
// m.setAge1()
// m.resetUserProfile()
// m.setAge2()
// m.resetUserProfile()

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example4Vue(options: ComponentOptions<Example4Vue>) : Vue {
    var numbers: Array<Int>
}

//  data: {
//    numbers: [ 1, 2, 3, 4, 5 ]
//  },
//  computed: {
//    evenNumbers: function () {
//      return this.numbers.filter(function (number) {
//        return number % 2 === 0
//      })
//    }
//  }
//  methods: {
//    even: function (numbers) {
//      return numbers.filter(function (number) {
//        return number % 2 === 0
//      })
//    }
//  }

val example4 = Example4Vue(ComponentOptions {
    el = ElementConfig("#example4")
    data = Data(json = json {
        numbers = arrayOf(1, 2, 3, 4, 5)
    })
    computed = json {
        this["evenNumbers"] = ComputedConfig {
            val self = thisAs<Example4Vue>()
            self.numbers.filter { it % 2 == 0 }.toTypedArray()
        }
    }
    methods = json {
        this["even"] = { numbers: Array<Int> ->
            numbers.filter { it % 2 == 0 }.toTypedArray()
        }
    }
})

//  Vue.component('todo-item', {
//    template: '\
//      <li>\
//        {{ title }}\
//        <button v-on:click="$emit(\'remove\')">X</button>\
//      </li>\
//    ',
//    props: ['title']
//  })
//  new Vue({
//    el: '#todo-list-example',
//    data: {
//      newTodoText: '',
//      todos: [
//        {
//          id: 1,
//          title: 'Do the dishes',
//        },
//        {
//          id: 2,
//          title: 'Take out the trash',
//        },
//        {
//          id: 3,
//          title: 'Mow the lawn'
//        }
//      ],
//      nextTodoId: 4
//    },
//    methods: {
//      addNewTodo: function () {
//        this.todos.push({
//            id: this.nextTodoId++,
//            title: this.newTodoText
//        })
//        this.newTodoText = ''
//      }
//    }
//  })

external class TodoItemComponent : Vue

val todoItem = Vue.component("todo-item", Component(ComponentOptions<TodoItemComponent> {
    template = """
        <li>
          {{ title }}
          <button v-on:click="${'$'}emit('remove')">X</button>
        </li>
        """
    props = Props(arrayOf("title"))
}))

class TodoItem(val id: Int, val title: String)

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class TodoListVue(options: ComponentOptions<TodoListVue>) : Vue {
    var newTodoText: String
    var todos: Array<TodoItem>
    var nextTodoId: Int
}

val todoListExample = TodoListVue(ComponentOptions {
    el = ElementConfig("#todo-list-example")
    data = Data(json = json {
        newTodoText = ""
        todos = arrayOf(
                TodoItem(1, "Do the dishes"),
                TodoItem(2, "Take out the trash"),
                TodoItem(3, "Mow the lawn")
        )
        nextTodoId = 4
    })
    methods = json {
        this["addNewTodo"] = {
            val self = thisAs<TodoListVue>()
            self.todos.push(TodoItem(self.nextTodoId++, self.newTodoText))
            self.newTodoText = ""
        }
    }
})
