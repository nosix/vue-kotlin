@file:Suppress("unused")

package org.musyozoku.vuekt.js2vue

import azadev.kotlin.css.Stylesheet
import org.musyozoku.vuekt.Vue

external fun require(name: String): dynamic

fun <V : Vue> translate(vue: ComponentVue<V>) {
    val fs = require("fs")

    if (fs.writeFileSync == undefined) return

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