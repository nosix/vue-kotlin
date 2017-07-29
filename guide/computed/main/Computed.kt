import org.musyozoku.vuekt.*
import kotlin.js.Json

external interface Model : Json {
    var message: String
    val reversedMessage: String

    var firstName: String
    var lastName: String
    var fullName: String
}

fun main(args: Array<String>) {
    val vm = Vue {
        el = "#example"
        data = Json<Model> {
            message = "Hello"
            firstName = "Foo"
            lastName = "Bar"
        }
        computed = Json {
            set("reversedMessage") {
                val self = thisAs<Model>()
                self.message.split("").reversed().joinToString("")
            }
            set("fullName", Json<Accessor<String>> {
                get = {
                    val self = thisAs<Model>()
                    "${self.firstName} ${self.lastName}"
                }
                set = {
                    newValue ->
                    val (firstName, lastName) = newValue.split(" ")
                    val self = thisAs<Model>()
                    self.firstName = firstName
                    self.lastName = lastName
                }
            })
        }
    }
    val model = vm.proxyOf<Model>()
    println(model.reversedMessage)
    model.message = "Goodbye"
    println(model.reversedMessage)
    println(model.fullName)
    model.fullName = "John Doe"
    println(model.fullName)
    println(model.firstName)
    println(model.lastName)
}