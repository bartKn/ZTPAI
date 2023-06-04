import React, { createContext, useContext } from "react";
import axios from "axios";
import AuthContext from "./AuthProvider";

const AxiosContext = createContext();
const { Provider } = AxiosContext;

const AxiosProvider = ({ children }) => {
    const authContext = useContext(AuthContext);

    const authAxios = axios.create({
        baseURL: 'http://localhost:8080/api/'
    });

    const publicAxios = axios.create({
        baseURL: 'http://localhost:8080/api'
    });

    authAxios.interceptors.request.use(
        config => {
            if (!config.headers.Authorization) {
                config.headers.Authorization = `Bearer ${authContext.getAccessToken()}`;
            }
            return config;
        },
        error => {
            return Promise.reject(error);
        }
    );

    authAxios.interceptors.response.use(
        (response) => response,
        async (error) => {
            const config = error?.config;

            if (error?.response?.status === 403 && !config?.sent) {
                config.sent = true;

                const data = {
                    token: authContext.auth.refreshToken,
                };

                const options = {
                    method: 'POST',
                    data,
                    url: 'http://localhost:8080/api/token/refresh'
                };

                try {
                    const response = await axios(options);

                    config.headers.Authorization = `Bearer ${response?.data?.token}`;
                    authContext.setAuth({
                        ...authContext.auth,
                        accessToken: response.data.token,
                    });

                    return axios(config);
                } catch {
                    authContext.setAuth({
                        accessToken: null,
                        refreshToken: null,
                        authenticated: false,
                        roles: []
                    });
                    return Promise.reject(error);
                }
            }
            authContext.setAuth({
                accessToken: null,
                refreshToken: null,
                authenticated: false,
                roles: []
            });
            return Promise.reject(error);
        }
    )

    return (
        <Provider
            value={{
                authAxios,
                publicAxios,
            }}>
            {children}
        </Provider>
    );
}

export { AxiosContext, AxiosProvider }