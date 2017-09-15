Object.assign(config.module, {
    loaders: [
      { test: /\.vue$/, loader: 'vue-loader' },
      { test: /\.css$/, loader: 'style-loader!css-loader' }
    ]
})
