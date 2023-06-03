import {MDBCol, MDBIcon, MDBRow} from "mdb-react-ui-kit";
import React from "react";

const ContributorsList = ({contributors}) => {
    return (
        <div className='m-3 p-3 bg-gradient rounded-6 border border-secondary shadow-5' style={{backgroundColor : '#CCD6F6'}}>
            <MDBRow className='m-3 fs-3 fw-bold'>
                <MDBCol md='9' className='fs-3 fw-bolder'>
                    Participants
                </MDBCol>
                <MDBCol md='3' className='fs-3 fw-bolder'>
                    Is completed
                </MDBCol>
            </MDBRow>
            {contributors.map(c => (
                <MDBRow className='mt-4 mb-4 ms-4'>
                    <MDBCol md='6' className='fs-3 fw-bolder'>
                        User: {c[0]}
                    </MDBCol>
                    <MDBCol md='4' className='fs-3 fw-bolder'>
                        Balance: {c[1] !== -1 ? c[1] : 0}
                    </MDBCol>
                    <MDBCol md='2' className='fs-3 fw-bolder'>
                        {c[1] !== -1 ?
                            <MDBIcon icon="check" size="1x" className='alert-success' style={{ background: 'transparent' }}/>
                            :
                            <MDBIcon icon="times" size="1x" className='alert-danger' style={{ background: 'transparent' }}/>
                        }
                    </MDBCol>
                </MDBRow>
            ))}
        </div>
    );
}

export default ContributorsList;