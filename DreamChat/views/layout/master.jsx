var React = require('react');

var MasterLayout = React.createClass({

    render: function () {

        return (

            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
            <meta charset="utf-8" />
            <title>{this.props.title}</title>
            </head>
            <body>
                {this.props.children}
            </body>
            </html>    
                   
            )
    } 

    });

module.exports = MasterLayout;