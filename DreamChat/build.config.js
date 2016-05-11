var path = require('path');
var webpack = require('webpack');

module.exports = {
  context: __dirname, //current folder as the reference to the other paths
  entry: {
        register_form_generator: './src/register_form_generator.js' //entry point for building scripts
  },
  output: {
    path: path.resolve('./views'), //save result in 'dist' folder
    filename: 'register_form.js'
  },
  module: {
    loaders: [
      { //transpile ES2015 with JSX into ES5
        test: /\.js?$/,
        exclude: /node_modules/,
        loader: 'babel',
        query: {
          presets: ['react', 'es2015']
        }
      }
    ]
  }
};
