import org.musyozoku.vuekt.json
import org.musyozoku.vuekt.Vue
import org.musyozoku.vuekt.thisAs
import kotlin.browser.document
import kotlin.js.Json

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

external interface Model : Json {
    var a: Int
}

fun main(args: Array<String>) {
    val model = json<Model> {
        a = 1
    }
    val vm = Vue {
        el = "#example"
        data = model
        beforeCreate = {
            val self = thisAs<Model>()
            println("beforeCreate: ${self.a}")
        }
        created = {
            val self = thisAs<Model>()
            println("created: ${self.a}")
        }
        beforeMount = {
            val self = thisAs<Model>()
            println("beforeMount: ${self.a}")
        }
        mounted = {
            val self = thisAs<Model>()
            println("mounted: ${self.a}")
        }
        beforeUpdate = {
            val self = thisAs<Model>()
            println("beforeUpdate: ${self.a}")
        }
        updated = {
            val self = thisAs<Model>()
            println("updated: ${self.a}")
        }
        beforeDestroy = {
            val self = thisAs<Model>()
            println("beforeDestroy: ${self.a}")
        }
        destroyed = {
            val self = thisAs<Model>()
            println("destroyed: ${self.a}")
        }
    }
    println("vm.data: ${vm.`$data` === model}")
    println("vm.el: ${vm.`$el` === document.getElementById("example")}")
    vm.`$watch`("a", {
        newVal: Int, oldVal: Int ->
        println("vm.watch: $newVal -> $oldVal")
        thisAs<Vue>().`$destroy`()
    })
    model.a = 2
}