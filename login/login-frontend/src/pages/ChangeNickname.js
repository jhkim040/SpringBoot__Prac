import axios from 'axios';
import React, { useContext, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../components/context/AuthProvider';

const ChangeNickname = () => {
  const navigate = useNavigate();
  const { auth, setAuth } = useContext(AuthContext);

  const [userInfo, setUserInfo] = useState({
    email: auth,
    nickname: '',
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
      .put('http://localhost:8080/member/nickname', userInfo)
      .then((res) => {
        console.log('닉네임 변경');
        console.log(res.data);

        alert(`닉네임 변경 완료!`);

        navigate('/user');
      })
      .catch((err) => {
        alert('닉네임 변경 에러');
        console.log(err);
      });
  };

  const ButtonStyle = {
    margin: '10px',
  };
  return (
    <>
      <h1>Change Your Nickname!</h1>
      <Form onSubmit={onSubmitHandler}>
        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>New Nickname</Form.Label>
          <Form.Control
            name="nickname"
            type="text"
            placeholder="New Nickname"
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

export default ChangeNickname;
