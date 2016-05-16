var db = require('./DBconnect.js');


module.exports = {
	sendRequest: function (senderid,recieverid,callback) {
		db.nonQuery("INSERT INTO friend_requests(sender,receiver) VALUES(" + senderid + ", " + recieverid + ")", function (success) { 
			if (typeof callback === "function") {
				callback(success);
			}
		});
	},

	cancelRequest: function (requestid, callback) {
		db.nonQuery("DELETE FROM friend_requests WHERE requestid="+requestid, function (success) {
			if (typeof callback === "function") {
				callback(success);
			}
		});
	},

	acceptRequest: function (requestid, callback) {
		overallSuccess = true;
		db.query("SELECT * FROM friend_requests  WHERE requestid=" + requestid, function (result) {
			if (result[0].sender != "undefined" && result[0].receiver != "undefined") {
				db.nonQuery("INSERT INTO friendships(user1,user2) VALUES(" + result[0].sender + ", " + result[0].receiver + ")", function (success) {
					if (success) {
						db.nonQuery("DELETE FROM friend_requests WHERE requestid=" + requestid, function (success) {
							if (success) {
								console.log("request accetion successful");
							}
							else {
								console.log("deletion failed while accepting request");
								overallSuccess = false;
							}
						});
					}
					else {
						console.log("insertion failed while accepting request");
						overallSuccess = false;
					}
				});
			}
			else {
				console.log("selection failed while accepting request");
				overallSuccess = false;
			}					
		});
		if (typeof callback === "function") {
			callback(overallSuccess);
		}
	},
	
	declineRequest: function (requestid, callback) {
		db.nonQuery("DELETE FROM friend_requests WHERE requestid=" + requestid, function (success) {
			if (typeof callback === "function") {
				callback(success);
			}
		});
	},

	loadRequests: function (userid,callback) {
		db.query("SELECT userid,name,surname,nickname FROM friend_requests INNER JOIN users ON friend_requests.sender=users.userid WHERE receiver=" + userid, function (results) {
			if (typeof callback === "function") {
				callback(results);
			}
		});
	}
};
