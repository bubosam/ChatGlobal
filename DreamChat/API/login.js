var db = require('./DBconnect.js');

module.exports = {
	login: function (email, password, callback){
        db.select("SELECT userid, COUNT(*) AS count FROM users WHERE email LIKE '" + email + "' AND password LIKE '" + password + "'", 
            function(result) {
            var userid;
            var token;
            if (result != undefined && result[0].count == 1) {
                    userid = result[0].userid;
					console.log("login successful " + result[0].userid);
					require('crypto').randomBytes(15, function (err, buffer) {
						token = buffer.toString('hex');
						console.log(token);
                        db.insert("INSERT INTO tokens VALUES(" + userid + ", '" + token + "')", function(userid,token) {
                            if (callback != undefined) {
                                callback(userid, token);
                            }}
						);
					});               
                }
                else {
                    console.log("login failed");
                    token = undefined;
                if (callback != undefined) {
                    callback(0,"");
                }
                }
        });
	},

	access: function (userid, token, callback){
		db.select("SELECT COUNT(*) AS count FROM tokens WHERE userid = " + userid + " AND token LIKE '" + token + "'", 
            function (result) {
			var access;
			if (result != undefined && result[0].count == 1) {
				console.log("access");
                if (callback != undefined) {
                    callback(true);
                }
			}
			else {
				console.log("access forbidden");
                if (callback != undefined) {
                    callback(false);
                }
			}
		});
	},

	logout: function (userid, token, callback) {
		db.insert("DELETE FROM tokens WHERE userid = " + userid + " AND token LIKE '" + token + "'", 
            function() {
			console.log("user logged out");
        });
        if (callback != undefined) {
            callback();
        }
	}
};
