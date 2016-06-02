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
          code = 200;
        });
      }
      else{
        res.json({
            "message": "authorization failed"
        });
        code = 401;
      }      
  });
});

module.exports = router;
