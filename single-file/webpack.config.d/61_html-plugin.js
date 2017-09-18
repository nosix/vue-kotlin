var HtmlWebpackPlugin = require('html-webpack-plugin')
var fs = require('fs')

for (var entry in config.entry) {
    var file = entry + '.html'
    var path = '../webContent/' + file
    if (fs.existsSync(path)) {
        config.plugins.push(new HtmlWebpackPlugin({
            inject: false,
            filename: file,
            template: '!!html-webpack-plugin/lib/loader.js!' + path,
        }))
    }
}
