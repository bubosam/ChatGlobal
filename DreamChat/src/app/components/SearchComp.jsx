import React from 'react';
import TextField from 'material-ui/TextField';
import FontIcon from 'material-ui/FontIcon';
import IconButton from 'material-ui/IconButton';
import ActionHome from 'material-ui/svg-icons/action/search';


export default class SearchComp extends React.Component {
  render() {
    return (
<form>
  <div className = "search">
    <TextField
      hintText="Search"
    />
     <IconButton linkButton={true}
      href="/friendboard">
       <ActionHome />
     </IconButton>
    </div>
    </form>
  );
 }
}
