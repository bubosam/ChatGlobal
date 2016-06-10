import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';

const styles = {
  style:{margin: 12},
  win:{marginLeft:280},
  pic:{width:40,height:40},
  text:{fontSize: 30, marginLeft:30},
};

export default class ProfileCard extends React.Component {
  render() {
    return (
      <center>
      <div>
      <div>
      <img src="http://cdnstatic.visualizeus.com/thumbs/c1/b3/oink-c1b32f19ddb794be29d5194c1031e8c4_h.jpg"/>
      </div>
      <span style={styles.text}>
        Milos Kocan<br/>
      User Name:Kocarik<br/>
        Age:21<br/>
      Adress: Sobrance,XXX XX<br/>
    School: SSOŠ Dneperska 1
        </span>
    </div>
    </center>
  );
 }
}
