import org.musyozoku.vuekt.*
import kotlin.browser.document

//  var data = { a: 1 }
//  var vm = new Vue({
//      el: '#example',
//      data: data
//  })
//
//  // These reference the same object!
//  // これらは同じオブジェクトを参照します!
//  vm.$data === data // -> true
//
//  // Setting the property on the instance also affects the original data
//  // プロパティへの代入は、元のデータにも反映されます
//  vm.a = 2
//  data.a // => 2
//
//  // ... and vice-versa
//  // ... そして、その逆もまたしかりです
//  data.a = 3
//  vm.a // => 3
//
//  vm.$el === document.getElementById('example') // -> true
//
//  // $watch is an instance method
//  // $watch はインスタンスメソッドです
//  vm.$watch('a', function (newVal, oldVal) {
//      // This callback will be called when `vm.a` changes
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

        // https://vuejs.org/v2/guide/instance.html#Instance-Lifecycle-Hooks
        // https://jp.vuejs.org/v2/guide/instance.html#インスタンスライフサイクルフック

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

    println("vm.data: ${vm.`$data` === data}") // => true

    vm.a = 2
    println("data.a: ${data.a}") // => 2

    data.a = 3
    println("vm.a: ${vm.a}") // => 3

    println("vm.el: ${vm.`$el` === document.getElementById("example")}") // => true

    vm.`$watch`("a", {
        newVal: Int, oldVal: Int ->
        println("vm.watch: $oldVal -> $newVal")
        thisAs<ExampleVue>().`$destroy`()
    })

    vm.a = 4 // 'watch' function is called.
}