var mysql = require('mysql');

var connection = mysql.createConnection({
	host: 'localhost',
	user: 'root',
	password: '',
	database: 'dreamchat'
});
connection.connect();

//TO DO add comments

module.exports = {
	insertObject: function(table, object, callback) {
		var query = connection.query('insert into ' + table + ' set ?', object, function (err, result) {
			if (err) {
				console.error(err);
				if (typeof callback === "function") {
					callback(false);
				}
			}
			else {
				if (typeof callback === "function") {
					callback(true);
				}
			}
			console.error(result);
		});
	},
	
	nonQuery: function(queryParam, callback) {
		var query = connection.query(queryParam, function (err, result) {
			if (err) {
				console.error(err);
				if (typeof callback === "function") {
					callback(false);
				}
			}
			else {
				if (typeof callback === "function") {
					callback(true);
				}
			}
			console.error(result);
        });
		
	},
	
	query: function(queryParam, callback) {
        var query = connection.query(queryParam, function (err, result) {
            if (!err && result !== undefined) {
				console.log('Results: ', result);
				if (typeof callback === "function") {
					callback(result);
				}
            }                
            else {
				console.log('Error while performing Query.');
				if (typeof callback === "function") {
					callback(undefined);
				}         
            }
			
        });
	}
};
