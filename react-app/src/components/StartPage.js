import React from "react";
import { Link } from "react-router-dom";

class StartPage extends React.Component {
    render() {
        return (<div>
            <h1>Голосовалка</h1>
            <Link to={"/login"}>
                <button type="button">Вход</button>
            </Link>
            <Link to={"/register"}>
                <button type="button">Регистрация</button>
            </Link>
        </div>);
    }
}

export default StartPage;