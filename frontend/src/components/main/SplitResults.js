import NavBar from "../NavBar";
import {MDBCol, MDBRow} from "mdb-react-ui-kit";
import React, {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";
import ContributorsList from "./ContributorsList";

const RESULT_URL = "/splits/result/";

const SplitResults = ({contributors, splitId}) => {

    const axios = useContext(AxiosContext);

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

    useEffect(() => {
        axios.authAxios.get(RESULT_URL.concat(splitId))
            .then(response => {
                setResults(response?.data?.results);
            });
    }, [axios, splitId])

    return (
        <>
            <NavBar />
            <MDBRow className='m-2'>
                <MDBCol md='6'>
                    <ContributorsList contributors={contributors}/>
                </MDBCol>
                <MDBCol md='6'>
                    <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                        <MDBRow className='m-3 fs-3 fw-bold'>
                            Results
                        </MDBRow>
                        {results.map(r => (
                            <MDBRow className='mt-4 mb-4 ms-4'>
                                <MDBCol md='4' className='fs-3 fw-bolder'> FROM: {r.from.username}</MDBCol>
                                <MDBCol md='4' className='fs-3 fw-bolder'> TO: {r.to.username}</MDBCol>
                                <MDBCol md='4' className='fs-3 fw-bolder'> AMOUNT: {r.amount}</MDBCol>
                            </MDBRow>
                        ))}
                    </div>
                </MDBCol>
            </MDBRow>
        </>
    );
}

export default SplitResults;