import React, {Component} from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {Link} from 'react-router';
import Paper from 'material-ui/Paper';

const styles={
  pap:{height: 300,
  width: 400,
  margin: 20,
  textAlign: 'center',
  display: 'inline-block'},
  pos:{marginTop:1+'%'},
};

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
          <div style={styles.pos}>
          <center>
          <Paper style={styles.pap} zDepth={1} >
            <div>
            <h1 className="text-center">LOGIN</h1>

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
              <RaisedButton style={{marginTop:20}} onClick={this.handleSubmitButtonClick} containerElement={<Link to="/welcomepage" />} type="submit" label="LOGIN" primary={true}/>

            <h4><div><Link to="/register">SIGN UP</Link></div></h4>
          </div>
        </Paper>
        </center>
        </div>
        );
    }
}
