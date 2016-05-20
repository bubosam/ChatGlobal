import React from 'react';
import TextField from 'material-ui/TextField';
import FontIcon from 'material-ui/FontIcon';
import IconButton from 'material-ui/IconButton';
import ActionHome from 'material-ui/svg-icons/action/search';


const SearchComp = () => (
<form>
  <div className = "search">
    <TextField
      hintText="Search"
    />
    <IconButton>
     <ActionHome />
   </IconButton>
    </div>
    </form>
);

export default SearchComp;
