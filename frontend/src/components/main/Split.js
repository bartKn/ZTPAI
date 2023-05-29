import {useContext, useEffect, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";
import AuthContext from "../../context/AuthProvider";
import {useLocation, useNavigate} from "react-router-dom";
import {MDBIcon, MDBListGroup, MDBListGroupItem} from "mdb-react-ui-kit";
import NavBar from "../NavBar";

const numberPattern = /\d+$/;
const SPLIT_DETAILS_URL = '/splits/';

const Split = () => {

    const axios = useContext(AxiosContext);
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();
    const location = useLocation();

    const[owner, setOwner] = useState(location.state?.user || 0);
    const[id, setId] = useState(0);
    const[contributors, setContributors] = useState([]);
    const[finished, setFinished] = useState(false);

    useEffect(() => {
        const url = window.location.href.toString();
        const matches = url.match(numberPattern);
        if (matches) {
            axios.authAxios.get(SPLIT_DETAILS_URL.concat(matches[0]))
                .then((res) => {
                    setId(res?.data?.splitId);
                    setContributors(Object.entries(res?.data?.contributions));
                    setFinished(res?.data?.finished);
                });
        }
    }, [axios]);

    return (
        <>
            <NavBar />
            <div>{owner}</div>
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
        </>
    );
}

export default Split;