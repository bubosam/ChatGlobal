var React = require('react');

var DefaultLayout = require('./layout/register');

var IndexComponent = React.createClass({
    render: function () {
        return (      

            <DefaultLayout title = {this.props.title}>          


            </DefaultLayout>
         );        
    }
});

module.exports = IndexComponent;