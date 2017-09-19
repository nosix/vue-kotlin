package org.musyozoku.vuekt.js2vue

import org.gradle.api.Plugin
import org.gradle.api.Project

class VuePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("vue", VuePluginExtension::class.java)

        project.tasks.create("js2vue", Js2VueTask::class.java) {
            it.dependsOn(project.tasks.getByPath("compileKotlin2Js"))
            project.tasks.getByPath("bundle").dependsOn(it)
            project.tasks.getByPath("run").dependsOn(it)
        }
    }
}