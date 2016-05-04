var React = require('react');

var DefaultLayout = require('./layout/master');

var IndexComponent = React.createClass({
    render: function () {
        return (
            <DefaultLayout title = {this.props.title}>
                 <div>
                    <h1>Welcome to {this.props.title}!!!</h1>
                 </div >
            </DefaultLayout>
         )
    }
});

module.exports = IndexComponent;