import React from 'react';
import ReactDOM from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();
import { browserHistory, Router, Route, IndexRoute } from 'react-router';
import Main from './components/Main';
import Login from './components/Login';
import Register from './components/Register';
import DashBoard from './components/DashBoard';

ReactDOM.render (
  <Router history={browserHistory}>
    <Route path="/" component={Main}>
      <Route path="register" component={Register}/>
      <Route path="dashboard" component={DashBoard}/>
      <IndexRoute component={Login}/>
    </Route>
  </Router>,
  document.getElementById('app')
);
