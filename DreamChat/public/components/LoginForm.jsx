var React = require('react');
var PropTypes = React.PropTypes;
var {Link} = require('react-router');

var LoginForm = React.createClass({

  render: function() {
    return (
      <div>
          <form>
            <input type="email" placeholder="please enter e-mail adress"/>
            <input type="password" placeholder="please enter password"/>
            <button className="button expanded">LOGIN</button>
          </form>
          <div><h5 className="text-center"><Link to="/register" activeClassName="active" activeStyle={{fontWeight: 'bold'}}>SIGN UP</Link></h5></div>
      </div>
    );
  }

});

module.exports = LoginForm;
