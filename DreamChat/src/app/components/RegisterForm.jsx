import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
var {Link, IndexLink} = require('react-router');

export default class RegisterForm extends React.Component {
  render() {
    return (
  <div className="register-form">
    <h1>Register Form</h1>
    <form method="post" action="/register">
      <TextField
        hintText="Please enter your nickname"
        name="nickname"
      /><br />
      <TextField
        hintText="Please enter your e-mail adress"
        name="email"
      /><br />
      <TextField
        hintText="Please enter your password"
        name="password"
        type="password"
      /><br />
      <TextField
        hintText="Please enter your phone"
        name="phone"
      /><br />
      <TextField
        hintText="Please enter your name"
        name="name"
      /><br />
      <TextField
        hintText="Please enter your suername"
        name="suername"
      /><br />
    <RaisedButton type="submit" label="REGISTER" primary={true}/>
       <div><h4 className="text-center"><IndexLink to="/" activeClassName="active" activeStyle={{fontWeight: 'bold'}}>BACK TO HOME</IndexLink></h4></div>
    </form>
  </div>
  );
 }
}
