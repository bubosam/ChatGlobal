import React from 'react';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
var {Link} = require('react-router');


const AppbarWithNav = () => (
  <AppBar
    title="Dream Chat"
    iconElementRight={
      <IconMenu
        iconButtonElement={
          <IconButton><MoreVertIcon /></IconButton>
        }
        targetOrigin={{horizontal: 'right', vertical: 'top'}}
        anchorOrigin={{horizontal: 'right', vertical: 'top'}}
      >
        <Link to="http://localhost:1337/"><MenuItem primaryText="Sign out" /></Link>
      </IconMenu>
    }
  />
);


export default AppbarWithNav;
