import React from 'react';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import MenuItem from 'material-ui/MenuItem';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
var {Link} = require('react-router');

/* import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; */


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

/*
public class LogoutServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                                throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out=response.getWriter();

            request.getRequestDispatcher("/Login").include(request, response);

            HttpSession session=request.getSession();
            session.invalidate();

            out.print("You are successfully logged out!");

            out.close();
    }
}
*/

export default AppbarWithNav;
