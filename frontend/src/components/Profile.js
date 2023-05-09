import NavBar from "./NavBar";
import {MDBBtn, MDBCol, MDBInput, MDBRow} from 'mdb-react-ui-kit';
import {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../context/AxiosProvider";

const Profile = () => {

    const axios = useContext(AxiosContext);

    const [userData, setUserData] = useState({
        username: '',
        balance: 0.0
    });

    useEffect(() => {
        axios.authAxios.get('/user/details')
            .then((res) => {
                setUserData({
                    username: res.data.username,
                    balance: res.data?.balance
                })
            });
    }, [axios.authAxios]);

    return (
        <>
            <NavBar />
            <MDBRow>
                <MDBCol md='6'>

                </MDBCol>
                <MDBCol md='6'>
                    <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                        <MDBRow className='m-3'>
                            Hi {userData.username}
                        </MDBRow>
                        <MDBRow className='m-3'>
                            <MDBCol md='8'>
                                <MDBInput label='Update username' id='form1' type='text' />
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBBtn className='me-1' disabled={true}>
                                    Save
                                </MDBBtn>
                            </MDBCol>
                        </MDBRow>
                        <MDBRow className='m-3'>
                            <MDBCol md='8'>
                                <MDBInput label='Update email' id='form1' type='text'/>
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBBtn className='me-1' color='success'>
                                    Save
                                </MDBBtn>
                            </MDBCol>
                        </MDBRow>
                        <MDBRow className='m-3'>
                            <MDBCol md='4'>
                                Balance: {userData.balance}
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBInput label='Add balance' id='form1' type='number' step='0.1' />
                            </MDBCol>
                            <MDBCol md='4'>
                                <MDBBtn className='me-1' color='success'>
                                    Save
                                </MDBBtn>
                            </MDBCol>
                        </MDBRow>
                        <MDBRow className='m-3'>
                            <MDBBtn className='me-1 w-25 text-center' color='danger'>
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