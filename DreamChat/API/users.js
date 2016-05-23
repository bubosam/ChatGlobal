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
    substrings.forEach(function(item) {
      db.query("SELECT userid,name,surname,nickname FROM users WHERE name LIKE '%"+item+"%' OR surname LIKE '%" + item + "%' OR nickname LIKE '%" + item + "%' OR email LIKE '%" + item + "%'",
          function (results) {
              allResults.push(results);
              //console.log(results);
              //console.log(allResults);
  		});
    });
    if (typeof callback === "function") {
        callback(allResults);
    }

	},
};
