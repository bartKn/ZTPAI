import {MDBBtn, MDBCol, MDBIcon, MDBListGroup, MDBListGroupItem, MDBRow} from "mdb-react-ui-kit";
import React, {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";
import {useNavigate} from "react-router-dom";

const SplitsTables = ({splits, owner}) => {

    const axios = useContext(AxiosContext);
    const navigate = useNavigate();

    const [splitsList, setSplitsList] = useState([]);

    useEffect(() => {
        setSplitsList(splits);
    }, [splits]);

    const handleDelete = async(e, splitId) => {
        e.preventDefault();
        axios.authAxios.delete('splits/delete/'.concat(splitId),
            {withCredentials: true})
            .then(setSplitsList(splitsList.filter(item => item.splitId !== splitId)));
    }

    const navigateToSplit = (e, splitId) => {
        e.preventDefault();
        navigate('/split/'.concat(splitId), {state: {user: owner}});
    }

    return (
        <MDBCol md='6'>
            <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                <MDBRow className='m-3'>
                    <MDBCol md='4' className='fs-3 fw-bold'>
                        Active splits
                    </MDBCol>
                </MDBRow>
                <MDBRow className='m-3 w-100'>
                    <MDBListGroup className='m-1 ' light>
                        {splitsList.filter(split => split.finished === false).map(item => (
                            <div className='d-flex align-items-center m-2'>
                                <MDBListGroupItem className='d-flex justify-content-between align-items-center rounded-5 w-75'>
                                    <MDBIcon icon="dollar" size="2x" className='m-2' />
                                    <p className='fs-5 fw-bolder m-auto'>Split ID: {item.splitId}</p>
                                    <MDBBtn className='m-1' onClick={(e) => navigateToSplit(e, item.splitId)}>
                                        View
                                    </MDBBtn>
                                </MDBListGroupItem>
                            </div>
                        ))}
                    </MDBListGroup>
                </MDBRow>
            </div>
            <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
                <MDBRow className='m-3'>
                    <MDBCol md='4' className='fs-3 fw-bold'>
                        Splits history
                    </MDBCol>
                </MDBRow>
                <MDBRow className='m-3 w-100'>
                    <MDBListGroup className='m-1 ' light>
                        {splitsList.filter(split => split.finished === true).map(item => (
                            <div className='d-flex align-items-center m-2'>
                                <MDBListGroupItem className='d-flex justify-content-between align-items-center rounded-5 w-75'>
                                    <MDBIcon icon="dollar" size="2x" className='m-2' />
                                    <p className='fs-5 fw-bolder m-auto'>Split ID: {item.splitId}</p>
                                    <MDBBtn className='m-1' onClick={(e) => navigateToSplit(e, item.splitId)}>
                                        View
                                    </MDBBtn>
                                    <MDBBtn className='m-1 btn-danger' onClick={(e) => handleDelete(e, item.splitId)}>
                                        Delete
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

export default SplitsTables;