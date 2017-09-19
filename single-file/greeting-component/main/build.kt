import azadev.kotlin.css.Stylesheet
import org.musyozoku.vuekt.ComponentOptions

external fun require(name: String): dynamic

private val vue = GreetingComponentVue()

var options = ComponentOptions(vue.script)

fun main(args: Array<String>) {
    val fs = require("fs")

    if (fs.writeFileSync == undefined) return

    fs.writeFileSync("${vue.name}.vue", """
            |<template>
            |${vue.template}
            |</template>
            |
            |<style${if (vue.scopedStyle) " scoped" else ""}>
            |${Stylesheet(vue.style).render()}
            |</style>
            |
            |<script>
            |module.exports = require('${vue.name}_main.js').options
            |</script>
        """.trimMargin())
}