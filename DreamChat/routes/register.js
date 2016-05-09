var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");

router.post('/', function (req, res) {
	var email = req.query.email;
	var password = req.query.password;
	var nickname = req.query.nickname;
	var reg = require(appRoot + "/API/register");
	reg.register(nickname, email, password, function (success) {
		console.log(success);
		res.json(success);
	});
});



module.exports = router;