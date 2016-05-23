var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var users = require(appRoot + "/API/users");

router.post('/load', function (req, res) {
    var userid = req.body.userid;
    users.load(userid, function (user) {
        res.json(user);
    });
});

router.post('/update', function (req, res) {
    var userid = req.body.user;
    users.load(userid, function (success) {
        res.json(success);
    });
});

router.post('/search', function (req, res) {
    var name = req.body.name;
    users.search(name, function (results) {

        console.log(results);
        console.log("jupi");
        res.json(results);
    });
});

module.exports = router;
