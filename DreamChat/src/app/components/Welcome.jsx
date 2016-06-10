import React, {Component} from 'react';
import RaisedButton from 'material-ui/RaisedButton';

const styles = {
  content: {
    marginLeft: 200,
  },

  headline: {
    fontSize: 58,
    padding: 16,
    fontWeight: 200,
  },

  line1: {
    width:600,
    margin:10,
    border:'1px solid black'
  },

  line2: {
    width:500,
    margin:10,
    border:'1px solid black'
  },

  btnGetStarted: {
    width:200,
    height:65,
  },
};

export default class Welcome extends Component {
  constructor(props) {
    super(props);
  }

    render() {
      return(
        <center>
          <div style={styles.content}>
              <div style={styles.headline}>WELCOME FIRSTNAME TO</div>
              <div>
                <img src="http://s33.postimg.org/o0duu0dbj/logo_dereamchat.png"/>
                <div style={styles.line1}></div>
                <div style={styles.line2}></div>
                <RaisedButton style={styles.btnGetStarted} label="GET STARTED" primary={true}/>
              </div>
          </div>
        </center>
      );
    }
}
