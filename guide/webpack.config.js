// https://webpack.js.org/configuration/

var path = require('path')

module.exports = {
  entry: {
    computed: 'computed',
    'class-and-style': 'class-and-style',
    list: 'list',
    events: 'events',
    forms: 'forms',
    components: 'components',
    vendor: ['vue', 'vuekt']
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