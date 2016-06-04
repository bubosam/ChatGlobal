import React, {Component, PropTypes} from 'react';
import {List, ListItem, MakeSelectable} from 'material-ui/List';
import Avatar from 'material-ui/Avatar';
import {blue500, yellow600} from 'material-ui/styles/colors';

let SelectableList = MakeSelectable(List);

const style = {
  margin: 12,
};

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

export default class FriendsProfileComp extends React.Component {
  render() {
      return (
  <div>
    <SelectableList>
      <ListItem
        value={1}
        primaryText="Matias Lajka"
        leftAvatar={<Avatar src="http://showbizgeek.com/wp-content/uploads/2013/04/Screen-Shot-2013-04-29-at-18.57.55.png" />
    }
/>
      <ListItem
        value={3}
        primaryText="Samuel Plavcik"
        leftAvatar={<Avatar src="http://img.csfd.cz/files/images/film/photos/159/259/159259152_2d7948.jpg?w700" />}
      />
      <ListItem
        value={4}
        primaryText="Michael Gorbacov"
        leftAvatar={<Avatar src="http://strategie.hnonline.sk/sites/default/files/obrazky/sprava/tucniacizmadagaskaru.jpg" />}
      />
      <ListItem
        value={5}
        primaryText="Roland Pisio"
        leftAvatar={<Avatar src="http://images5.fanpop.com/image/photos/31200000/Alex-M3EMW-madagascar-3-31228226-1366-768.png" />}
      />
    </SelectableList>
  </div>
  );
 }
}
