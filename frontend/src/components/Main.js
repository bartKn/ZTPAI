import NavBar from "./NavBar";
import {useNavigate} from "react-router-dom";


const Main = () => {
    const navigate = useNavigate();
    const goProfile = () => navigate('/profile');
    return (
        <>
            <NavBar />
            <section>
                <br />
                <div>Main page</div>
                <div>
                    <button onClick={goProfile}>Go to profile</button>
                </div>
            </section>
        </>
    );
}

export default Main;