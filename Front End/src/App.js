import React, { Component } from 'react';
import Carlist from './components/Carlist';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Car List</h1>
        </header>
        <Carlist />
      </div>
    );
  }
}

export default App;
