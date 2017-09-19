package org.musyozoku.vuekt.js2vue

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.frontend.util.kotlinOutput
import org.jetbrains.kotlin.gradle.frontend.util.nodePath
import org.jetbrains.kotlin.gradle.frontend.util.startWithRedirectOnFail
import java.io.File

open class Js2VueTask : DefaultTask() {

    private val extension: VuePluginExtension = project.extensions.getByType(VuePluginExtension::class.java)

    @Suppress("MemberVisibilityCanPrivate")
    val nodeCommand: File
            get() = nodePath(project, "node").first()

    @Suppress("MemberVisibilityCanPrivate")
    val nodeModulesDir: File
            get() = project.buildDir.resolve("node_modules")

    @Suppress("MemberVisibilityCanPrivate")
    val workingDirs: List<File>
            get() = project.subprojects.map { kotlinOutput(it).parentFile!! }

    @Suppress("unused")
    @TaskAction
    fun translate() {
        val regExp = extension.targetPattern.toRegex()

        workingDirs.forEach { workingDir ->
            project.logger.debug(workingDir.absolutePath)

            workingDir.listFiles { _, name ->
                name.matches(regExp)
            }.forEach { script ->
                project.logger.debug(script.absolutePath)

                ProcessBuilder(nodeCommand.absolutePath, script.absolutePath)
                        .directory(workingDir)
                        .apply { environment().put("NODE_PATH", nodeModulesDir.absolutePath) }
                        .startWithRedirectOnFail(project, "node <js2vue translator>.js")
            }
        }
    }
}