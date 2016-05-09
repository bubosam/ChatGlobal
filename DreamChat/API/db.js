var knex = require('knex')({
    client: 'mysql',
    connection: {
        host: 'localhost',  // your host
        user: 'root', // your database user
        password: '', // your database password
        database: 'dreamchat',
        charset  : 'utf8'
    }
});

var DB = require('bookshelf')(knex);

module.exports.DB = DB;