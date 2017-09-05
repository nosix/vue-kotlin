import org.musyozoku.vuekt.*
import kotlin.browser.document

//  var data = { a: 1 }
//  var vm = new Vue({
//      el: '#example',
//      data: data
//  })
//  vm.$data === data // -> true
//  vm.$el === document.getElementById('example') // -> true
//  // $watch はインスタンスメソッドです
//  vm.$watch('a', function (newVal, oldVal) {
//      // このコールバックは `vm.a` の値が変わる時に呼ばれます
//  })

@JsModule("vue")
@JsNonModule
@JsName("Vue")
external class ExampleVue(options: ComponentOptions<ExampleVue>?) : Vue {
    var a: Int
}

fun main(args: Array<String>) {
    val data = json<ExampleVue> {
        a = 1
    }
    val vm = ExampleVue(ComponentOptions {
        el = ElementConfig("#example")
        this.data = ObjectOrFactory(data)
        beforeCreate = {
            val self = thisAs<ExampleVue>()
            println("beforeCreate: ${self.a}")
        }
        created = {
            val self = thisAs<ExampleVue>()
            println("created: ${self.a}")
        }
        beforeMount = {
            val self = thisAs<ExampleVue>()
            println("beforeMount: ${self.a}")
        }
        mounted = {
            val self = thisAs<ExampleVue>()
            println("mounted: ${self.a}")
        }
        beforeUpdate = {
            val self = thisAs<ExampleVue>()
            println("beforeUpdate: ${self.a}")
        }
        updated = {
            val self = thisAs<ExampleVue>()
            println("updated: ${self.a}")
        }
        beforeDestroy = {
            val self = thisAs<ExampleVue>()
            println("beforeDestroy: ${self.a}")
        }
        destroyed = {
            val self = thisAs<ExampleVue>()
            println("destroyed: ${self.a}")
        }
    })
    println("vm.data: ${vm.`$data` === data}")
    println("vm.el: ${vm.`$el` === document.getElementById("example")}")
    vm.`$watch`("a", {
        newVal: Int, oldVal: Int ->
        println("vm.watch: $newVal -> $oldVal")
        thisAs<Vue>().`$destroy`()
    })
    data.a = 2
}