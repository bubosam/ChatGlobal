import React from 'react';
var PropTypes = React.PropTypes;
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
var {Link} = require('react-router');

const LoginForm = () => (
  <div className="login-form">
    <h1 className="text-center">LOGIN FORM</h1>
    <form method="post" action="/login">
      <TextField
        hintText="Please enter your e-mail adress"
        name="email"
      /><br />
      <TextField
        hintText="Please enter your password"
        name="password"
        type="password"
      /><br />
    <RaisedButton type="submit" label="LOGIN" primary={true}/>
    </form>
    <h4><div><Link to="/register">SIGN UP</Link></div></h4>
    <h4><div><Link to="/dashboard">DASHBOARD</Link></div></h4>
  </div>

);

export default LoginForm;
