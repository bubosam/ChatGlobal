import React, {Component, PropTypes} from 'react';
import {Tabs, Tab} from 'material-ui/Tabs';

const styles = {
  headline: {
    fontSize: 24,
    paddingTop: 16,
    marginBottom: 12,
    fontWeight: 200,
  },
};

export default class UserDeskComp extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      value: 'a',
    };
  }

  handleChange = (value) => {
    this.setState({
      value: value,
    });
  };

  render() {
    return (
      <div className="user-desk-form">
      <Tabs
        value={this.state.value}
        onChange={this.handleChange}
      >
        <Tab label="Profile" value="a" >
          <div>
            <center>
              <h2 style={styles.headline}>Profile</h2>
            <p>
              Profile
            </p>
          </center>
          </div>
        </Tab>
        <Tab label="Messages" value="b">
          <div>
            <center>
              <h2 style={styles.headline}>Messages</h2>
            <p>
            Messages
            </p>
            </center>
          </div>
        </Tab>
      </Tabs>
      </div>
    );
  }
}

export default UserDeskComp;
