import React from "react";
import { useDispatch } from "react-redux";
import "../App.css";
import { increase, decrease } from "../store/store";

const Bottom = () => {
  const dispatcher = useDispatch();
  return (
    <div className="sub_container">
      <h1>Bottom</h1>
      <button onClick={() => dispatcher(increase("kim"))}>증가</button>
      <button onClick={() => dispatcher(decrease())}>감소</button>
      {/* dispatcher({type:"INCREMENT"}) */}
    </div>
  );
};

export default Bottom;
