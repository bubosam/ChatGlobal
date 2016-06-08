import React from 'react';
import {Card, CardActions, CardHeader, CardMedia, CardTitle, CardText} from 'material-ui/Card';

export default class ProfileCard extends React.Component {
  render() {
    return (
  <Card>
    <CardHeader
      title="Tomas Muransky"
      subtitle="KocurMurko"
      avatar="http://img.lum.dolimg.com/v1/images/eu_finding_nemo_chi_squirt_n_1c9ff515.jpeg"
    />
    <CardText>
      <p>
        Age:21<br/>
      Adress: Nad jazerom,Kosice,040 01<br/>
        School: SSOS Dneperska 1
      </p>
    </CardText>
  </Card>
  );
 }
}
