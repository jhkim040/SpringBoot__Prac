import React from 'react';
import { createContext, useState } from 'react';

export const HttpHeadersContext = createContext();

const HttpHeadersProviders = ({ children }) => {
  const [headers, setHeaders] = useState({
    'content-type': 'application/json',
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  });

  const value = { headers, setHeaders };
  return (
    <HttpHeadersContext.Provider value={value}>
      {children}
    </HttpHeadersContext.Provider>
  );
};

export default HttpHeadersProviders;
