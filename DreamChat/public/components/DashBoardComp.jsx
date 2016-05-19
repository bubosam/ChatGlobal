var React = require('react');
var PropTypes = React.PropTypes;
var {Link} = require('react-router');


var DashBoardComp = React.createClass({


  render: function() {
    return (
      <div>
      <input type="text" name="title" /><input type="button" name="searchbutton" value="Search" onClick="search()" />
      </div>
    );
  }
});

module.exports = DashBoardComp;
