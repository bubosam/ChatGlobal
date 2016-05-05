var db = require('./DBconnect.js');

module.exports = {
	login: function (email, password){
        db.select("SELECT userid, COUNT(*) AS count FROM users WHERE email LIKE '" + email + "' AND password LIKE '" + password + "'", 
            function chceckLogin(result) {
            var userid;
            var token;
            if (result != undefined && result[0].count == 1) {
                    userid = result[0].userid;
                console.log("login successful " + result[0].userid);
                require('crypto').randomBytes(10, function (err, buffer) {
                    token = buffer.toString('hex');
                    console.log(token);
                });
                
                }
                else {
                    console.log("login failed");
                    token = undefined;
                }
        });
	}
};
