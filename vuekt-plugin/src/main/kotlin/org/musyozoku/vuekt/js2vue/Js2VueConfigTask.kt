package org.musyozoku.vuekt.js2vue

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class Js2VueConfigTask : DefaultTask() {

    companion object {
        val CONFIG_FILE_SUFFIX: String = "js2vue.js"
    }

    private val extension: VuePluginExtension = project.extensions.getByType(VuePluginExtension::class.java)

    @Suppress("MemberVisibilityCanPrivate")
    val webpackConfigDir: File
        get() = project.projectDir.resolve("webpack.config.d")

    @Suppress("MemberVisibilityCanPrivate")
    val configFile: File
        get() = webpackConfigDir.resolve(extension.configFile)

    @Suppress("unused")
    @TaskAction
    fun generate() {
        webpackConfigDir.exists() || webpackConfigDir.mkdir()
        val configFiles = webpackConfigDir.listFiles { _, name ->  name.endsWith(CONFIG_FILE_SUFFIX) }
        if (configFiles.isEmpty()) {
            configFile.writeText("""
                // Disable `fs` module on web target.
                config.node = { fs: 'empty' }
            """.trimIndent())
        }
    }
}