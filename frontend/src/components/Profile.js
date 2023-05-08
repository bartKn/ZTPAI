import NavBar from "./NavBar";
import useAuth from "../hooks/useAuth";
import {useNavigate} from "react-router-dom";

const Profile = () => {
    const navigate = useNavigate();
    const goMain = () => navigate('/');
    const { auth } = useAuth();
    console.log('profile');
    console.log(auth.roles);
    return (
        <>
            <NavBar />
            <section>
                <br />
                <div>Profile</div>
                <div>
                    <button onClick={goMain}>Go main</button>
                </div>
            </section>
        </>
    );
}

export default Profile;