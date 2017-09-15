// https://vuejs.org/v2/guide/installation.html#Runtime-Compiler-vs-Runtime-only
Object.assign(config.resolve, {
    extensions: ['.js', '.css', '.vue'],
    alias: {
        'vue': 'vue/dist/vue.js'
    }
})
