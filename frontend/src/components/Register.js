import React, {useEffect, useState} from 'react';
import { Link, useNavigate, useLocation } from "react-router-dom";
import {
    MDBContainer,
    MDBTabs,
    MDBTabsItem,
    MDBTabsLink,
    MDBTabsContent,
    MDBTabsPane,
    MDBBtn,
    MDBIcon,
    MDBInput,
    MDBCheckbox
}
    from 'mdb-react-ui-kit';
import axios from "../api/axios";
import useAuth from "../hooks/useAuth";


const REGISTER_URL = '/register';

const USER_REGEX = /^[A-z][A-z0-9-_ ]{3,23}$/;
const PASS_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

function Register() {

    const { setAuth } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const[username, setUsername] = useState('');
    const[validUsername, setValidUsername] = useState(false);

    const[password, setPassword] = useState('');
    const[matchPassword, setMatchPassword] = useState('');
    const[validPassword, setValidPassword] = useState(false);
    const[validMatch, setValidMatch] = useState(false);

    const[email, setEmail] = useState('');
    const[validEmail, setValidEmail] = useState(false);

    const[errMsg, setErrMsg] = useState('');

    useEffect(() => {
        const result = USER_REGEX.test(username);
        setValidUsername(result);
    }, [username]);

    useEffect(() => {
        const result = PASS_REGEX.test(password);
        setValidPassword(result);
        const match = password === matchPassword;
        setValidMatch(match);
    }, [password, matchPassword]);

    useEffect(() => {
        const result = EMAIL_REGEX.test(email);
        console.log(email);
        console.log(result);
        setValidEmail(result);
    }, [email]);


    const handleRegister = async (e) => {
        e.preventDefault();
        console.log(username);
        console.log(email);
        console.log(password);
        console.log(matchPassword);
    }

    return (
        <MDBContainer className="p-3 my-5 d-flex flex-column w-50">

            <MDBTabsContent>

                <div className="text-center mb-3">
                    <p>Sign up:</p>
                </div>

                <MDBInput wrapperClass='mb-4'
                          label='Username'
                          id='form1'
                          type='text'
                          onChange={(e) => setUsername(e.target.value)}
                />
                <MDBInput wrapperClass='mb-4'
                          label='Email'
                          id='form1'
                          type='email'
                          onChange={(e) => setEmail(e.target.value)}
                />
                <MDBInput wrapperClass='mb-4'
                          label='Password'
                          id='form1'
                          type='password'
                          onChange={(e) => setPassword(e.target.value)}
                />
                <MDBInput wrapperClass='mb-4'
                          label='Confirm password'
                          id='form1'
                          type='password'
                          onChange={(e) => setMatchPassword(e.target.value)}
                />

                <div className='d-flex justify-content-center mb-4'>
                    <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I have read and agree to the terms' />
                </div>

                <MDBBtn className="mb-4 w-100" onClick={handleRegister}>Sign up</MDBBtn>

            </MDBTabsContent>

        </MDBContainer>
    );
}

export default Register;
