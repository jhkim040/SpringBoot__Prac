import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

const Main = () => {
  const [list, SetList] = useState([]);

  useEffect(() => {
    axios.get("/board/list").then((res) => {
      SetList(res.data);
      console.log(res);
    });
  }, []);

  return (
    <div>
      <div>
        <Link to="/write">글 쓰기</Link>
      </div>
      <h1>Board List</h1>
      {list.map((board) => (
        <div>
          <span>id : {board.id} </span>
          <span>title : {board.title}</span>
          <span>content : {board.content}</span>
        </div>
      ))}
    </div>
  );
};
export default Main;
