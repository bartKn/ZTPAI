import { createContext, useState } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const[auth, setAuth] = useState({
        accessToken: null,
        refreshToken: null,
        authenticated: false,
        roles: []
    });

    const getAccessToken = () => {
        return auth.accessToken;
    }

    const logout = () => {
        setAuth({
            accessToken: null,
            refreshToken: null,
            authenticated: false,
            roles: []
        });
    }

    return (
        <AuthContext.Provider value={{auth, setAuth, getAccessToken, logout}}>
            { children }
        </AuthContext.Provider>
    )
}

export default AuthContext;