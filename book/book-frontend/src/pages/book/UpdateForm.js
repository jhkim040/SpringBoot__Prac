import React, { useState, useEffect } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  useEffect(() => {
    fetch(`http://localhost:8080/book/${id}`)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      })
      .catch((err) => console.err(err));
  }, []);

  const onChangeHandler = (e) => {
    setBook({
      ...book,
      [e.target.name]: e.target.value,
    });
  };

  const onSubmitHandler = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8080/book/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset-utf-8',
      },
      body: JSON.stringify(book),
    })
      .then((res) => {
        // console.log(1, res);
        if (res.status === 200) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res != null) {
          navigate(`/book/${id}`);
        } else {
          alert('책 수정에 실패하였습니다.');
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
            value={book.title}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Author</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Author"
            name="author"
            onChange={onChangeHandler}
            value={book.author}
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Submit
        </Button>
      </Form>
    </div>
  );
};

export default UpdateForm;
