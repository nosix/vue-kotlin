// https://vuejs.org/v2/guide/installation.html#Runtime-Compiler-vs-Runtime-only
Object.assign(config.resolve, {
    extensions: ['.js'],
    alias: {
        'vue$': 'vue/dist/vue.common.js'
    }
})

config.resolveLoader = {
    // required because `module not found` occurs on some loaders
    modules: [require('path').join(__dirname, 'node_modules')]
}