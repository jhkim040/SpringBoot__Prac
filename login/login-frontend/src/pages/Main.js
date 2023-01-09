import React, { useContext } from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { Wrap } from '../components/common/Wrap';
import { AuthContext } from '../components/context/AuthProvider';
import { HttpHeadersContext } from '../components/context/HttpHeadersProviders';
import { NicknameContext } from '../components/context/NicknameProvider';

const Main = () => {
  const navigate = useNavigate();
  const { auth, setAuth } = useContext(AuthContext);
  const { headers, setHeaders } = useContext(HttpHeadersContext);
  const { nickname, setNickname } = useContext(NicknameContext);

  const onLogoutHandler = () => {
    localStorage.clear();
    alert('로그아웃 완료');

    setAuth(null);
    setHeaders(null);
    setNickname(null);

    navigate('/');
  };

  return (
    <>
      <h1>Main Page</h1>
      <h2>Hello, {nickname}</h2>
      <Button variant="primary" type="button" onClick={onLogoutHandler}>
        Logout
      </Button>
    </>
  );
};

export default Main;
