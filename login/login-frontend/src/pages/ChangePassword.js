import axios from 'axios';
import React, { useContext, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../components/context/AuthProvider';

const ChangePassword = () => {
  const navigate = useNavigate();
  const { auth, setAuth } = useContext(AuthContext);

  const [userInfo, setUserInfo] = useState({
    email: auth,
    exPassword: '',
    newPassword: '',
  });

  const onChangeHandler = (e) => {
    setUserInfo({
      ...userInfo,
      [e.target.name]: e.target.value,
    });
  };

  const onSubmitHandler = async (e) => {
    e.preventDefault();

    await axios
      .put('http://localhost:8080/member/password', userInfo)
      .then((res) => {
        console.log('비밀번호 변경');
        console.log(res.data);

        alert(`비밀번호 변경 완료! 다시 로그인해주세요!`);

        navigate('/');
      })
      .catch((err) => {
        alert('비밀번호 변경 에러');
        console.log(err);
      });
  };

  const ButtonStyle = {
    margin: '10px',
  };
  return (
    <>
      <h1>Change Your Password!</h1>
      <Form onSubmit={onSubmitHandler}>
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Your Password</Form.Label>
          <Form.Control
            name="exPassword"
            type="password"
            placeholder="Ex Password"
            onChange={onChangeHandler}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>New Password</Form.Label>
          <Form.Control
            name="newPassword"
            type="password"
            placeholder="New Password"
            onChange={onChangeHandler}
          />
        </Form.Group>

        <Button style={ButtonStyle} variant="primary" type="submit">
          Update
        </Button>
        <Button
          style={ButtonStyle}
          variant="secondary"
          type="button"
          onClick={() => {
            navigate('/user');
          }}
        >
          Main Page
        </Button>
      </Form>
    </>
  );
};

export default ChangePassword;
