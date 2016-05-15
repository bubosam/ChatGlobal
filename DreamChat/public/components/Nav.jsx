var React = require('react');
var {Link, IndexLink} = require('react-router');

var Nav = React.createClass({
  onSearch: function (e)  {
    e.preventDefault();
    alert('Test Search!');
  },
  render: function () {
    return (
      <div className="top-bar">
        <div className="top-bar-left">
            <ul className="menu">
              <li className="menu-text">DREAM CHAT</li>
              <li>
                <IndexLink to="/" activeClassName="active" activeStyle={{fontWeight: 'bold'}}>Login</IndexLink>
              </li>
              <li>
                  <Link to="/register" activeClassName="active" activeStyle={{fontWeight: 'bold'}}>Register</Link>
              </li>
              <li>
                  <Link to="/about" activeClassName="active" activeStyle={{fontWeight: 'bold'}}>About</Link>
              </li>
            </ul>
        </div>
        <div className="top-bar-right">
          <form onSubmit={this.onSearch}>
            <ul className="menu">
              <li>
                <input type="search" placeholder="Search..."/>
              </li>
              <li>
                <input type="submit" className="button" value="Search"/>
              </li>
            </ul>
          </form>
        </div>
      </div>
    );
  }
});
module.exports = Nav;
