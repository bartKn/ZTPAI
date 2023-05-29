import NavBar from "../NavBar";
import React, {useContext, useEffect, useState} from "react";
import {MDBBtn, MDBCol, MDBInput, MDBRow} from "mdb-react-ui-kit";
import {AxiosContext} from "../../context/AxiosProvider";

const NUMBER_PATTERN = /\d+$/;
const POSITIVE_NUMBER_REGEX = /^(?:\d+\.?\d*)?$/;
const CALC_URL = "/splits/calculate";

const Main = () => {

    const axios = useContext(AxiosContext);

    const[id, setId] = useState(0);
    const[results, setResults] = useState([{
        amount: 0,
        from: {
            email: '',
            id: 0,
            username: ''
        },
        to: {
            email: '',
            id: 0,
            username: ''
        }
    }]);
    const[participants, setParticipants] = useState([{
        id: 0,
        balance: 0,
        username: 'User_1'
    },{
        id: 1,
        balance: 0,
        username: 'User_2'
    }]);

    useEffect(() => {
        const url = window.location.href.toString();
        const matches = url.match(NUMBER_PATTERN);
        if (matches) {
            setId(parseInt(matches[0]));
        }
    }, []);

    const handleParticipantAdd = (e) => {
        e.preventDefault();
        const lastIndex = participants.length - 1;
        const nextId = participants[lastIndex].id + 1;
        let currentParticipants = participants.slice();
        currentParticipants.push({balance: 0, id: nextId, username: 'User_'.concat(nextId + 1)});
        setParticipants(currentParticipants);
    }

    const handleBalanceChange = (e, id) => {
        if (e.target.name === 'balance' && !POSITIVE_NUMBER_REGEX.test(e.target.value)) {
            e.preventDefault();
        } else {
            const objIndex = participants.findIndex(p => p.id === id);
            participants[objIndex].balance = e.target.value;
            let currentParticipants = participants.slice();
            setParticipants(currentParticipants);
        }
    }

    const handleResultsCalc = async (e) => {
        e.preventDefault();
        try {
            await axios.publicAxios.post(CALC_URL,
                JSON.stringify({
                    splitDataList: participants
                }), {
                    headers: {'Content-Type' : 'application/json'},
                    withCredentials: true
                }).then(response => {
                    setResults(response?.data?.results);
            })
        } catch (err) {
            console.log(err);
        }
    }


    return (
        <>
            <NavBar />
            <MDBRow className='m-2'>
                <MDBCol md='6'>
                    <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                        <MDBRow className='m-3 fs-3 fw-bold'>
                            test
                        </MDBRow>
                        {participants.map(p => (
                            <MDBRow className='mt-4 mb-4 ms-4'>
                                <MDBCol md='4' className='fs-3 fw-bolder'>
                                    Name: {p.username}
                                </MDBCol>
                                <MDBCol md='4' className='fs-3 fw-bolder'>
                                    Balance: {p.balance}
                                </MDBCol>
                                <MDBCol md='4'>
                                    <MDBInput label='Change balance' id='form1' type='text'
                                              name='balance'
                                              value={p.balance}
                                              onChange={(e) => handleBalanceChange(e, p.id)} />
                                </MDBCol>
                            </MDBRow>
                        ))}
                        <MDBRow className='mt-4 mb-4 ms-4'>
                            <MDBBtn className= 'w-25 text-center' color='success' onClick={handleParticipantAdd}>
                                Add participant
                            </MDBBtn>
                        </MDBRow>
                        <MDBRow className='mt-4 mb-4 ms-4'>
                            <MDBBtn className= 'w-25 text-center' color='success' onClick={handleResultsCalc}>
                                Calculate results
                            </MDBBtn>
                        </MDBRow>
                    </div>
                </MDBCol>
                <MDBCol md='6'>
                    <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                        <MDBRow className='m-3 fs-3 fw-bold'>
                            Results
                        </MDBRow>
                        {results.map(r => (
                            <MDBRow className='mt-4 mb-4 ms-4'>
                                <p>FROM: {r.from.username}</p>
                                <p>TO: {r.to.username}</p>
                                <p>AMOUNT: {r.amount}</p>
                            </MDBRow>
                        ))}
                    </div>
                </MDBCol>
            </MDBRow>
        </>
    );
}

export default Main;