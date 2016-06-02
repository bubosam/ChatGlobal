import React, {Component, PropTypes} from 'react';
import {List, ListItem, MakeSelectable} from 'material-ui/List';
import Avatar from 'material-ui/Avatar';

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

export default class ContactListComp extends React.Component {
  render() {
    return (
  <div>
    <SelectableList>
      <ListItem
        value={1}
        primaryText="Brano David"
        leftAvatar={
          <Avatar src="./images/ok.jpg" />
        }
      />
      <ListItem
        value={2}
        primaryText="Matus Kokoska"
        leftAvatar={
          <Avatar src="./images/ok.jpg" />
        }
      />
      <ListItem
        value={3}
        primaryText="Tomas Muransky"
        leftAvatar={
          <Avatar src="./images/ok.jpg" />
      }
      />
      <ListItem
        value={4}
        primaryText="Marian Osvald"
        leftAvatar={
          <Avatar src="./images/ok.jpg" />
        }
      />
    </SelectableList>
  </div>
  );
 }
}
