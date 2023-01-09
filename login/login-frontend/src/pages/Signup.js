import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Signup = () => {
  const navigate = useNavigate();

  const [userInfo, setUserInfo] = useState({
    email: '',
    password: '',
    nickname: '',
  });

  const onSignupHandler = async (e) => {
    e.preventDefault();

    await axios
      .post('http://localhost:8080/auth/signup', userInfo)
      .then((res) => {
        console.log('회원가입 시작');
        console.log(res.data);

        alert(`${res.data.nickname}님, 회원가입 완료!`);

        navigate('/');
      })
      .catch((err) => {
        alert('회원가입 에러');
        console.log(err);
      });
  };

  const onChangeHandler = (e) => {
    setUserInfo({
      ...userInfo,
      [e.target.name]: e.target.value,
    });
  };
  const ButtonStyle = {
    margin: '10px',
  };

  return (
    <>
      <h1>Signup Page</h1>
      <Form onSubmit={onSignupHandler}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control
            name="email"
            type="email"
            placeholder="Enter email"
            onChange={onChangeHandler}
          />
          <Form.Text className="text-muted">
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            name="password"
            type="password"
            placeholder="Password"
            onChange={onChangeHandler}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Nickname</Form.Label>
          <Form.Control
            name="nickname"
            type="text"
            placeholder="Nickname"
            onChange={onChangeHandler}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Check me out" />
        </Form.Group>
        <Button style={ButtonStyle} variant="primary" type="submit">
          Submit
        </Button>
        <Button
          style={ButtonStyle}
          variant="secondary"
          type="button"
          onClick={() => {
            navigate('/');
          }}
        >
          Login
        </Button>
      </Form>
    </>
  );
};

export default Signup;
