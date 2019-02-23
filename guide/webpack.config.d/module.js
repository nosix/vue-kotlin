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
            test: /\.css$/,
            use: [
                'vue-style-loader',
                'css-loader'
            ]
        }
    ],
})
