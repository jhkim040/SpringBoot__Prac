import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Wrap } from './components/common/Wrap';
import AuthProvider from './components/context/AuthProvider';
import HttpHeadersContext from './components/context/HttpHeadersProviders';
import NicknameContext from './components/context/NicknameProvider';
import Login from './pages/Login';
import Main from './pages/Main';
import Signup from './pages/Signup';

function App() {
  return (
    <Wrap>
      <BrowserRouter>
        <AuthProvider>
          <HttpHeadersContext>
            <NicknameContext>
              <Routes>
                <Route path="/signup" exact={true} element={<Signup />} />
                <Route path="/" exact={true} element={<Login />} />
                <Route path="/user" exact={true} element={<Main />} />
              </Routes>
            </NicknameContext>
          </HttpHeadersContext>
        </AuthProvider>
      </BrowserRouter>
    </Wrap>
  );
}

export default App;
