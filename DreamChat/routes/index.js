var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res) {
    //res.render('index', { title: 'DREAM CHAT' });
    res.sendFile(path.resolve('./views/index.html'));
});

module.exports = router;