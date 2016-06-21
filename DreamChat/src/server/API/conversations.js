var db = require('./DBconnect.js');

module.exports = {
  newMessage: function(user1, user2, message, callback){
    var code;
    db.query("SELECT * FROM conversations WHERE (user1="+user1+" AND user2="+user2+") OR (user1="+user2+" AND user2="+user1+")", function(result){
      if(result.length>0){
        db.nonQuery("INSERT INTO messages(coversaionid,userid,message) VALUES("+result.conversationid+","+user1+",'"+message+"')",function(success){
          if (typeof callback === "function") {
              callback(success);
          }
        });
      }
      else{
        db.nonQuery("INSERT INTO conversations(user1,user2) VALUES("+user1+","+user2+")",function(success){
          if(success){
            db.nonQuery("INSERT INTO messages(coversationid,userid,message) VALUES((SELECT conversationid FROM conversations ORDER BY conversationid LIMIT1),"
                        +user1+",'"+message+"')",function(success2){
              if (typeof callback === "function") {
                  callback(success2);
              }
            });
          }
          else{
            if (typeof callback === "function") {
                callback(false);
            }
          }
        });
      }
    });
  },

  get: function(userid){
    db.query("SELECT * FROM conversations LEFT JOIN messages ON message.conversationid=conversations.conversationid "+
              "WHERE user1="+userid+" OR user2="+userid+" ORDER BY messageid DESC", function(results){
          if (typeof callback === "function") {
                callback(results);
          }
    });
  },

  getRecentMessages: function(conversationid, limit){
    db.query("SELECT * FROM messages WHERE conversationid="+conversationid+" ORDER BY messageID DESC LIMIT "+limit, function(results){
      if (typeof callback === "function") {
            callback(results);
      }
    });
  },

  getMoreMessages: function(conversationid, limit, lastMessageId){
    db.query("SELECT * FROM messages WHERE conversationid="+conversationid+" AND messageid<"+lastMessageId+" ORDER BY messageID DESC LIMIT "+limit, function(results){
      if (typeof callback === "function") {
            callback(results);
      }
    });
  }
}
