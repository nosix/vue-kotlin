package org.musyozoku.vuekt.js2vue

open class VuePluginExtension {

    /**
     * Files to match this pattern translate to vue file.
     */
    var targetPattern = """.*-component\.js"""

    /**
     * Name of the file generated as `webpack.config.d/$configFile`
     */
    var configFile = "01_${Js2VueConfigTask.CONFIG_FILE_SUFFIX}"
}