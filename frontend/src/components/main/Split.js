import React, {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";
import AuthContext from "../../context/AuthProvider";
import {useLocation, useNavigate} from "react-router-dom";
import {MDBBtn, MDBCol, MDBInput, MDBRow} from "mdb-react-ui-kit";
import NavBar from "../NavBar";
import SplitResults from "./SplitResults";
import ContributorsList from "./ContributorsList";

const NUMBER_REGEX = /\d+$/;
const POSITIVE_NUMBER_REGEX = /^(?:\d+\.?\d*)?$/;
const SPLIT_DETAILS_URL = '/splits/';
const SPLIT_UPDATE_URL = '/splits/update';

const Split = () => {

    const axios = useContext(AxiosContext);
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();
    const location = useLocation();

    const owner = location.state?.user || '';

    const[id, setId] = useState(0);
    const[contributions, setContributions] = useState([]);
    const[finished, setFinished] = useState(false);

    const[userBalance, setUserBalance] = useState(0);

    useEffect(() => {
        const url = window.location.href.toString();
        const matches = url.match(NUMBER_REGEX);
        if (matches) {
            axios.authAxios.get(SPLIT_DETAILS_URL.concat(matches[0]))
                .then((res) => {
                    console.log(res);
                    setId(res?.data?.splitId);
                    const contributionsData = Object.entries(res?.data?.contributions);
                    setContributions(contributionsData);
                    setUserBalance(contributionsData.find(e => e[0] === owner && e[1] !== -1)[1]);
                    setFinished(res?.data?.finished);
                });
        }
    }, [axios, owner]);


    const handleBalanceChange = (e) => {
        !POSITIVE_NUMBER_REGEX.test(e.target.value) ?
            e.preventDefault()
        :
            setUserBalance(e.target.value);
    };

    const handleBalanceUpdate = async (e) => {
        e.preventDefault();
        try {
            await axios.authAxios.post(SPLIT_UPDATE_URL,
                JSON.stringify({
                    email: owner,
                    contribution: userBalance,
                    splitId: id
                }), {
                    headers: {'Content-Type' : 'application/json'},
                    withCredentials: true
                }).then(() => {
                    alert('Balance updated!');
            });
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <>
            {!finished ?
                <>
                    <NavBar />
                    <MDBRow className='m-2'>
                        <MDBCol md='6'>
                            <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                                <MDBRow className='m-3 fs-3 fw-bold'>
                                    Update your balance
                                </MDBRow>
                                <MDBRow className='mt-4 mb-4 ms-4'>
                                    <MDBCol md='3' className='fs-3 fw-bolder'>
                                        Balance: {userBalance}
                                    </MDBCol>
                                    <MDBCol md='3'>
                                        <MDBInput label='Change balance' id='form1' type='text'
                                                  name='balance'
                                                  value={userBalance}
                                                  onChange={handleBalanceChange} />
                                    </MDBCol>
                                    <MDBCol md='3'>
                                        <MDBBtn className= 'w-100 text-center' color='success'
                                                onClick={handleBalanceUpdate}
                                                disabled={userBalance.toString().trim().length < 1}
                                        >
                                            Save
                                        </MDBBtn>
                                    </MDBCol>
                                </MDBRow>
                            </div>
                            <ContributorsList contributors={contributions.filter(e => e[0] !== owner)} />
                        </MDBCol>
                        <MDBCol md='6'>
                            <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                                <MDBRow className='m-3 fs-3 fw-bold'>
                                    Results
                                </MDBRow>
                                <MDBRow className='mt-4 mb-4 ms-4'>
                                    Results will be available when everyone complete their balance
                                </MDBRow>
                            </div>
                        </MDBCol>
                    </MDBRow>
                </>
        :
            <SplitResults contributors={contributions} splitId={id}/>
        }
        </>
    );
}

export default Split;