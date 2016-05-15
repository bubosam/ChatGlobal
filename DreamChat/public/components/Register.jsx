var React = require('react');
var RegisterForm = require('RegisterForm');

var Register = React.createClass({

  render: function() {
    return (
      <div>
        <h1 className="text-center">REGISTER FORM</h1>
        <RegisterForm/>
      </div>
    );
  }

});

module.exports = Register;
