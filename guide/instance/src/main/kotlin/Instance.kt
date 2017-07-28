import org.musyozoku.vuekt.JsObject
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

external interface Model {
    var a: Int
}

fun main(args: Array<String>) {
    val data = JsObject<Model> {
        a = 1
    }
    val vm = Vue {
        el = "#example"
        this.data = data
    }
    println("vm.data: ${vm.`$data` === data}")
    println("vm.el: ${vm.`$el` === document.getElementById("example")}")
    vm.`$watch`("a", {
        newVal: Int, oldVal: Int ->
        println("vm.watch: $newVal -> $oldVal")
    })
    data.a = 2
}