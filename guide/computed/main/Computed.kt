import org.musyozoku.vuekt.*

@JsNonModule
@JsModule("axios")
external val axios: dynamic

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ExampleVue(options: ComponentOptions<ExampleVue>) : Vue {
    var message: String
    val reversedMessage: String

    var firstName: String
    var lastName: String
    var fullName: String
}

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class WatchExampleVue(options: ComponentOptions<WatchExampleVue>) : Vue {
    var question: String
    var answer: String
    fun getAnswer()
}

fun main(args: Array<String>) {
    val vm = ExampleVue(ComponentOptions {
        el = ElementConfig("#example")
        data = ObjectOrFactory(json<ExampleVue> {
            message = "Hello"
            firstName = "Foo"
            lastName = "Bar"
        })
        computed = ComputedMap {
            this["reversedMessage"] = ComputedConfig {
                val self = thisAs<ExampleVue>()
                self.message.split("").reversed().joinToString("")
            }
            this["fullName"] = ComputedConfig(ComputedOptions<String> {
                get = {
                    val self = thisAs<ExampleVue>()
                    "${self.firstName} ${self.lastName}"
                }
                set = {
                    newValue ->
                    val (firstName, lastName) = newValue.split(" ")
                    val self = thisAs<ExampleVue>()
                    self.firstName = firstName
                    self.lastName = lastName
                }
            })
        }
    })
    println(vm.reversedMessage)
    vm.message = "Goodbye"
    println(vm.reversedMessage)
    println(vm.fullName)
    vm.fullName = "John Doe"
    println(vm.fullName)
    println(vm.firstName)
    println(vm.lastName)

    WatchExampleVue(ComponentOptions {
        el = ElementConfig("#watch-example")
        data = ObjectOrFactory(json<WatchExampleVue> {
            question = ""
            answer = "I cannot give you an answer until you ask a question!"
        })
        watch = WatchMap {
            this["question"] = Watcher<String> { _, _ ->
                val self = thisAs<WatchExampleVue>()
                self.answer = "Waiting for you to stop typing..."
                self.getAnswer()
            }
        }
        methods = FunctionMap {
            this["getAnswer"] = lodash.debounce(
                    {
                        val self = thisAs<WatchExampleVue>()
                        if (self.question.indexOf("?") == -1) {
                            self.answer = "Questions usually contain a question mark. ;-)"
                        } else {
                            self.answer = "Thinking..."
                            axios.get("https://yesno.wtf/api")
                                    .then({
                                        response ->
                                        self.answer = lodash.capitalize(response.data.answer as String)
                                        Unit // return value of dynamic type
                                    })
                                    .catch({
                                        error: String ->
                                        self.answer = "Error! Could not reach the API. " + error
                                        Unit // return value of dynamic type
                                    })
                            Unit // suppress UnsafeCaseFromDynamic
                        }
                    },
                    500
            )
        }
    })
}