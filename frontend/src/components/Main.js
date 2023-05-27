import NavBar from "./NavBar";
import {useNavigate} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../context/AxiosProvider";
import AuthContext from "../context/AuthProvider";
import {MDBIcon, MDBListGroup, MDBListGroupItem} from "mdb-react-ui-kit";

const numberPattern = /\d+$/;
const SPLIT_DETAILS_URL = '/splits/';

const Main = () => {

    const axios = useContext(AxiosContext);
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();

    const[id, setId] = useState(0);
    const[contributors, setContributors] = useState([]);
    const[finished, setFinished] = useState(false);

    useEffect(() => {
        const url = window.location.href.toString();
        const matches = url.match(numberPattern);
        if (matches) {
            const splitId = matches[0];
            axios.authAxios.get(SPLIT_DETAILS_URL.concat(splitId))
                .then((res) => {
                    setId(res?.data?.splitId);
                    setContributors(Object.entries(res?.data?.contributions));
                    setFinished(res?.data?.finished);
                });
        } else {
            console.log('No number found at the end of the URL.');
        }
    }, [axios]);


    return (
        <>
            <NavBar />
            {id !== 0 ?
                <MDBListGroup className='m-1 ' light>
                    <div className='d-flex align-items-center m-2'>
                        {contributors.map(item => (
                            <MDBListGroupItem className='d-flex justify-content-between align-items-center rounded-5 w-75'>
                                <MDBIcon icon="dollar" size="2x" className='m-2' />
                                <p className='fs-5 fw-bolder m-auto'>Email: {item[0]}</p>
                                <p className='fs-5 fw-bolder m-auto'>Money: {item[1]}</p>
                            </MDBListGroupItem>
                        ))}
                    </div>
                </MDBListGroup>
                :
                <p>empty</p>
            }


        </>
    );
}

export default Main;