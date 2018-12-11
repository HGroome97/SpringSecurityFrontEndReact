import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import Login from './Login.js'
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import Homepage from './Homepage.js';

function PageRouting() {
  return (
    <Router>
      <div>
      <Route exact path="/index.html" component={Loginpage} />
      <Route path="/homepage" component={Homepagepage} />
    </div>
    </Router>
  );
}

class Loginpage extends Component {
  render() {
    return (
      <div className="App">
        <Login />
      </div>
    );
  }
}


class Homepagepage extends Component {
  render() {
    return (
      <div className="App">
      <Homepage />
      </div>
    );
  }
}


export default PageRouting;
