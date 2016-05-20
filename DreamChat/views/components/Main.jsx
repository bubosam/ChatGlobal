import React from 'react';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import {deepOrange500} from 'material-ui/styles/colors';
import AppBar from 'AppBar';

const muiTheme = getMuiTheme({
  palette: {
    accent1Color: deepOrange500,
  },
});

class Main extends React.Component {
  render() {
    return (
       <MuiThemeProvider muiTheme={muiTheme}>
        <div>
            <AppBar/>
            <div>{this.props.children}</div>
        </div>
        </MuiThemeProvider>
    );
  }
}

export default Main;
