import React, {Component} from 'react';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
import {Link} from 'react-router';

/* import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession; */

export default class AppbarWithNav extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const iconElementRight = <IconMenu
            iconButtonElement={
          <IconButton><MoreVertIcon /></IconButton>
        }
            targetOrigin={{horizontal: 'right', vertical: 'top'}}
            anchorOrigin={{horizontal: 'right', vertical: 'top'}}
        >
            <Link to="http://localhost:1337/"><MenuItem primaryText="Sign out" /></Link>
        </IconMenu>;

        return (
            <AppBar title="Dream Chat" iconElementRight={iconElementRight} />
        );
    }
}
