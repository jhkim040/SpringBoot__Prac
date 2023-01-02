import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const SaveForm = (props) => {
  const navigate = useNavigate();

  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const onChangeHandler = (e) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value,
    });
  };

  const onSubmitHandler = (e) => {
    e.preventDefault();
    fetch('http://localhost:8080/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset-utf-8',
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        // console.log(1, res);
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res != null) {
          navigate('/');
        } else {
          alert('책 등록에 실패하였습니다.');
        }
      })
      .catch((err) => {
        console.err(err);
      });
  };

  return (
    <div>
      <Form onSubmit={onSubmitHandler}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Title"
            name="title"
            onChange={onChangeHandler}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Author</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Author"
            name="author"
            onChange={onChangeHandler}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Submit
        </Button>
      </Form>
    </div>
  );
};

export default SaveForm;
