var express = require('express');
var db = require(appRoot + '/API/DBconnect.js');
var router = express.Router();

/* GET users listing. */
router.get('/', function (req, res) {
	res.json({"text": "lalalallacjwsuifewiufhwiufhe3iuwfheiuwf"});

});

router.post('/', function (req, res) {
	console.log(req.body.password);
	res.json({"key": "value"});

});

module.exports = router;