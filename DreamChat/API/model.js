var DB = require('./db').DB;

var User = DB.Model.extend({
    tableName: 'users',
    idAttribute: 'userID',
});

module.exports = {
    User: User
};