import org.musyozoku.vuekt.*

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example1Vue(options: ComponentOptions<Example1Vue>) : Vue {
    var message: String
    var checked: Boolean
    var checkedNames: Array<String>
    var picked: String
    var selected: String
    var multiSelected: Array<String>
    var options: Array<OptionItem>
}

class OptionItem(val text: String, val value: String)

//  new Vue({
//    el: '...',
//    data: {
//      ...
//      checkedNames: []
//      ...
//      selected: 'A',
//      options: [
//        { text: 'One', value: 'A' },
//        { text: 'Two', value: 'B' },
//        { text: 'Three', value: 'C' }
//      ]
//    }
//  })

val example1 = Example1Vue(ComponentOptions {
    el = ElementConfig("#example-1")
    data = Data(json = json {
        message = ""
        checked = false
        checkedNames = emptyArray()
        picked = ""
        selected = "A"
        multiSelected = emptyArray()
        options = arrayOf(
                OptionItem("One", "A"),
                OptionItem("Two", "B"),
                OptionItem("Three", "C")
        )
    })
})

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example2Vue(options: ComponentOptions<Example2Vue>) : Vue {
    var toggle: String
    var a: String
    var b: String
    var pick: String
    var selected: String
}

val example2 = Example2Vue(ComponentOptions {
    el = ElementConfig("#example-2")
    data = Data(json = json {
        toggle = ""
        a = "a is selected"
        b = "b is selected"
        pick = ""
        selected = ""
    })
})

@JsModule(vue.MODULE)
@JsNonModule
@JsName(vue.CLASS)
external class Example3Vue(options: ComponentOptions<Example3Vue>) : Vue {
    var msg: String
    var age: Int
}

val example3 = Example3Vue(ComponentOptions {
    el = ElementConfig("#example-3")
    data = Data(json = json {
        msg = ""
        age = 0
    })
})