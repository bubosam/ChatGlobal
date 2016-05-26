var router = require('express').Router();
var index = require('./routes/index');
var test = require('./routes/test');

// GET home page
router.use('/api/test', test);
router.use('*', index);

module.exports = router;