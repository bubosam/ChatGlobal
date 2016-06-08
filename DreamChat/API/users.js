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

	search: function (name, userid, callback) {
    var substrings = name.split(" ");
    var queryString="SELECT userid,name,surname,nickname, CASE "
                  +"WHEN userid IN (SELECT userid FROM friendships "
                  +"INNER JOIN users ON friendships.user2=users.userid "
                  +"WHERE user1="+userid+") "
                  +"OR userid IN (SELECT userid FROM friendships "
                  +"INNER JOIN users ON friendships.user1=users.userid "
                  +"WHERE user2="+userid+") "
                  +"THEN 1 "
                  +"ELSE 0 "
                  +"END "
                  +"'isFriend' "
                  +"FROM users WHERE ";
    for (var i = 0, len = substrings.length; i < len; i++){
      (function(i){
         queryString+="(name LIKE '%"+substrings[i]+"%' OR surname LIKE '%" + substrings[i]
          + "%' OR nickname LIKE '%"+ substrings[i] + "%' OR email LIKE '%" + substrings[i] + "%')";
          if(i+1<len){
            queryString+=" or ";
          }
          else{
            queryString+=" ORDER BY `isFriend` DESC";
            console.log(queryString);
            db.query(queryString,function (results){
              if (typeof callback === "function") {
                  callback(results);
              }
            });
          }
  		})(i);
    }
	},
};
