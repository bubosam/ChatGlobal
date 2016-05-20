var express = require('express');
var router = express.Router();
var authorization = require(appRoot + "/API/authorization");

/* GET home page. */
router.get('/', function (req, res) {
<<<<<<< HEAD
    authorization.authorize(req, function (access) {
        if (access) {
            res.render('index', { title: 'DREAM CHAT' });
        }
        else {
            res.render('login', { title: 'DREAM CHAT' });
        }
    })
    //res.sendFile(path.resolve('./public/index.html'));
=======
     res.render('index', { title: 'DREAM CHAT' });
>>>>>>> d4c515a73e84338ca62b8a74b3c5de5f1335ad0b
});

module.exports = router;
