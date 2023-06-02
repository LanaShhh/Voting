import React from "react";
import { Routes, Route } from "react-router-dom";
import StartPage from "./components/StartPage";
import RegistrationForm from "./components/RegistrationForm";
import LoginForm from "./components/LoginForm";
import Account from "./components/Account";
import CreatePollForm from "./components/CreatePollForm";

class App extends React.Component {
    render() {
        return (<div>
            <Routes>
                <Route path="/" element={<StartPage/>} />
                <Route path="/register" element={<RegistrationForm/>} />
                <Route path="/login" element={<LoginForm/>} />
                <Route path="/account" element={<Account/>} />
                <Route path="/create_poll" element={<CreatePollForm/>} />
            </Routes>
        </div>);
    }
}

export default App;