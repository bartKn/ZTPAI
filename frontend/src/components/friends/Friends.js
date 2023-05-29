import NavBar from "../NavBar";
import {MDBRow} from "mdb-react-ui-kit";
import FriendsList from "./FriendsList";
import {useContext, useEffect, useRef, useState} from "react";
import {AxiosContext} from "../../context/AxiosProvider";
import AddFriends from "./AddFriends";

const FRIENDS_URL = '/users/friends'

const Friends = () => {

    const axios = useContext(AxiosContext);

    const [fetchedFriends, setFetchedFriends] = useState([]);
    useRef();
    useEffect(() => {
        axios.authAxios.get(FRIENDS_URL)
            .then(res => {
                setFetchedFriends(res?.data);
            });
    }, [axios.authAxios]);

    const updateFriends = (newFriend) => {
        let currentFriends = fetchedFriends.slice();
        currentFriends.push(newFriend);
        setFetchedFriends(currentFriends);
    }

    return (
        <>
            <NavBar />
            <MDBRow className='m-2'>
                <AddFriends updateFriends={updateFriends} fetchedFriends={fetchedFriends} />
                <FriendsList friendsList={fetchedFriends}/>
            </MDBRow>
        </>
    );
}

export default Friends;