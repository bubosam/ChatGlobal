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
                        "message": "request canceled successfully"
                    });
                    res.statusCode = 200;
                }
                else {
                    res.json({
                        "message": "unexpected error occured while deleting"
                    });
                    res.statusCode = 500;
                }
            });
        }    
        else {
            res.json({
                "message": "authorization failed"
            });
            res.statusCode = 401;
        }
    });
});

//adding friend to table friendships and deleting request from friend_requests
router.post('/accept', function (req, res) {
    var code;
    authorization.authorize(req, function (access) {
        if (access) {
            var requestid = req.body.requestid;
            requests.acceptRequest(requestid, req.headers.userid, function (success,statuscode,message) {
                code = statuscode;
                if (success) {
                    res.json({
                        message: message
                    });
                }
                else {
                    var response= {
                        "message": message
                    }
                    console.log("holahe4756j");
                    res.json(response);
                    console.log("holahe4756j");
                }
            });
        }    
        else {
            res.json({
                "message": "authorization failed"
            });
            code = 401;
        }
        //res.statusCode = code;
    });
});

//load pending requests from table friend_requests
router.get('/', function (req, res) {
    var code;
    var userid = req.headers.userid;
    authorization.authorize(req, function (access) {
        if (access) {           
            requests.loadRequests(userid, function (results) {
                var response = {
                    results: results,
                    message: "requests loaded successfully"
                };
                res.json(response);
                res.statusCode = 200;
            });
        }
        else {
            res.json({
                "message": "authorization failed"
            });
            res.statusCode = 401;
        }
    });    
});

module.exports = router;