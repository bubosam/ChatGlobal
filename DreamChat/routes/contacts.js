var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var authorization = require(appRoot + "/API/authorization");
var friends = require(appRoot + "/API/friends");

router.get('/',function(req,res){
  authorization.authorize(req, function (access) {

      if (access) {
        friends.getFriends(req.headers.userid,function(results){
          res.json(results);
          res.statusCode = 200;
        });
      }
      else{
        res.json({
            "message": "authorization failed"
        });
        res.statusCode  = 401;
      }
  });
});

router.delete('/',function(req,res){
  authorization.authorize(req, function (access) {
      if (access) {
        var friendid=req.body.friendid;;
        friends.removeFriend(req.headers.userid, friendid, function(success, code){
          res.json(success);
          res.statusCode  = code;
        });
      }
      else{
        res.json({
            "message": "authorization failed"
        });
        res.statusCode  = 401;
      }
  });
});

module.exports = router;
