import React, {Component, PropTypes} from 'react';
import {Tabs, Tab} from 'material-ui/Tabs';
import FloatingActionButton from 'material-ui/FloatingActionButton';
import ContentAdd from 'material-ui/svg-icons/content/add';
import ProfileCard from './ProfileCard.jsx';
import MessagesComp from './MessagesComp.jsx';
import {CardHeader} from 'material-ui/Card';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

const styles = {
  headline: {
    fontSize: 24,
    paddingTop: 16,
    marginBottom: 12,
    fontWeight: 200,
  },
  mapping: {
    marginTop: 10,
    marginLeft:260,
  },
  flouting: {
    marginRight: 20,
  },
};

export default class UserTabsComp extends React.Component {

  constructor(props) {
    super(props);

  }


  render() {
    return (
      <div className="user-desk-form">
      <Tabs style={styles.mapping} >
        <Tab label="Profile" value="a" >
          <div>
            <center>
              <h2 style={styles.headline}>Profile</h2>
          <ProfileCard/>
    <FloatingActionButton style={styles.flouting}>
      <ContentAdd />
    </FloatingActionButton>
            </center>
          </div>
        </Tab>
        <Tab label="Messages" value="b">
          <div>
            <center>
              <h2 style={styles.headline}>Messages</h2>
              <CardHeader
                title="Tomas Muransky"
                subtitle="KocurMurko"
                avatar="http://img.lum.dolimg.com/v1/images/eu_finding_nemo_chi_squirt_n_1c9ff515.jpeg"
              />
            <MessagesComp/>
              <TextField
            hintText="Type your message"
            />
          <RaisedButton onClick={this.handleSubmitButtonClick} type="submit" label="SEND MESSAGE" secondary={true}/>
            </center>
          </div>
        </Tab>
      </Tabs>
      </div>
    );
  }
}
