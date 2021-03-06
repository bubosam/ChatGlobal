import React, {Component, PropTypes} from 'react';
import {List, ListItem, MakeSelectable} from 'material-ui/List';
import Avatar from 'material-ui/Avatar';
import Subheader from 'material-ui/Subheader';

let SelectableList = MakeSelectable(List);

function wrapState(ComposedComponent) {
  return class SelectableList extends Component {
    static propTypes = {
      children: PropTypes.node.isRequired,
      defaultValue: PropTypes.number.isRequired,
    };

    componentWillMount() {
      this.setState({
        selectedIndex: this.props.defaultValue,
      });
    }

    handleRequestChange = (event, index) => {
      this.setState({
        selectedIndex: index,
      });
    };

    render() {
      return (
        <ComposedComponent
          value={this.state.selectedIndex}
          onChange={this.handleRequestChange}
        >
          {this.props.children}
        </ComposedComponent>
      );
    }
  };
}

SelectableList = wrapState(SelectableList);

const ContactListComp = () => (
  <div className="contactListFull">
    <SelectableList defaultValue={3}>
      <Subheader>Selectable Contacts</Subheader>
      <ListItem
        value={1}
        primaryText="Brendan Lim"
        leftAvatar={<Avatar src="images/logo_register.png" />}
        nestedItems={[
          <ListItem
            value={2}
            primaryText="Grace Ng"
            leftAvatar={<Avatar src="images/logo_register.png" />}
          />,
        ]}
      />
      <ListItem
        value={3}
        primaryText="Kerem Suer"
        leftAvatar={<Avatar src="images/logo_register.png" />}
      />
      <ListItem
        value={4}
        primaryText="Eric Hoffman"
        leftAvatar={<Avatar src="images/logo_register.png" />}
      />
      <ListItem
        value={5}
        primaryText="Raquel Parrado"
        leftAvatar={<Avatar src="images/logo_register.png" />}
      />
    </SelectableList>
  </div>
);

export default ContactListComp;
