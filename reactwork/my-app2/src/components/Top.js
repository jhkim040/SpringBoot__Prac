import React from "react";
import { useSelector } from "react-redux";
import "../App.css";

const Top = () => {
  const { username, number } = useSelector((store) => store.reducer);
  return (
    <div className="sub_container">
      <h1>Top</h1>
      번호 : {number}
      이름 : {username}
    </div>
  );
};

export default Top;
