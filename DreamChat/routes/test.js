var express = require('express');
var db = require(appRoot + '\\API\\DBconnect.js');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res) {
	res.send('respond with a resource');
	db.select("SELECT * FROM users");
});

module.exports = router;