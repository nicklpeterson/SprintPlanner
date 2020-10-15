var webpack = require("webpack");
var path = require('path');
var HtmlWebpackPlugin =  require('html-webpack-plugin');

module.exports = {
    entry: './src/index.js',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'index_bundle.js',
        publicPath: '/'
    },
    module: {
        rules: [
            {test: /\.(js)$/, use: 'babel-loader'},
            {test: /\.css$/, use: ['style-loader', 'css-loader']}
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify('development'),
                'API_URL': JSON.stringify('http://localhost:8080')
            }
        }),
        new HtmlWebpackPlugin({
            template: 'src/index.html'
        })
    ],
    devServer: {
        historyApiFallback: true,
    }
}