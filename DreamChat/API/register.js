var db = require('./DBconnect.js');
var hash = require('password-hash');

module.exports = {
	register: function (nickname, email, password, callback) {
		var hashedPassword = hash.generate(password);
        db.nonQuery("INSERT INTO users(nickname,email,password) VALUES ('" + nickname + "','" + email + "','" + hashedPassword + "')",
        function (success){
			if (typeof callback === "function") {
				callback(success);
			}
        });		
    }
};