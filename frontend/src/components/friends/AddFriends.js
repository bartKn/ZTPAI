import {MDBBtn, MDBCol, MDBIcon, MDBInput, MDBListGroup, MDBListGroupItem, MDBRow} from "mdb-react-ui-kit";
import React, {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";

const SEARCH_URL = '/users/all'
const ADD_URL = '/users/friends/add'

const AddFriends = ({updateFriends, fetchedFriends}) => {

    const axios = useContext(AxiosContext);

    const [results, setResults] = useState([]);
    const [searchValue, setSearchValue] = useState('');
    const [ids, setIds] = useState([]);

    useEffect(() => {
        setIds(fetchedFriends.map(entry => entry.id));
    }, [fetchedFriends]);

    const onChange = (e) => {
        if (e.key === 'Enter') {
            handleUserSearch(e);
        }
        setSearchValue(e.target.value);
    }

    const handleUserSearch = async (e) => {
        e.preventDefault();
        let urlParam = '';
        if (searchValue.trim().length > 0) {
            urlParam = '?username='.concat(searchValue.trim());
        }
        try {
            await axios.authAxios.get(SEARCH_URL.concat(urlParam),
                {withCredentials: true})
                .then(response => {
                    setResults(response?.data);
                });
        } catch (err) {
            console.log(err);
        }
    }

    const handleFriendAdd = async (e, id, username, email) => {
        e.preventDefault();
        try {
            await axios.authAxios.post(ADD_URL,
                JSON.stringify({
                    id: id
                }), {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }).then(() => {
                updateFriends({
                    id: id,
                    email: email,
                    username: username
                });
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
                        Find friends
                    </MDBCol>
                </MDBRow>
                <MDBRow className='m-3'>
                    <MDBCol md='8'>
                        <MDBInput label='Search by username' id='form1' type='text' name='username'
                                  value={searchValue}
                                  onChange={onChange}
                                  onKeyDown={event => {
                                      if (event.key === 'Enter') {
                                          onChange(event);
                                      }
                                  }
                        }/>
                    </MDBCol>
                    <MDBCol md='4'>
                        <MDBBtn className='me-1 w-75' color='success' onClick={handleUserSearch}>
                            Find!
                        </MDBBtn>
                    </MDBCol>
                </MDBRow>
                <MDBRow className='m-3 w-100'>
                    <MDBListGroup className='m-1 ' light>
                        {results.map(f => (
                            <div className='d-flex align-items-center m-2'>
                                <MDBListGroupItem className='d-flex justify-content-between align-items-start rounded-5 w-100'>
                                    <MDBIcon icon="user" size="2x" className='m-2' />
                                    <p className='fs-5 fw-bolder m-auto'>Mail: {f.email}</p>
                                    <p className='fs-5 fw-bolder m-auto'>Username: {f.username}</p>
                                    <MDBBtn className='me-1 w-10 mt-2' color='success'
                                            onClick={event => handleFriendAdd(event, f.id, f.username, f.email)}
                                            disabled={ids.includes(f.id)}>
                                        Add friend
                                    </MDBBtn>
                                </MDBListGroupItem>
                            </div>
                        ))}
                    </MDBListGroup>
                </MDBRow>
            </div>
        </MDBCol>
    );
}

export default AddFriends;