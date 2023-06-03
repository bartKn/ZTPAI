import React, {useContext, useEffect, useState} from "react";
import {MDBBtn, MDBCheckbox, MDBCol, MDBIcon, MDBListGroup, MDBListGroupItem, MDBRow} from "mdb-react-ui-kit";
import {AxiosContext} from "../../context/AxiosProvider";
import {useNavigate} from "react-router-dom";

const SPLIT_URL = '/splits/new'

const FriendsList = ({friendsList}) => {

    const axios = useContext(AxiosContext);
    const navigate = useNavigate();

    const [active, setActive] = useState(false);
    const [friends, setFriends] = useState([]);
    const [splitParticipants, setSplitParticipants] = useState([]);

    useEffect(() => {
        setFriends(friendsList);
    }, [friendsList]);

    const handeParticipantAdd = (e, id) => {
        if (splitParticipants.includes(id)) {
            setSplitParticipants([]);
            setSplitParticipants(splitParticipants.filter(element => element !== id));
            if (splitParticipants.length - 1 === 0) {
                setActive(false);
            }
        } else {
            splitParticipants.push(id);
            setActive(true);
        }
    }


    const handleSplitCreation = async (e) => {
        e.preventDefault();
        try {
           await axios.authAxios.post(SPLIT_URL,
               JSON.stringify({
                   userIds: splitParticipants
               }), {
                   headers: {'Content-Type': 'application/json'},
                   withCredentials: true
               }).then(response => {
                   navigate('/profile');
           })
        } catch (err) {
            console.log(err);
        }
    }

    return (
        <MDBCol md='6'>
            <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                <MDBRow className='m-3'>
                    <MDBCol md='4' className='fs-3 fw-bold'>
                        Friends
                    </MDBCol>
                </MDBRow>
                <MDBRow className='m-3 w-100'>
                    <MDBListGroup className='m-1 ' light>
                        {friends.map(f => (
                            <div className='d-flex align-items-center m-2'>
                                <MDBListGroupItem className='d-flex justify-content-center align-items-start rounded-5 w-100'>
                                    <MDBIcon icon="user" size="2x" className='m-2' />
                                    <p className='fs-5 fw-bolder m-auto'>Mail: {f.email}</p>
                                    <p className='fs-5 fw-bolder m-auto'>Username: {f.username}</p>
                                    <div className='me-2 mt-3'>
                                        <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault'
                                                     label='Add to split'
                                                     onClick={(e) => handeParticipantAdd(e, f.id)} />
                                    </div>
                                </MDBListGroupItem>
                            </div>
                        ))}
                    </MDBListGroup>
                </MDBRow>
                <MDBRow className='mt-4 mb-4 ms-4'>
                    <MDBBtn className= 'w-50 text-center' disabled={!active} color='success'
                            onClick={handleSplitCreation}>
                        Create split with selected friends
                    </MDBBtn>
                </MDBRow>
            </div>
        </MDBCol>
    );
}

export default FriendsList;