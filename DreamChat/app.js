var express = require('express');
var path = require('path');
global.appRoot = path.resolve(__dirname);
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');


var routes = require('./routes/index');
var test = require('./routes/test');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, '/views'));
app.set('view engine', 'jsx');
app.engine('jsx', require('express-react-views').createEngine());


// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(require('stylus').middleware(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', routes);
app.use('/test', test);


// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});



// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function (err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function (err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});


module.exports = app;

//TESTING BELOW THIS LINE


/*<<<<<<< HEAD
var login = require(appRoot + "\\API\\login");
login.login("fake@donald.com", "amerikausa", function(success){
	login.access(function (success){
		console.log(success);
	})
});
login.login("zase@prace.com", "starazalubovna", function (success) {
	console.log(success);
});
=======
var login = require(appRoot + "\\API\\login");
login.login("zase@prace.com", "staralubovna");
login.login("zase@prace.com", "starazalubovna");
var register = require(appRoot + "\\API\\register");
register.register("Emilko","emilko@mrkvicka.hu","Quatro123");
>>>>>>> a195637d16f3cebb71275b9eba5f8119b9fc2033*/
