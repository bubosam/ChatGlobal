var db = require('./DBconnect.js');

module.exports = {
  new: function(user1, user2, callback){
    var code;
    db.query("SELECT * FROM conversations WHERE (user1="+user1+" AND user2="+user2+") OR (user1="+user2+" AND user2="+user1+")", function(result){
      if(result.length>0){
        if (typeof callback === "function") {
            callback(false,409);
        }
      }
      else{
        db.nonQuery("INSERT INTO conversations(user1,user2) VALUES("+user1+","+user2+")",function(success){
          if (typeof callback === "function") {
              if(success){
                callback(true,200);
              }
              else{
                callback(false,500);
              }
          }
        });
      }
    });
  }

  get: function(userid){
    db.query("SELECT * FROM conversations LEFT JOIN messages ON message.conversationid=conversations.conversationid WHERE user1="+userid+" OR user2="+userid+" ORDER BY messageid DESC")
  }
}
