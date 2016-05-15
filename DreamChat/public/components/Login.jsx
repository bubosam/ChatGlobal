var React = require('react');
var LoginForm = require('LoginForm');

var Login = React.createClass({

  render: function() {
    return (
      <div>
        <h1 className="text-center">LOGIN FORM</h1>
        <LoginForm/>
      </div>
    );
  }

});

module.exports = Login;
