import { Link } from "react-router-dom";

export default function AfterRegistrationPage() {
    return (<div>
        <h1>Регистрация прошла успешно!</h1>
        <Link to={"/login"}>
            <button type="button">Вход</button>
        </Link>
        <Link to={"/"}>
            <button type="button">Назад</button>
        </Link>
    </div>);
}