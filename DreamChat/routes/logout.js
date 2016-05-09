var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");

router.post('/', function (req, res) {
	var userid = req.query.userid;
	var token = req.query.token;
	var login = require(appRoot + "/API/login");
	login.logout(userid, token, function (success) {
		console.log(success);
		res.json(success);
	});
});



module.exports = router;