// https://vuejs.org/v2/guide/installation.html#Runtime-Compiler-vs-Runtime-only
Object.assign(config.resolve, {
    extensions: ['.js'],
    alias: {
        'vue$': 'vue/dist/vue.common.js'
    }
})

config.resolveLoader = {
      modules: [require('path').join(__dirname, 'node_modules')],
//      extensions: ['.js', '.json'],
//      mainFields: ['loader', 'main']
}