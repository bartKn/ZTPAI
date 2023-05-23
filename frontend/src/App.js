import {Routes, Route} from "react-router-dom";
import RequireAuth from "./components/RequireAuth";
import Layout from "./components/Layout";
import Profile from "./components/Profile";
import Login from "./components/Login";
import Register from "./components/Register";
import Main from "./components/Main";
import Friends from "./components/Friends";
import Unauthorized from "./components/Unauthorized";


function App() {
    return (
       <Routes>
           <Route path="/" element={<Layout />}>
               <Route path="/" element={<Main />} />
               <Route path="main*" element={<Main />} />
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