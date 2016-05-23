﻿var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var authorization = require(appRoot + "/API/authorization");


router.post('/', function (req, res) {
    var email = req.body.email;
    var password = req.body.password;
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

router.delete('/', function (req, res) {
  authorization.authorize(req, function (access) {
      if (access) {
          var userid = req.headers.userid;
        	var token = req.headers.token;
        	var login = require(appRoot + "/API/login");
        	login.logout(userid, token, function (success) {
          		console.log(success);
          		res.json(success);
              res.statusCode = 200;
        	});
      }
      else{
          res.json(false);
          res.statusCode = 401;
      }
  });
});



module.exports = router;
