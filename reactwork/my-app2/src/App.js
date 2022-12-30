import logo from "./logo.svg";
import "./App.css";
import React, { useState } from "react";
import Top from "./components/Top";
import Bottom from "./components/Bottom";

function App() {
  return (
    <div className="container">
      <h1>최상단화면</h1>
      <Top />
      <Bottom />
    </div>
  );
}

export default App;
