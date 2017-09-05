import org.musyozoku.vuekt.*

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var parentMessage: String
    var items: Array<Item>
    var `object`: Person
}

class Item(val message: String)

val example1 = ExampleVue(ComponentOptions {
    el = "#example-1"
    data = ObjectOrFactory(json<ExampleVue> {
        items = arrayOf(Item("Foo"), Item("Bar"))
    })
})

val example2 = ExampleVue(ComponentOptions {
    el = "#example-2"
    data = ObjectOrFactory(json<ExampleVue> {
        parentMessage = "Parent"
        items = arrayOf(Item("Foo"), Item("Bar"))
    })
})

class Person(
        val firstName: String,
        val lastName: String,
        val age: Int)

val example3 = ExampleVue(ComponentOptions {
    el = "#repeat-object"
    data = ObjectOrFactory(json<ExampleVue> {
        `object` = Person("John", "Doe", 30)
    })
})

val todoItem = Vue.component("todo-item", Component(ComponentOptions<ExampleVue> {
    template = """
        <li>
          {{ title }}
          <button v-on:click="${'$'}emit('remove')">X</button>
        </li>
        """
    props = Props(arrayOf("title"))
}))

class TodoItem(val id: Int, val title: String)

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class TodoListVue(options: ComponentOptions<TodoListVue>) : Vue {
    var newTodoText: String
    var todos: MutableList<TodoItem>
    var nextTodoId: Int
}

val todoListExample = TodoListVue(ComponentOptions {
    el = "#todo-list-example"
    data = ObjectOrFactory(json<TodoListVue> {
        newTodoText = ""
        todos = mutableListOf(
                TodoItem(1, "Do the dishes"),
                TodoItem(2, "Take out the trash"),
                TodoItem(3, "Mow the lawn")
        )
        nextTodoId = 4
    })
    methods = FunctionMap {
        this["addNewTodo"] = {
            val self = thisAs<TodoListVue>()
            self.todos.add(TodoItem(self.nextTodoId++, self.newTodoText))
            self.newTodoText = ""
        }
        this["removeTodo"] = {
            index: Int ->
            val self = thisAs<TodoListVue>()
            self.todos.removeAt(index)
        }
    }
    computed = ComputedMap {
        this["todo_array"] = ComputedConfig {
            val self = thisAs<TodoListVue>()
            self.todos.toTypedArray()
        }
    }
})