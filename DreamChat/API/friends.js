var db = require('./DBconnect.js');

module.exports = {
  getFriends: function(userid, callback){
    db.query("SELECT userid,name,surname,nickname FROM friendships INNER JOIN users ON friendships.user2=users.userid WHERE user1="+userid+"",
    function (results1) {
      db.query("SELECT userid,name,surname,nickname FROM friendships INNER JOIN users ON friendships.user1=users.userid WHERE user2="+userid+"",
      function(results2){
        if (typeof callback === "function") {
            console.log(results1);
            console.log(results2);
            console.log(results1.concat(results2));
            callback(results1.concat(results2));
        }
      });
    });
  }
}
