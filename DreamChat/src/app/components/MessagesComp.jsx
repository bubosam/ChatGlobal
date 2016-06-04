import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';

  const style = {
    height: 100,
    width: 200,
    margin: 20,
    textAlign: 'center',
    display: 'inline-block',
  };

export default class MessagesComp extends React.Component {

  render() {
    return (
  <Card>
    <div>
        <Paper style={style} zDepth={3} align="left"/><br />
        <Paper style={style} zDepth={4} align="right"/><br /><br />
      </div>
  </Card>
  );
 }
}
