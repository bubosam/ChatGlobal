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
	insert: function(table, object) {
		var query = connection.query('insert into ' + table + ' set ?', object, function (err, result) {
			if (err) {
				console.error(err);
				return;
			}
			console.error(result);
		});
	},
	
	insert: function(queryParam) {
		var query = connection.query(queryParam, function (err, result) {
			if (err) {
				console.error(err);
				return;
			}
			console.error(result);
		});
	},
	
	select: function(queryParam) {
		var query = connection.query(queryParam, function (err, result) {
			console.log(query.sql);
		});
	}
};
