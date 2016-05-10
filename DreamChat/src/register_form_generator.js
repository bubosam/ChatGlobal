import React from 'react';
import ReactDOM from 'react-dom';
import Form, {AppBar, Text, SubmitButton} from './index';

ReactDOM.render((

<Form onSubmit={data => console.log(data)}>

<AppBar 
title="Dream Chat"/>

    <Text
      name="nickname"
      validate={['required']}
      placeholder="Type your nickname here"
      label="Your nickname"/>

    <Text
        name="password"
        type="password"
        validate={['required']}
        placeholder="Type your password here"
        label="Your password"/>

    <Text
      name="email"
      validate={['required', 'email']}
      placeholder="Type your email here"
      label="E-mail"/>   
    
    <SubmitButton/>
  </Form>
), document.getElementById('container'));
