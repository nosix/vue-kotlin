Object.assign(config.module, {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          esModule: false
        }
      }
    ],
})
