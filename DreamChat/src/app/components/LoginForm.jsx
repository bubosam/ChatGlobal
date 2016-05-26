import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {Link} from 'react-router';

export default class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.handleSubmitButtonClick = this.handleSubmitButtonClick.bind(this);
    }

    handleSubmitButtonClick() {
        var email = this.refs.email.getValue();
        var password = this.refs.password.getValue();

        var data = {
            email: email,
            password: password
        };
    }

    render() {
        return (
            <div className="login-form">
            <h1 className="text-center">LOGIN FORM</h1>

                <TextField
                    ref="email"
                    hintText="Please enter your e-mail adress"
                    name="email"
                /><br />
                <TextField
                    hintText="Please enter your password"
                    name="password"
                    type="password"
                    ref="password"
                /><br />
                <RaisedButton onClick={this.handleSubmitButtonClick} type="submit" label="LOGIN" primary={true}/>

            <h4><div><Link to="/register">SIGN UP</Link></div></h4>
            <h4><div><Link to="/dashboard">DASHBOARD</Link></div></h4>
        </div>
        );
    }
}