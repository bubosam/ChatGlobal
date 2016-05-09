//inserts
var React = require('react');
var Colors = require('material-ui/lib/styles/colors');
var AppBar = require('material-ui/lib/app-bar');
var TextField = require('material-ui//lib/text-field');
var RaisedButton = require('material-ui/lib/raised-button');

var RegisterLayout = React.createClass({
    render: function () {
        return (      

            //webpage body                      
             <html xmlns="http://www.w3.org/1999/xhtml">
             <head>
                 //page head with with

                 <link rel="stylesheet" href="css/main.css" />
                <script src="http://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js" type="text/javascript" async=""></script>
                <meta charset="utf-8" />
                <title>{this.props.title}</title>

             </head>

            //body of page
            <body>
                <form method="post" action="/signin">

                    //app logo
                    <div className='header'>
                       <span className='logo'></span>
                    </div>

                    //menu bar
                    <div className='menu'>
                     <AppBar title="Dream Chat"
                             iconClassNameRight="muidocs-icon-navigation-expand-more"
                             className="app-bar" />
                    </div>

                    //login page logo
                   <div className='register'>
                  </div>

                    //textfields for username and password
              
                  <br /><br />
                   <center>

                    <TextField name="username" id="username" placeholder="e-mail" required="true" />
                   </center>
                  <br />
              

                   <center>
                    <TextField type="password" name="password" id="password" placeholder="password" required="true" />
                   </center>
                 <br />

                  <center>
                    <TextField type="re-enter password" name="password" id="password" placeholder="re-enter password" required="true" />
                 </center>
                <br />

             //buttons
                  <center>
                   <div>//submit registration
                     <RaisedButton label="Register" secondary={true} type="submit" name="signup" id="signup" value="register" 
                                   style={{
                                   margin: 3,
                                   display: 'block',
                                   width: 10 + '%',
                                   float: 'center'
                     }} />
                   </div>
                </center>
                
               <center>
                 <div>//refer to login
                     <RaisedButton label="Back" secondary={true} 
                                   style={{
                                   margin: 3,
                                   display: 'block',
                                   width: 10 + '%',
                                   float: 'center'
                     }} />
                     <a href="/master"></a>
                </div>
              </center>

               </form>
            </body>
          </html>    
                   
          )
        }
});

module.exports = MasterLayout;