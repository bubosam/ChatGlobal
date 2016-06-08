import React from 'react';
import DrawerComp from '../components/DrawerComp.jsx';
import Welcome from '../components/Welcome.jsx';

export default class DashBoard extends React.Component {
    render() {
        return (
            <div>
              <DrawerComp />
              <Welcome />
            </div>
        );
    }
}
