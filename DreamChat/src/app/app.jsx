import React from 'react';
import ReactDOM from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();
import { browserHistory, Router, Route, IndexRoute } from 'react-router';
import Main from './components/Main.jsx';
import LoginPage from './pages/Login.jsx';
import Register from './components/Register.jsx';
import DashBoard from './pages/DashBoard.jsx';

const target = document.getElementById('app');
const node = <Router history={browserHistory}>
    <Route path="/" component={Main}>
        <Route path="register" component={Register}/>
        <Route path="dashboard" component={DashBoard}/>
        <IndexRoute component={LoginPage}/>
    </Route>
</Router>;

ReactDOM.render(node, target);