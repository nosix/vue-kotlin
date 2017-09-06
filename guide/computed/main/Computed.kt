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
}

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class DemoVue(options: ComponentOptions<DemoVue>) : Vue {
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

//  var vm = new Vue({
//    el: '#example',
//    data: {
//      message: 'Hello'
//    },
//    computed: {
//      // a computed getter
//      // 算出 getter 関数
//      reversedMessage: function () {
//        // `this` points to the vm instance
//        // `this` は vm インスタンスを指します
//        return this.message.split('').reverse().join('')
//      }
//    }
//  })

    val example = ExampleVue(ComponentOptions {
        el = ElementConfig("#example")
        data = ObjectOrFactory(json<ExampleVue> {
            message = "Hello"
        })
        computed = json {
            this["reversedMessage"] = ComputedConfig {
                val self = thisAs<ExampleVue>()
                self.message.split("").reversed().joinToString("")
            }
        }
    })

//  console.log(vm.reversedMessage) // => 'olleH'
//  vm.message = 'Goodbye'
//  console.log(vm.reversedMessage) // => 'eybdooG'

    println(example.reversedMessage) // => 'olleH'
    example.message = "Goodbye"
    println(example.reversedMessage) // => 'eybdooG'

//  var vm = new Vue({
//    el: '#demo',
//    data: {
//      firstName: 'Foo',
//      lastName: 'Bar'
//    },
//    computed: {
//      fullName: {
//        // getter
//        get: function () {
//          return this.firstName + ' ' + this.lastName
//        },
//        // setter
//        set: function (newValue) {
//          var names = newValue.split(' ')
//          this.firstName = names[0]
//          this.lastName = names[names.length - 1]
//        }
//      }
//    }
//  })

    val demo = DemoVue(ComponentOptions {
        el = ElementConfig("#demo")
        data = ObjectOrFactory(json<DemoVue> {
            firstName = "Foo"
            lastName = "Bar"
        })
        computed = json {
            this["fullName"] = ComputedConfig(ComputedOptions<String> {
                get = {
                    val self = thisAs<DemoVue>()
                    "${self.firstName} ${self.lastName}"
                }
                set = { newValue ->
                    val (firstName, lastName) = newValue.split(" ")
                    val self = thisAs<DemoVue>()
                    self.firstName = firstName
                    self.lastName = lastName
                }
            })
        }
    })

    println(demo.fullName) // => 'Foo Bar'
    demo.fullName = "John Doe"
    println(demo.fullName) // => 'John Doe'
    println(demo.firstName) // => 'John'
    println(demo.lastName) // => 'Doe'

//  var watchExampleVM = new Vue({
//    el: '#watch-example',
//    data: {
//      question: '',
//      answer: 'I cannot give you an answer until you ask a question!'
//    },
//    watch: {
//      // whenever question changes, this function will run
//      question: function (newQuestion) {
//        this.answer = 'Waiting for you to stop typing...'
//        this.getAnswer()
//      }
//    },
//    methods: {
//      // _.debounce is a function provided by lodash to limit how
//      // often a particularly expensive operation can be run.
//      // In this case, we want to limit how often we access
//      // yesno.wtf/api, waiting until the user has completely
//      // finished typing before making the ajax request. To learn
//      // more about the _.debounce function (and its cousin
//      // _.throttle), visit: https://lodash.com/docs#debounce
//      getAnswer: _.debounce(
//        function () {
//          if (this.question.indexOf('?') === -1) {
//            this.answer = 'Questions usually contain a question mark. ;-)'
//            return
//          }
//          this.answer = 'Thinking...'
//          var vm = this
//          axios.get('https://yesno.wtf/api')
//            .then(function (response) {
//              vm.answer = _.capitalize(response.data.answer)
//            })
//            .catch(function (error) {
//              vm.answer = 'Error! Could not reach the API. ' + error
//            })
//        },
//        // This is the number of milliseconds we wait for the
//        // user to stop typing.
//        500
//      )
//    }
//  })

    WatchExampleVue(ComponentOptions {
        el = ElementConfig("#watch-example")
        data = ObjectOrFactory(json<WatchExampleVue> {
            question = ""
            answer = "I cannot give you an answer until you ask a question!"
        })
        watch = json {
            this["question"] = Watcher<String> { _, _ ->
                val self = thisAs<WatchExampleVue>()
                self.answer = "Waiting for you to stop typing..."
                self.getAnswer()
            }
        }
        methods = json {
            this["getAnswer"] = lodash.debounce(
                    fun() {
                        val self = thisAs<WatchExampleVue>()
                        if (self.question.indexOf("?") == -1) {
                            self.answer = "Questions usually contain a question mark. ;-)"
                        } else {
                            self.answer = "Thinking..."
                            axios.get("https://yesno.wtf/api")
                                    .then(fun(response: dynamic) {
                                        self.answer = lodash.capitalize(response.data.answer as String)
                                    })
                                    .catch(fun(error: String) {
                                        self.answer = "Error! Could not reach the API. " + error
                                    })
                        }
                    },
                    500
            )
        }
    })
}