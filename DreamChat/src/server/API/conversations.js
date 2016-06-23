var db = require('./DBconnect.js');

module.exports = {
  newMessage: function(user1, user2, message, callback){
    var code;
    var id;
    db.query("SELECT * FROM conversations WHERE (user1="+user1+" AND user2="+user2+") OR (user1="+user2+" AND user2="+user1+")", function(result){
      if(result.length>0){

        id=result[0].conversationID;
        db.nonQuery("INSERT INTO messages(conversationid,userid,message) VALUES("+id+","+user1+",'"+message+"')",function(success){
    		  if (typeof callback === "function") {
                  callback(success,id);
              }
        });
      }
      else{
        db.nonQuery("INSERT INTO conversations(user1,user2) VALUES("+user1+","+user2+")",function(success){
          if(success){
            db.nonQuery("INSERT INTO messages(conversationid,userid,message) VALUES((SELECT conversationid FROM conversations ORDER BY conversationid LIMIT 1),"
                        +user1+",'"+message+"')",function(success2){
              if (typeof callback === "function") {
                  db.query("SELECT * FROM conversations WHERE (user1="+user1+" AND user2="+user2+") OR (user1="+user2+" AND user2="+user1+")",function(result){
                    if(result.length!=0){
                      callback(success2,result.conversationid);
                    }
                    else{
                      callback(success, 0);
                    }
                  });

              }
            });
          }
          else{
            if (typeof callback === "function") {
                callback(false,0);
            }
          }
        });
      }
    });
  },

  get: function(userid, callback){
    db.query("SELECT * FROM conversations LEFT JOIN messages ON message.conversationid=conversations.conversationid "+
              "WHERE user1="+userid+" OR user2="+userid+" ORDER BY messageid DESC", function(results){
          if (typeof callback === "function") {
                callback(results);
          }
    });
  },

  getRecentMessages: function(conversationid, limit, callback){
    db.query("SELECT * FROM messages WHERE conversationid="+conversationid+" ORDER BY messageID DESC LIMIT "+limit, function(results){
      if (typeof callback === "function") {
            callback(results);
      }
    });
  },

  getMoreMessages: function(conversationid, limit, lastMessageId, callback){
    db.query("SELECT * FROM messages WHERE conversationid="+conversationid+" AND messageid<"+lastMessageId+" ORDER BY messageID DESC LIMIT "+limit, function(results){
      if (typeof callback === "function") {
            callback(results);
      }
    });
  }
}
