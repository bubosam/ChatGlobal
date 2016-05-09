var express = require('express');
var session = require('express-session');
var bcrypt = require('bcrypt-nodejs');
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
global.appRoot = path.resolve(__dirname);

// custom libraries
// routes
var route = require('./routes/route');
var test = require('./routes/test');

// model
var Model = require(appRoot + "\\API\\model");

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.engine('ejs', require('ejs').renderFile);



// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(require('stylus').middleware(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'public')));
app.use(session({ secret: 'secret strategic xxzzz code' }));
app.use(passport.initialize());
app.use(passport.session());

app.use('/test', test);


// pasport
passport.use(new LocalStrategy(function (email, password, done) {
    new Model.User({ email: email }).fetch().then(function (data) {
        var user = data;
        if (user === null) {
            return done(null, false, { message: 'Invalid username or password' });
        } else {
            user = data.toJSON();
            if (!bcrypt.compareSync(password, user.password)) {
                return done(null, false, { message: 'Invalid username or password' });
            } else {
                return done(null, user);
            }
        }
    });
}));

passport.serializeUser(function (user, done) {
    done(null, user.email);
});

passport.deserializeUser(function (email, done) {
    new Model.User({ email: email }).fetch().then(function (user) {
        done(null, user);
    });
});


// GET
app.get('/', route.index);

// signin
// GET
app.get('/signin', route.signIn);
// POST
app.post('/signin', route.signInPost);

// signup
// GET
app.get('/signup', route.signUp);
// POST
app.post('/signup', route.signUpPost);

// logout
// GET
app.get('/signout', route.signOut);

/********************************/

/********************************/
// 404 not found
app.use(route.notFound404);



module.exports = app;

//TESTING BELOW THIS LINE
/*
var login = require(appRoot + "\\API\\login");
login.login("fake@donald.com", "amerikausa", function(success){
	login.access(function (success){
		console.log(success);
	})
}); 
var login = require(appRoot + "\\API\\login");
login.access(3, "ba5364740c82656d6c58e81df4321a", function(success){
    console.log(success);
});


=======
var login = require(appRoot + "\\API\\login");
login.login("zase@prace.com", "staralubovna");
login.login("zase@prace.com", "starazalubovna");
var register = require(appRoot + "\\API\\register");
register.register("Vlagenta","iloveputin@russia.ru","vlakystoja");*/
