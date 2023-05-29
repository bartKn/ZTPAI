import {Routes, Route} from "react-router-dom";
import RequireAuth from "./components/auth/RequireAuth";
import Layout from "./components/Layout";
import Profile from "./components/profile/Profile";
import Login from "./components/auth/Login";
import Register from "./components/auth/Register";
import Main from "./components/main/Main";
import Friends from "./components/friends/Friends";
import Unauthorized from "./components/Unauthorized";
import Split from "./components/main/Split";


function App() {
    return (
       <Routes>
           <Route path="/" element={<Layout />}>
               <Route path="/" element={<Main />} />
               <Route path="split/*" element={<Split />} />
               <Route path="login" element={<Login />} />
               <Route path="register" element={<Register />} />
               <Route path="unauthorized" element={<Unauthorized />} />

               <Route element={<RequireAuth allowedRoles={['ROLE_USER', 'ROLE_ADMIN']} /> } >
                   <Route path="profile" element={<Profile />} />
               </Route>

               <Route element={<RequireAuth allowedRoles={['ROLE_USER', 'ROLE_ADMIN']} /> } >
                   <Route path="friends" element={<Friends />} />
               </Route>

           </Route>
       </Routes>
    )
}

export default App;