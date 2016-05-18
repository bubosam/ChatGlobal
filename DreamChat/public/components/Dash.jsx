var React = require('react');
var DashBoardComp = require('DashBoardComp');

var Dash = React.createClass({

  render: function() {
    return (
      <div>
        <h1 className="text-center">Dashboard</h1>
        <DashBoardComp/>
      </div>
    );
  }

});

module.exports = Dash;
