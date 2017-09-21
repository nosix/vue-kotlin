Object.assign(config.module, {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          esModule: false // required for Kotlin/JS
        }
      },
      {
        test: /\.html$/,
        loader: 'html-loader'
      }
    ],
})
