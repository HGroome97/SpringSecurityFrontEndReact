import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import Form from './Form'

const inputs = [{
  name: "username",
  placeholder: "username",
  type: "text"
},{
  name: "password",
  placeholder: "password",
  type: "password"
},{
  type: "submit",
  value: "Log in",
  className: "btn"
}]

const props = {name: 'loginForm', method: 'POST', action: '/perform_login', inputs: inputs}

const params = new URLSearchParams(window.location.search)

export default class Login extends Component {
  render() {
    return (
      <div className="App">
        <Form {...props} error={params.get('error')} />
      </div>
    );
  }
}
