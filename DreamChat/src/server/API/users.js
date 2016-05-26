var db = require('./DBconnect.js');

module.exports = {
    load: function (userid,callback) {
        db.query("SELECT userid,name,surname,nickname FROM users WHERE userid="+userid+"",
        function (result) {
            if (typeof callback === "function") {
                callback(result[0]);
            }
        });
    },

    update: function (user,callback) {
        db.nonQuery("UPDATE users SET nickname:'" + user.nickname + "', password:'" + user.password +
                    "', email:'" + user.email + "', phone:'" + user.phone + "', name:'" + user.name +
                    "', surname:'" + user.surname + "', ",
        function (success) {
            if (typeof callback === "function") {
                callback(success);
            }
        });
	},

	search: function (name, callback) {
    var substrings = name.split(" ");
    var allResults = [];
    for (var i = 0, len = substrings.length; i < len; i++){
      (function(i){
        db.query("SELECT userid,name,surname,nickname FROM users WHERE name LIKE '%"+substrings[i]+"%' OR surname LIKE '%" + substrings[i] + "%' OR nickname LIKE '%"
                  + substrings[i] + "%' OR email LIKE '%" + substrings[i] + "%'",  function (results) {
                allResults=allResults.concat(results);
                //console.log(results);
                //console.log(allResults);
                console.log(i);
                if (typeof callback === "function" && i+1==len) {
                    console.log("tu");
                    var uniqueUsers = [];
                    var uniqueNicknames = [];
                    for(j = 0; j< allResults.length; j++){
                        console.log(allResults[j].nickname);
                        if(uniqueNicknames.indexOf(allResults[j].nickname === -1)){
                            uniqueNicknames.push(allResults[j].nickname);
                            uniqueUsers.push(allResults[j]);
                            //console.log(uniqueNicknames);
                        }
                    }
                    console.log(uniqueNicknames);
                    callback(uniqueUsers);
                }
        });
  		})(i);
    }
	},
};
