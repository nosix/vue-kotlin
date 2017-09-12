// https://webpack.js.org/configuration/

var path = require('path')

module.exports = {
  entry: {
    vendor: ['vue', 'vuekt'],
    computed: '../computed/build/computed.js',
    'class-and-style': '../class-and-style/build/class-and-style.js',
    list: '../list/build/list.js',
    events: '../events/build/events.js',
    forms: '../forms/build/forms.js',
    components: '../components/build/components.js'
  },
  output: {
    publicPath: '/build/',
    path: path.resolve('./bundle'),
    filename: '[name].js',
    // library* : For reference from browser console
    library: 'main',
    libraryTarget: 'var'
  },
  resolve: {
    extensions: ['.js', '.css', '.vue'],
    modules: [
      path.resolve('js'),
      path.resolve('..', 'resources'),
      path.resolve('.'),
      path.resolve('node_modules')
    ],
    alias: {
      vue: 'vue/dist/vue.js' // https://vuejs.org/v2/guide/installation.html#Runtime-Compiler-vs-Runtime-only
    }
  },
//  module: {
//    loaders: [
//      { test: /\.vue$/, loader: 'vue-loader' },
//      { test: /\.css$/, loader: 'style-loader!css-loader' }
//    ]
//  },
  devtool: '#source-map'
};

console.log(module.exports.resolve.modules);