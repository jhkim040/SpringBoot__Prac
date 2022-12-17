import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Write = () => {
  const [board, setBoard] = useState({
    title: "",
    content: "",
  });
  const { title, content } = board;

  const onInputChange = (e) => {
    setBoard({ ...board, [e.target.name]: e.target.value });
  };

  const navigate = useNavigate();
  const onSubmit = async (e) => {
    e.preventDefault();
    console.log(board);
    await axios.post("http://localhost:8080/board/insert", board);
    navigate("/");
  };

  return (
    <div>
      <form onSubmit={(e) => onSubmit(e)}>
        <div>
          <input
            type="text"
            name="title"
            onChange={(e) => {
              onInputChange(e);
            }}
            value={title}
          />
        </div>
        <div>
          <textarea
            name="content"
            onChange={(e) => {
              onInputChange(e);
            }}
            value={content}
          ></textarea>
        </div>
        <button type="submit">글 작성</button>
      </form>
    </div>
  );
};

export default Write;
