var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");


//sending sender id and receiver id to database and creating row with requestid information
router.post('/', function (req, res) {
    var sender = req.body.userid;
    var receiver = req.body.receiver;
    var sendReq = require(appRoot + "/API/requests");
    var data = {
        "sender": 0,
        "receiver": 0
    };
    sendReq.sendRequest(sender, receiver, function (sender, receiver) {
        data["sender"] = sender;
        data["receiver"] = receiver;
        console.log(data);
        res.json(data);
    });	
});

//delete row from database where there is a requestid
router.post('/', function (req, res) {
    var requestid = req.body.requestid;
    var cancelReq = require(appRoot + "/API/requests");
    cancelReq.cancelRequest(requestid, function (success) {
        console.log(success);
        res.json(success);
    });
});

//adding friend to table friendships and deleting request from friend_requests
router.post('/', function (req, res) {
    var requestid = req.body.requestid;
    var acceptReq = require(appRoot + "/API/requests");
    acceptReq.acceptRequest(requestid, function (success) {
        console.log(success);
        res.json(success);
    });
});

//delete request from table friend_requests
router.post('/', function (req, res) {
    var requestid = req.body.requestid;
    var declineReq = require(appRoot + "/API/requests");
    declineReq.declineRequest(requestid, function (success) {
        console.log(success);
        res.json(success);
    });
});

//load pending requests from table friend_requests
router.get('/', function (req, res) {
    var userid = req.body.userid;
    var loadReq = require(appRoot + "/API/requests");
    loadReq.loadRequests(requestid, function (success) {
        res.json(results);
    });
});

module.exports = router;