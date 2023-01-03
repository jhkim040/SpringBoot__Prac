import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

const Detail = (props) => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [book, setBook] = useState({
    id: '',
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

  const deleteHandler = () => {
    fetch(`http://localhost:8080/book/${id}`, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        } else {
          alert('삭제 실패');
        }
      })
      .catch((err) => console.err(err));
  };

  const updateHandler = () => {
    navigate(`/updateForm/${id}`);
  };
  return (
    <div>
      <h1>책 상세보기</h1>
      <Button variant="warning" onClick={updateHandler}>
        수정
      </Button>
      {'  '}
      <Button variant="danger" onClick={deleteHandler}>
        삭제
      </Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;
