var db = require('./DBconnect.js');
var Regex = require("regex");
var regexEmail = new Regex(/^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i);
var regexPassword = new Regex(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/);


module.exports = {
    register: function (nickname, email, password, callback) {
        db.nonQuery("INSERT INTO users(nickname,email,password) VALUES ('" + nickname + "','" + email + "','" + password + "')",
        function (success){
			if (typeof callback === "function") {
				callback(success);
			}
        });		
    }
};