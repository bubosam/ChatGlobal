import React from 'react';
import AppBar from 'material-ui/lib/app-bar';
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();

var MasterLayout = React.createClass({

    render: function () {

        return (

            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <link rel="stylesheet"  href="css/main.css"/>
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

                <div className='login'>



                </div>

                {this.props.children}

            </body>
            </html>

            );
    }

    });

module.exports = MasterLayout;