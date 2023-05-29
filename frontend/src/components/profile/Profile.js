import NavBar from "../NavBar";
import {MDBBtn, MDBCol, MDBInput, MDBRow} from 'mdb-react-ui-kit';
import React, {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";
import AuthContext from "../../context/AuthProvider";
import {useNavigate} from "react-router-dom";
import SplitsTables from "./SplitsTables";


const USERNAME_URL = '/users/username';
const BALANCE_URL = '/users/balance';
const DELETE_URL = '/auth/delete';
const POSITIVE_NUMBER_REGEX = /^(?:\d*[1-9]\d*)?$/;

const Profile = () => {

    const axios = useContext(AxiosContext);
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();

    const [splits, setSplits] = useState([]);

    const [userData, setUserData] = useState({
        username: '',
        balance: 0.0,
        id: 0
    });

    const[formValue, setFormValue] = useState({
        username: '',
        balance: ''
    });

    const[errMsg, setErrMsg] = useState('');

    useEffect(() => {
        axios.authAxios.get('/users/details')
            .then((res) => {
                setUserData({
                    username: res?.data?.username,
                    balance: res?.data?.balance,
                    id: res?.data?.id
                })
            });
        axios.authAxios.get('/splits/data')
            .then(res => {
                setSplits(res?.data)
            });
    }, [axios.authAxios]);


    const onChange = (e) => {
        if (e.target.name === 'balance' && !POSITIVE_NUMBER_REGEX.test(e.target.value)) {
            e.preventDefault();
        } else {
            setFormValue({...formValue, [e.target.name]: e.target.value});
        }
    }

    const handleUsernameChange = async (e) => {
        e.preventDefault();
        try {
            await axios.authAxios.patch(USERNAME_URL,
                JSON.stringify({
                    value: formValue.username
                }), {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }).then(response => {
                    setUserData({
                        username: response?.data?.username,
                        balance: response?.data?.balance
                    });
                    setFormValue({...formValue, username: ''});
                    setErrMsg('');
            });
        } catch (err) {
            if (err.response.status === 409) {
                setErrMsg(err.response.data);
            } else {
                setErrMsg('Error');
            }
        }
    }


    const handleBalanceChange = async (e) => {
        e.preventDefault();
        try {
            await axios.authAxios.patch(BALANCE_URL,
                JSON.stringify({
                    value: formValue.balance
                }), {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }).then(response => {
                setUserData({
                    username: response?.data?.username,
                    balance: response?.data?.balance
                });
                setFormValue({...formValue, balance: ''});
                setErrMsg('');
            });
        } catch (err) {
            if (err.response.status === 409) {
                setErrMsg(err.response.data);
            } else {
                setErrMsg('Error');
            }
        }
    }

    const handleDelete = async (e) => {
        e.preventDefault();
        try {
            await axios.authAxios.delete(DELETE_URL,
                {withCredentials: true});
            authContext.logout();
            navigate('/login');
        } catch (err) {
            setErrMsg('Failed');
        }
    }

    return (
        <>
            <NavBar />
            <MDBRow className='m-2'>
                <SplitsTables splits={splits} owner={userData.id}/>
                <MDBCol md='6'>
                    <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                        <MDBRow className='m-3 fs-3 fw-bold'>
                            <MDBCol md='2'>
                                Hi {userData.username}
                            </MDBCol>
                            <MDBCol md='6'>
                                {errMsg}
                            </MDBCol>
                        </MDBRow>
                        <MDBRow className='m-3'>
                            <MDBCol md='8'>
                                <MDBInput label='Update username' id='form1' type='text' name='username'
                                          value={formValue.username} onChange={onChange}/>
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBBtn className='me-1 w-75' color='success' disabled={!formValue.username.trim().length > 0}
                                        onClick={handleUsernameChange}>
                                    Save
                                </MDBBtn>
                            </MDBCol>
                        </MDBRow>
                        <MDBRow className='m-3'>
                            <MDBCol md='4' className='fs-3 fw-bolder'>
                                Balance: {userData.balance}
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBInput label='Add balance' id='form1' type='text'
                                          name='balance' value={formValue.balance} onChange={onChange} />
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBBtn className='me-1 w-75' color='success' disabled={!formValue.balance.length > 0}
                                        onClick={handleBalanceChange}>
                                    Save
                                </MDBBtn>
                            </MDBCol>
                        </MDBRow>
                        <MDBRow className='mt-4 mb-4 ms-4'>
                            <MDBBtn className= 'w-25 text-center' color='danger' onClick={handleDelete}>
                                Delete account
                            </MDBBtn>
                        </MDBRow>
                    </div>

                </MDBCol>
            </MDBRow>
        </>
    );
}

export default Profile;