import React, {Component, PropTypes} from 'react';
import {List, ListItem, MakeSelectable} from 'material-ui/List';
import Avatar from 'material-ui/Avatar';
import {pink800, yellow600} from 'material-ui/styles/colors';

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

export default class OthersPComp extends React.Component {
  render() {
      return (
  <div>
    <SelectableList>
      <ListItem
        value={1}
        primaryText="Maros Kovac"
        leftAvatar={<Avatar src="http://www.trbimg.com/img-54935c75/turbine/la-apphoto-dreamworks-animation-all-hail-king-jul-20141218/650/650x366" />
    }
/>
      <ListItem
        value={3}
        primaryText="Samanta Lukacova"
        leftAvatar={<Avatar src="http://images5.fanpop.com/image/photos/30500000/beautiful-Gia-madagascar-3-30561090-653-650.jpg" />}
      />
      <ListItem
        value={4}
        primaryText="Miro Palko"
        leftAvatar={<Avatar src="https://i.ytimg.com/vi/dyn_JBebR2I/maxresdefault.jpg" />}
      />
      <ListItem
        value={5}
        primaryText="Robo Pokora"
        leftAvatar={<Avatar src="http://images4.fanpop.com/image/photos/17500000/Look-at-this-face-and-try-to-say-no-penguins-of-madagascar-17540302-377-349.jpg" />}
      />
    </SelectableList>
  </div>
  );
 }
}
