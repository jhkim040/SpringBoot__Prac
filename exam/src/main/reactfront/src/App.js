// import logo from './logo.svg';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import Login from "./pages/login";
import Result from "./pages/result";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="userInfo" element={<Result />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
