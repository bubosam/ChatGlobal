var db = require('./DBconnect.js');

module.exports = {
    register: function (nickname, email, password, callback) {
        db.insert("INSERT INTO users(nickname,email,password) VALUES ('" + nickname + "','" + email + "','" + password + "')",
        function (err, res){
            if (err) throw err;
        });
    }
};