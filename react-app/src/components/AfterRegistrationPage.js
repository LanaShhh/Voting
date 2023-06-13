import { Link } from "react-router-dom";
import React from "react";

export default function AfterRegistrationPage() {
    return (<div>
        <h1>Регистрация прошла успешно!</h1>
        <Link to={"/login"}>
            <button type="button " className="btn default">Вход</button>
        </Link>
        <Link to={"/"}>
            <button type="button " className="btn default">На главную</button>
        </Link>
    </div>);
}