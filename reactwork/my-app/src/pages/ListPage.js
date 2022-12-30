import React, { useState } from 'react';
import styled from 'styled-components';

const ListPage = () => {
  const [posts, setPosts] = useState([
    { id: 1, title: '제목1', content: '내용1' },
    { id: 2, title: '제목2', content: '내용2' },
    { id: 3, title: '제목3', content: '내용3' },
    { id: 4, title: '제목4', content: '내용4' },
    { id: 5, title: '제목5', content: '내용5' },
  ]);

  let num = posts.length + 1;
  const [post, setPost] = useState({
    id: num,
    title: '',
    content: '',
  });

  const StyledItemBoxDiv = styled.div`
    display: flex;
    justify-content: space-between;
    border: 1px solid black;
    padding: 10px;
    height: 100px;
    margin: 20px;
  `;

  const handleWrite = (e) => {
    e.preventDefault();
    setPost({ ...post, id: ++num });
    setPosts([...posts, post]);
  };

  const handleForm = (e) => {
    console.log(e.target.name);
    console.log(e.target.value);
    // computed propery names (key값 동적 할당)
    setPost({ ...post, [e.target.name]: e.target.value });
  };
  return (
    <div>
      <h1>글목록 페이지</h1>
      <form>
        <input
          type="text"
          placeholder="제목을 입력하세요..."
          value={post.title}
          onChange={handleForm}
          name="title"
        />
        <input
          type="text"
          placeholder="내용을 입력하세요..."
          value={post.content}
          onChange={handleForm}
          name="content"
        />
        <button type="submit" onClick={handleWrite}>
          글 쓰기
        </button>
      </form>
      <hr />
      {posts.map((post) => (
        <StyledItemBoxDiv>
          <div>
            번호 : {post.id} / 제목 : {post.title} / 내용 : {post.content}
          </div>
          <button>삭제</button>
        </StyledItemBoxDiv>
      ))}
    </div>
  );
};

export default ListPage;
