var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var users = require(appRoot + "/API/users");
var authorization = require(appRoot + "/API/authorization");

router.post('/load', function (req, res) {
    var userid = req.body.userid;
    users.load(userid, function (user) {
        res.json(user);
    });
});

router.put('/', function (req, res) {
  authorization.authorize(req, function (access) {
    if(access){
    var userid = req.body.user;
    users.load(userid, function (success) {
        res.json(success);
    });
  }
  else{
    res.json({});
    res.statusCode = 401;
  }
});

router.get('/', function (req, res) {
  authorization.authorize(req, function (access) {
    if(access){
      var key = req.headers.key;
      if(isNaN(key)){
        users.search(key,function(results){
          res.json(results);
          res.statusCode = 200;
        });
      }
      else{
        users.load(key,function(results){
          res.json(results);
          res.statusCode = 200;
        });
      }
    }
    else{
      res.json({});
      res.statusCode = 401;
    }
  });




});

module.exports = router;
