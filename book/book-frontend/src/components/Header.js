import React from 'react';
import { Button, Nav, Navbar, Form, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <>
      <Navbar bg="dark" variant="dark">
        <Container>
          <Nav className="me-auto">
            <Link to="/" className="nav-link">
              홈
            </Link>
            <Link to="/joinForm" className="nav-link">
              회원가입
            </Link>
            <Link to="/loginForm" className="nav-link">
              로그인
            </Link>
            <Link to="/saveForm" className="nav-link">
              글쓰기
            </Link>
          </Nav>

          <Form className="d-flex">
            <Form.Control
              type="text"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success">Search</Button>
          </Form>
        </Container>
      </Navbar>
      <br />
    </>
  );
};

export default Header;
