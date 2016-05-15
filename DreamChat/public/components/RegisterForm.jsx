var React = require('react');
var PropTypes = React.PropTypes;

var RegisterForm = React.createClass({

  render: function() {
    return (
      <div>
          <form>
            <input type="text" placeholder="please enter your username"/>
            <input type="email" placeholder="please enter e-mail adress"/>
            <input type="password" placeholder="please enter password"/>
            <button className="button expanded">SIGN UP</button>
          </form>
      </div>
    );
  }

});

module.exports = RegisterForm;
