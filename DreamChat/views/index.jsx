var React = require('react');

var DefaultLayout = require('./layout/master');

var IndexComponent = React.createClass({
    render: function () {
        return (

            <DefaultLayout name = {this.props.title}>
                 <div>
                    <h1>Using ReactJS</h1>
                 </div >
            </DefaultLayout>

         )      
    }
});

module.exports = IndexComponent;