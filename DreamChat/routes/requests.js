var express = require('express');
var router = express.Router();
var bodyParser = require("body-parser");
var requests = require(appRoot + "/API/requests");


//sending sender id and receiver id to database and creating row with requestid information
router.post('/send', function (req, res) {
    var sender = req.body.userid;
    var receiver = req.body.receiver;

    requests.sendRequest(sender, receiver, function (success) {
        console.log(success);
        res.json(success);
    });	
});

//delete row from database where there is a requestid
router.delete('/cancel', function (req, res) {
    var requestid = req.body.requestid;
    requests.cancelRequest(requestid, function (success) {
        console.log(success);
        res.json(success);
    });
});

//adding friend to table friendships and deleting request from friend_requests
router.post('/accept', function (req, res) {
    var requestid = req.body.requestid;
    requests.acceptRequest(requestid, function (success) {
        console.log(success);
        res.json(success);
    });
});

//delete request from table friend_requests
router.delete('/decline', function (req, res) {
    var requestid = req.body.requestid;
    requests.cancelRequest(requestid, function (success) {
        console.log(success);
        res.json(success);
    });
});

//load pending requests from table friend_requests
router.post('/load', function (req, res) {
    var userid = req.body.userid;
    requests.loadRequests(userid, function (results) {
        res.json(results);
    });
});

module.exports = router;