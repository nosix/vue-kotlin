import azadev.kotlin.css.Stylesheet
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

object VueFileBuilder {

    @JvmStatic
    fun main(args: Array<String>) {
        val vue: ComponentVue = ComponentVueImpl()

        val outputDir = "../build/classes/main"
        Files.createDirectories(Paths.get(outputDir))
        Files.newBufferedWriter(Paths.get(outputDir, "${vue.name}.vue"), Charset.forName("UTF-8")).use { out ->
            out.appendln("""
            |<template>
            |${vue.template}
            |</template>
            |
            |<style${if (vue.scopedStyle) " scoped" else ""}>
            |${Stylesheet(vue.style).render()}
            |</style>
            |
            |<script>
            |module.exports = require('${vue.script}').options
            |</script>
        """.trimMargin())
        }
    }
}
