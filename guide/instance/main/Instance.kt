import org.musyozoku.vuekt.JsObject
import org.musyozoku.vuekt.JsThis
import org.musyozoku.vuekt.Vue
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

external interface Model: JsObject {
    var a: Int
}

fun main(args: Array<String>) {
    val model = JsObject<Model> {
        a = 1
    }
    val vm = Vue {
        el = "#example"
        data = model
        beforeCreate = {
            val self = JsThis<Model>()
            println("beforeCreate: ${self.a}")
        }
        created = {
            val self = JsThis<Model>()
            println("created: ${self.a}")
        }
        beforeMount = {
            val self = JsThis<Model>()
            println("beforeMount: ${self.a}")
        }
        mounted = {
            val self = JsThis<Model>()
            println("mounted: ${self.a}")
        }
        beforeUpdate = {
            val self = JsThis<Model>()
            println("beforeUpdate: ${self.a}")
        }
        updated = {
            val self = JsThis<Model>()
            println("updated: ${self.a}")
        }
        beforeDestroy = {
            val self = JsThis<Model>()
            println("beforeDestroy: ${self.a}")
        }
        destroyed = {
            val self = JsThis<Model>()
            println("destroyed: ${self.a}")
        }
    }
    println("vm.data: ${vm.`$data` === model}")
    println("vm.el: ${vm.`$el` === document.getElementById("example")}")
    vm.`$watch`("a", {
        newVal: Int, oldVal: Int ->
        println("vm.watch: $newVal -> $oldVal")
        JsThis<Vue>().`$destroy`()
    })
    model.a = 2
}