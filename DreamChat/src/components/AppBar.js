import React, { PropTypes } from 'react';
import AppBar from 'material-ui/lib/app-bar';

export default React.createClass({

displayName: 'AppBar',
     
     propTypes: {
title: PropTypes.string
},

    render(){
        return(
            <div>
                <AppBar
                title={this.props.title}
                iconClassNameRight="muidocs-icon-navigation-expand-more"
/>
            </div>
);
}    
});