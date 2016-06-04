//sidebar with contacts and searchbar
import React from 'react';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import ContactListComp from './ContactListComp.jsx';
import SearchComp from './SearchComp.jsx';
import Divider from 'material-ui/Divider';

export default class DrawerComp extends React.Component {

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
          <Divider />
          <SearchComp/>
      </Drawer>
      </div>
    );
  }
}
