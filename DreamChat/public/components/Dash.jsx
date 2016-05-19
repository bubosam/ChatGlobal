var React = require('react');
var DashBoardComp = require('DashBoardComp');

var Dash = React.createClass({

  render: function() {
    return (
      <div>
      <h2 className="text-center">Profile Page</h2>
        <DashBoardComp/>
      </div>
    );
  }
});

module.exports = Dash;
