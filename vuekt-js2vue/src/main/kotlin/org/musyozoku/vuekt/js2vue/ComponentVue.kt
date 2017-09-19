@file:Suppress("unused")

package org.musyozoku.vuekt.js2vue

import azadev.kotlin.css.Stylesheet
import org.musyozoku.vuekt.ComponentOptions
import org.musyozoku.vuekt.Vue

typealias StyleBuilder = Stylesheet.() -> Unit

typealias ComponentOptionsBuilder<V> = ComponentOptions<V>.() -> Unit

interface ComponentVue<V : Vue> {

    /**
     * Base name of files.
     */
    val name: String

    /**
     * Vue template.
     */
    val template: String

    /**
     * If style is scoped, then this value is true.
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

    /**
     * Output vue file name.
     */
    val outputFile: String get() = "$name.vue"

    /**
     * Input script name
     */
    val inputFile: String get() = "${name}_main.js"
}