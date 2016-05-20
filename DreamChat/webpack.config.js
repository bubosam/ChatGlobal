var webpack = require('webpack');

module.exports = {
  entry: [
    'script!jquery/dist/jquery.min.js',
    'script!foundation-sites/dist/foundation.min.js',
    './views/app.jsx'
  ],
  externals: {
    jquery: 'jQuery'
  },
  plugins: [
    new webpack.ProvidePlugin({
      '$': 'jquery',
      'jQuery': 'jquery'
    })
  ],
  output:{
    path: __dirname,
    filename: './views/bundle.js'
  },
  resolve: {
    root: __dirname,
    alias: {
      Main: 'views/components/Main.jsx',
      AppBar: 'views/components/AppBar.jsx',
      Login: 'views/components/Login.jsx',
      LoginForm: 'views/components/LoginForm.jsx',
      Register: 'views/components/Register.jsx',
      RegisterForm: 'views/components/RegisterForm.jsx',
      DashBoardComp: 'views/components/DashBoardComp.jsx',
      DashBoard: 'views/components/DashBoard.jsx',
      ContactListComp: 'views/components/ContactListComp.jsx',
      ContactList: 'views/components/ContactList.jsx'
    },
    extensions: ['', '.js', '.jsx' ]
  },
  module: {
    loaders: [
      {
      loader: 'babel-loader',
      query: {
        presets: ['react', 'es2015', 'stage-0']
      },
      test: /\.jsx?$/,
      exclude: /(node_module|bower_components)/
      }
    ]
  }
};
