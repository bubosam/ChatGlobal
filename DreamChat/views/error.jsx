var React = require('react');

var DefaultLayout = require('./layout/master');

var ErrorIndexComponent = React.createClass({
    render: function () {
        return (
            <DefaultLayout title = {this.props.title}>
                 <div>
                    <h1>ERROR</h1>
                 </div >
            </DefaultLayout>
         )
}
});

module.exports = ErrorIndexComponent;