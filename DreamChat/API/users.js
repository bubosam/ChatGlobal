var db = require('./DBconnect.js');

module.exports = {
    load: function (userid,callback) {
        db.query("SELECT FROM users WHERE userid="+userid+"",
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

	searchUser: function (name, callback) {
		db.Query("SELECT * FROM users WHERE name LIKE '%"+name+"%' OR surname LIKE '%" + name + "%' OR nickname LIKE '%" + name + "%' OR email LIKE '%" + name + "%'",
        function (results) {
			if (typeof callback === "function") {
				callback(results);
			}
		});
	},
};