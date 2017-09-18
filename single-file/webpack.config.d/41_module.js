Object.assign(config.module, {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          esModule: false
        }
      },
      {
        test: /\.html$/,
        loader: 'html-loader'
      }
    ],
})
