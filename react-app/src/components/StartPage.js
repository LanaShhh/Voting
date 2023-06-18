import React from "react";
import { Link } from "react-router-dom";

class StartPage extends React.Component {
    render() {
        return (<div>
            <nav className="navbar navbar-expend-md">
                <a className="navbar-brand" href="#">
                        Голосовалка
                </a>
            </nav>
            <div className="btn-group btn-lg" data-toggle='buttons'>
                <Link to={"/login"}>
                    <button type="button " className="btn default">Вход</button>

                </Link>
                <Link to={"/register"}>
                    <button type="button " className="btn default">Регистрация</button>
                </Link>
            </div>

        </div>);
    }
}

export default StartPage;