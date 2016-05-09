var express = require('express');
var router = express.Router();
//var http = require('http').Server(app); // http server
var bodyParser = require("body-parser"); // Body parser for fetch posted data

router.post('/login', function (req, res) {
    var email = req.body.email;
    var password = req.body.password;
    var login = require(appRoot + "\\API\\login");
    var data = {
        "userid": 0,
        "token": ""
    };
    login.login(email, password, function (userid, token) {
        data["userid"] = userid;
        data["token"] = token;
        res.json(data);
    });
});

module.exports = router;