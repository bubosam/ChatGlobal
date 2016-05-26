import React from 'react';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import ContactListComp from './ContactListComp.jsx';
import UserDeskComp from './UserDeskComp.jsx';
import SearchComp from './SearchComp.jsx';

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
