import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from "react-router-dom";
import {
    MDBContainer,
    MDBTabsContent,
    MDBBtn,
    MDBInput,
    MDBCheckbox
}
    from 'mdb-react-ui-kit';
import axios from "../api/axios";
import useAuth from "../hooks/useAuth";


const LOGIN_URL = '/login';

function Login() {

    const { setAuth } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";


    const[password, setPassword] = useState('');
    const[email, setEmail] = useState('');

    const[errMsg, setErrMsg] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(LOGIN_URL,
                JSON.stringify({
                    email,
                    password
                }),
                {
                    headers: {'Content-Type' : 'application/json'},
                    withCredentials: true
                });
            setAuth({
                    accessToken: response.data.token,
                    refreshToken: response.data.refreshToken,
                    authenticated: true,
                    roles: response.data.roles
            });
            navigate(from, { replace: true });
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No server response');
            } else if (err.response.status === 409) {
                setErrMsg(err.response?.data)
            } else {
                setErrMsg('Login failed');
            }
        }
    }


    return (
        <MDBContainer className="p-3 my-5 d-flex flex-column w-50">

            <MDBTabsContent>

                <div className="text-center mb-3">
                    <p>Sign in:</p>
                </div>
                <p className={ !errMsg ? "invisible" : ""}>
                    {errMsg}
                </p>
                <MDBInput wrapperClass='mb-4'
                          label='Email address'
                          id='form1'
                          type='email'
                          onChange={(e) => setEmail(e.target.value)}
                />

                <MDBInput wrapperClass='mb-4'
                          label='Password'
                          id='form2'
                          type='password'
                          onChange={(e) => setPassword(e.target.value)}
                />

                <div className="d-flex justify-content-between mx-4 mb-4">
                    <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label='Remember me' />
                    <a href="!#">Forgot password?</a>
                </div>

                <MDBBtn className="mb-4 w-100"
                        onClick={handleLogin}
                >Sign in</MDBBtn>
                <p className="text-center">Not a member? <Link to='/register'>Register</Link></p>


            </MDBTabsContent>

        </MDBContainer>
    );
}

export default Login;
