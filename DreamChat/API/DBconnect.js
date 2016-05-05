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
	insert: function(table, object, callback) {
		var query = connection.query('insert into ' + table + ' set ?', object, function (err, result) {
			if (err) {
				console.error(err);
				return;
			}
			console.error(result);
        });
        callback;
	},
	
	insert: function(queryParam, callback) {
		var query = connection.query(queryParam, function (err, result) {
			if (err) {
				console.error(err);
				return;
			}
			console.error(result);
        });
        callback;
	},
	
	select: function(queryParam, callback) {
        var query = connection.query(queryParam, function (err, result) {
            if (!err && result !== undefined) {
                console.log('Results: ', result);
            }                
            else {
                console.log('Error while performing Query.');             
            }
            callback(result);	
        });
	}
};
