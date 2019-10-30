import React, { Component } from 'react';
import Carlist from './components/Carlist';
import logo from './logo.svg';
import './App.css';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Login from './components/Login.js';

class App extends Component {
  render() {
    return (
      <div className="App">
        <AppBar position="static" color="default">
          <Toolbar>Car List</Toolbar>
        </AppBar>
        <Login />
      </div>
    );
  }
}

export default App;
