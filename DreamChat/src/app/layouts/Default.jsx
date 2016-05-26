import React, {Component} from 'react';
import AppBar from '../components/AppBar.jsx';

export default class Login extends Component {
    render() {
        return (
            <div>
                <AppBar />
                <div>
                    {this.props.children}
                </div>
            </div>
        );
    }
}
