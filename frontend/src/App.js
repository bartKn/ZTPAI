import {Routes, Route} from "react-router-dom";
import Layout from "./components/Layout";
import Profile from "./components/Profile";
import Login from "./components/Login";
import Main from "./components/Main";


function App() {
    return (
       <Routes>
           <Route path="/" element={<Layout/>}>
               <Route path="/" element={<Main/>}/>
               <Route path="login" element={<Login/>}/>
               <Route path="profile" element={<Profile/>}/>
           </Route>
       </Routes>
    )
}

export default App;