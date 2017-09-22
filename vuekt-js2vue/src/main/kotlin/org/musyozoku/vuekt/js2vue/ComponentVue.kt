@file:Suppress("unused")

package org.musyozoku.vuekt.js2vue

import azadev.kotlin.css.Stylesheet
import org.musyozoku.vuekt.ComponentOptions
import org.musyozoku.vuekt.Vue

private external fun require(moduleName: String): dynamic

typealias StyleBuilder = Stylesheet.() -> Unit

typealias ComponentOptionsBuilder<V> = ComponentOptions<V>.() -> Unit

interface ComponentVue<V : Vue> {

    /**
     * Base name of files.
     *
     * Default: if class name is `FooBarComponentVue`, then base name is `foo-bar-component`.
     */
    val name: String get() = this::class.js.name
            .replace("^Vue|Vue$".toRegex(), "").replace("([A-Z])".toRegex(), "-$1").toLowerCase().trim('-')

    /**
     * Output vue file name.
     *
     * Default: `${name}.vue`
     */
    val outputFile: String get() = "$name.vue"

    /**
     * Input script name
     *
     * Default: `${name}_main.js`
     */
    val inputFile: String get() = "${name}_main.js"

    /**
     * Vue template.
     */
    val template: String

    /**
     * If style is scoped, then this value is true.
     *
     * Default: false
     */
    val scopedStyle: Boolean get() = false

    /**
     * Vue style.
     */
    val style: StyleBuilder

    /**
     * Vue script.
     */
    val script: ComponentOptionsBuilder<V>
}

fun <V : Vue> translate(vue: ComponentVue<V>): ComponentOptions<V> {
    val fs = require("fs")

    if (fs.writeFileSync != undefined) {

        fs.writeFileSync(vue.outputFile, """
            |<template>
            |${vue.template}
            |</template>
            |
            |<style${if (vue.scopedStyle) " scoped" else ""}>
            |${Stylesheet(vue.style).render()}
            |</style>
            |
            |<script>
            |module.exports = require('${vue.inputFile}').options
            |</script>
        """.trimMargin())
    }

    return ComponentOptions(vue.script)
}
