var router = require('express').Router();
var index = require('./routes/index');
var login = require('./routes/login');
var register = require('./routes/register');
var requests = require('./routes/requests');
var test = require('./routes/test');
var users = require('./routes/users');

const API = "/api";

// GET home page
router.use(API + '/test', test);
router.use(API + '/login', login);
router.use(API + '/register', register);
router.use(API + '/requests', requests);
router.use(API + '/users', users);
// router.use('/', index);

module.exports = router;