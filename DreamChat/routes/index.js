var express = require('express');
var router = express.Router();
var authorization = require(appRoot + "/API/authorization");

/* GET home page. */
router.get('/', function (req, res) {
    authorization.authorize(req, function (access) {
        if (access) {
            res.render('index', { title: 'DREAM CHAT' });
        }
        else {
            res.render('login', { title: 'DREAM CHAT' });
        }
    })
    //res.sendFile(path.resolve('./public/index.html'));
     res.render('index', { title: 'DREAM CHAT' });
});

module.exports = router;
