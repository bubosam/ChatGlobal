var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");

router.post('/', function (req, res) {
    var email = req.query.email;
    var password = req.query.password;
    var login = require(appRoot + "/API/login");
	var data =  {
		"userid": 0,
        "token": ""
	};
	login.login(email, password, function (userid, token) {
		data["userid"] = userid;
		data["token"] = token;
		console.log(data);
		res.json(data);
	});

	
});



module.exports = router;