import org.musyozoku.vuekt.ComponentDefinition
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.json
import org.musyozoku.vuekt.thisAs
import kotlin.js.Json

class Item(val message: String)

external interface Model : Json {
    var parentMessage: String
    var items: Array<Item>
}

val example1 = Vue {
    el = "#example-1"
    data = json<Model> {
        items = arrayOf(Item("Foo"), Item("Bar"))
    }
}

val example2 = Vue {
    el = "#example-2"
    data = json<Model> {
        parentMessage = "Parent"
        items = arrayOf(Item("Foo"), Item("Bar"))
    }
}

class Person(
        val firstName: String,
        val lastName: String,
        val age: Int)

val example3 = Vue {
    el = "#repeat-object"
    data = json {
        set("object", Person("John", "Doe", 30))
    }
}

val todoItem = Vue.component("todo-item", json<ComponentDefinition> {
    template = """
        <li>
          {{ title }}
          <button v-on:click="${'$'}emit('remove')">X</button>
        </li>
        """
    props = arrayOf("title")
})

class TodoItem(val id: Int, val title: String)

external interface TodoModel : Json {
    var newTodoText: String
    var todos: MutableList<TodoItem>
    var nextTodoId: Int
}

val todoListExample = Vue {
    el = "#todo-list-example"
    data = json<TodoModel> {
        newTodoText = ""
        todos = mutableListOf(
                TodoItem(1, "Do the dishes"),
                TodoItem(2, "Take out the trash"),
                TodoItem(3, "Mow the lawn")
        )
        nextTodoId = 4
    }
    methods = json {
        set("addNewTodo") {
            val self = thisAs<TodoModel>()
            self.todos.add(TodoItem(self.nextTodoId++, self.newTodoText))
            self.newTodoText = ""
        }
        set("removeTodo") {
            index: Int ->
            val self = thisAs<TodoModel>()
            self.todos.removeAt(index)
        }
    }
    computed = json {
        set("todo_array") {
            val self = thisAs<TodoModel>()
            self.todos.toTypedArray()
        }
    }
}