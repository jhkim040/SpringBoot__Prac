import React, { useContext, useState } from 'react';
import { Button, Card, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../components/context/AuthProvider';
import { HttpHeadersContext } from '../components/context/HttpHeadersProviders';
import axios from 'axios';
import { NicknameContext } from '../components/context/NicknameProvider';

const Login = () => {
  const { auth, setAuth } = useContext(AuthContext);
  const { headers, setHeaders } = useContext(HttpHeadersContext);
  const { nickname, setNickname } = useContext(NicknameContext);

  const navigate = useNavigate();

  const [userInfo, setUserInfo] = useState({
    email: '',
    password: '',
  });
  const onChangeHandler = (e) => {
    setUserInfo({
      ...userInfo,
      [e.target.name]: e.target.value,
    });
  };

  const onLoginHandler = async (e) => {
    e.preventDefault();

    await axios
      .post('http://localhost:8080/auth/login', userInfo)
      .then((res) => {
        console.log('로그인 시작');
        console.log(res.data);

        alert(`${res.data.nickname}님, 로그인 완료!`);

        localStorage.setItem('accessToken', res.data.accessToken);
        localStorage.setItem('email', res.data.email);
        localStorage.setItem('nickname', res.data.nickname);

        setAuth(res.data.email);
        setHeaders({ Authorization: `Bearer ${res.data.accessToken}` });
        setNickname(res.data.nickname);

        navigate('/user');
      })
      .catch((err) => {
        alert('로그인 에러');
        console.log(err);
      });
  };

  const ButtonStyle = {
    margin: '10px',
  };

  return (
    <>
      <h1>Login Page</h1>
      <Form onSubmit={onLoginHandler}>
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
            navigate('/signup');
          }}
        >
          Signup
        </Button>
      </Form>
    </>
  );
};

export default Login;
