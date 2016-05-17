var React = require('react');
var PropTypes = React.PropTypes;
var {Link, IndexLink} = require('react-router');

var RegisterForm = React.createClass({

  render: function() {
    return (
      <div>
          <form method="post" action="/register">
            <input type="text" placeholder="please enter your nickname" name="nickname" id="nickname" required="true"/>
            <input type="text" placeholder="please enter e-mail adress" name="email" id="email" required="true"/>
            <input type="password" placeholder="please enter password" name="password" id="password" required="true"/>
            <button type="submit" className="button expanded">SIGN UP</button>
          </form>
          <div><h5 className="text-center"><IndexLink to="/" activeClassName="active" activeStyle={{fontWeight: 'bold'}}>BACK TO HOME</IndexLink></h5></div>
      </div>
    );
  }

});

module.exports = RegisterForm;
