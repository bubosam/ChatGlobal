var React = require('react');
var Colors = require('material-ui/lib/styles/colors');
var AppBar = require('material-ui/lib/app-bar');
var TextField = require('material-ui//lib/text-field');
var RaisedButton = require('material-ui/lib/raised-button');

var MasterLayout = React.createClass({


    render: function () {

        return (

            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <link rel="stylesheet" href="css/main.css" />
                <script src="http://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js" type="text/javascript" async=""></script>
                <meta charset="utf-8" />
            <title>{this.props.title}</title>
            </head>
            <body>
                 <div className='header'>
                    <span className='logo'></span>
                 </div>

               <div className='menu'>
                    <AppBar title="Dream Chat"
                            iconClassNameRight="muidocs-icon-navigation-expand-more"
                            className="app-bar" />
               </div>

                 <div className='register'>
                 </div>

<div>
     <br /><br />
<center>
     <TextField hintText='UserName' />
</center>

<br />

<center>
<TextField hintText='Password'
        type="password" />
</center>
             <br />
             <center>
<TextField hintText='Re-Enter Password'
        type="password" />
             </center>
             <br />
         </div>
 
<center>
  <div>
<RaisedButton label="Register" secondary={true} style={{
    margin: 3,
    display: 'block',
    width: 10 + '%',
    float: 'center'
}} />
         </div>
 </center>
         <center>
         <div>
    <RaisedButton label="Cancel" secondary={true} style={{
    margin: 3,
    display: 'block',
    width: 10 + '%',
float: 'center'
}} />
</div>
</center>
   </body>
   </html>    
                   
            )
} 

});

module.exports = MasterLayout;