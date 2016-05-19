var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var requests = require(appRoot + "/API/requests");
var authorization = require(appRoot + "/API/authorization");


//sending sender id and receiver id to database and creating row with requestid information
router.post('/', function (req, res) {
    authorization.authorize(req, function (access) { 
        if (access) {
            var sender = req.headers.userid;
            var receiver = req.body.receiver;
            
            requests.sendRequest(sender, receiver, function (success,code) {
                if (success) {
                    res.json({
                        "message": "request sent successfully"
                    });
                }
                else {
                    res.json({
                        "message": "sending request failed, please try again later"
                    });
                }
            });
        }
        else {
            res.json({
                "message": "authorization failed"
            });
            code = 401;
        }
        res.statusCode = code;
    });    
});

//delete row from database where there is a requestid
router.delete('/', function (req, res) {
    authorization.authorize(req, function (access) {
        if (access) {
            var requestid = req.body.requestid;
            requests.cancelRequest(requestid, function (success) {
                if (success) {
                    res.json({
                        "message": "request canceled successfully",
                        "error": 0,
                        "success": true
                    });
                }
                else {
                    res.json({
                        "message": "deleting request failed, please try again later",
                        "error": 1,
                        "success": false
                    });
                }
            });
        }    
        else {
            res.json({
                "message": "authorization failed",
                "error": 2,
                "success": false
            });
        }
    });
});

//adding friend to table friendships and deleting request from friend_requests
router.post('/accept', function (req, res) {
    authorization.authorize(req, function (access) {
        if (access) {
            var requestid = req.body.requestid;
            requests.acceptRequest(requestid, function (success) {
                if (success) {
                    res.json({
                        "message": "friend successfully added",
                        "error": 0,
                        "success": true
                    });
                }
                else {
                    res.json({
                        "message": "accepting request failed, please try again later",
                        "error": 1,
                        "success": false
                    });
                }
            });
        }    
        else {
            res.json({
                "message": "authorization failed",
                "error": 2,
                "success": false
            });
        }
    });
});

//load pending requests from table friend_requests
router.get('/', function (req, res) {
    var userid = req.headers.userid;
    requests.loadRequests(userid, function (results) {
        res.json(results);
    });
});

module.exports = router;