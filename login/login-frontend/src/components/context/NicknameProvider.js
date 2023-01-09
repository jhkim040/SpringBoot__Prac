import React from 'react';
import { createContext, useState } from 'react';

export const NicknameContext = createContext();

const NicknameProvider = ({ children }) => {
  const [nickname, setNickname] = useState(localStorage.getItem('nickname'));
  const value = { nickname, setNickname };

  return (
    <NicknameContext.Provider value={value}>
      {children}
    </NicknameContext.Provider>
  );
};

export default NicknameProvider;
