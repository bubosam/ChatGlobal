var express = require('express');
var path = require('path');
global.appRoot = path.resolve(__dirname);
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var consolidate = require('consolidate');
var config = require('../../config');
var compress = require('compression');

var index = require('./routes/index');
var routes = require('./routes.js');
var login = require('./API/login');

// Webpack import
var webpack = require('webpack');
var wpconfig = require('../../webpack.config');
var webpackDevMiddleware = require('webpack-dev-middleware');
var webpackHotMiddleware = require('webpack-hot-middleware');
var historyApiFallback = require('connect-history-api-fallback');

var app = express();

app.use(compress());

// Apply body Parser and server public assets and routes
app.use(bodyParser.json({ limit: '20mb' }));
app.use(bodyParser.urlencoded({ limit: '20mb', extended: false }));

// app.use(function (req, res, next){
//     if (req.headers['x-forwarded-proto'] === 'https') {
//         res.redirect('http://' + req.hostname + req.url);
//     }else{
//         next();
//     }
// });

app.engine('html', consolidate['swig']);
app.set('view engine', 'html');

app.use(routes);

//new part
app.use(historyApiFallback({
    verbose: false
}));

const compiler = webpack(wpconfig);
const middleware = webpackDevMiddleware(compiler, {
    noInfo: true,
    filename: wpconfig.output.filename,
    publicPath: wpconfig.output.publicPath,
    hot: true,
    progress: true,
    inline: true,
    stats: {
        colors: true
    },
    historyApiFallback: true
});
app.use(middleware);
app.use(webpackHotMiddleware(compiler, {
    log: console.log,
    path: '/__webpack_hmr',
    heartbeat: 10 * 1000
}));
app.use(logger('dev'));
console.log(config.SERVER_SRC_DIR);
app.set('views', path.join(config.SERVER_SRC_DIR, 'views'));
//END new part

app.use('/', index);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.json({
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.json({
        message: err.message,
        error: {}
    });
});

var appServer = app.listen(config.PORT, function (){
    console.log('Server is Up on Port ' + config.PORT);
});

var io = require('socket.io').listen(appServer);

io.sockets.on('connection', function(socket){
    console.log('user connected');
});

module.exports = app;

//TESTING BELOW THIS LINE
