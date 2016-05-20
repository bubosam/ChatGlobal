import React from 'react';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import ContactListComp from 'ContactListComp';
import UserDeskComp from 'UserDeskComp';
import SearchComp from 'SearchComp';

export default class DashBoardComp extends React.Component {

  constructor(props) {
    super(props);
    this.state = {open: true};
  }

  handleToggle = () => this.setState({open: !this.state.open});

  render() {
    return (
      <div>
      <Drawer className="Drawer" open={this.state.open}>
          <ContactListComp/>
          <SearchComp/>
      </Drawer>
      <UserDeskComp/>
      </div>
    );
  }
}
