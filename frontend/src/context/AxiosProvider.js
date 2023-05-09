import React, { createContext, useContext } from "react";
import axios from "axios";
import AuthContext from "./AuthProvider";
import createAuthRefreshInterceptor from "axios-auth-refresh";

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

    // authAxios.interceptors.response.use(
    //     (response) => response,
    //     async (error) => {
    //         const config = error?.config;
    //
    //         if (error?.response?.status === 403 && !config?.sent) {
    //             config.sent = true;
    //
    //             const data = {
    //                 refreshToken: authContext.auth.refreshToken,
    //             };
    //
    //             const options = {
    //                 method: 'POST',
    //                 data,
    //                 url: 'http://localhost:8080/api/token/refresh'
    //             };
    //
    //
    //             const response = await axios(options);
    //             console.log(response.data)
    //
    //             config.headers.Authorization = `Bearer ${response.data.token}`;
    //             authContext.setAuth({
    //                 ...authContext.auth,
    //                 accessToken: response.data.token,
    //             });
    //
    //             return axios(config);
    //         }
    //         authContext.setAuth({
    //             accessToken: null,
    //             refreshToken: null,
    //             authenticated: false,
    //             roles: []
    //         });
    //         return Promise.reject(error);
    //     }
    // )

    const refreshAuthLogic = failedRequest => {
        const data = {
            refreshToken: authContext.auth.refreshToken,
        };

        const options = {
            method: 'POST',
            data,
            url: 'http://localhost:8080/api/token/refresh'
        };
        return axios(options)
            .then(async tokenRefreshResponse => {
                failedRequest.response.config.headers.Authorization =
                    'Bearer ' + tokenRefreshResponse.data.token;

                authContext.setAuth({
                    ...authContext.auth,
                    accessToken: tokenRefreshResponse.data.token,
                });

                return Promise.resolve();
            })
            .catch(() => {
                authContext.setAuth({
                    accessToken: null,
                    refreshToken: null,
                    authenticated: false,
                    roles: []
                });
            });
    };

    createAuthRefreshInterceptor(authAxios, refreshAuthLogic, {});

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