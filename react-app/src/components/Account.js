import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import http from "../http-common";
import PollData from "./PollData";
import translateToRussian from "../translation";

export default function Account() {
    const { userEmail } = useLocation().state;
    const [userPolls, setUserPolls] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");

    function getPolls(email) {
        http.get("/get_user_polls?email=" + email).then(
            (res) => {
                setUserPolls(res.data);
            }
        ).catch(
            (err) => {
                setErrorMessage("Ошибка: " + translateToRussian(err.response.data));
            }
        );
    }

    useEffect(() => {
        getPolls(userEmail);
    }, [userEmail]);

    return (<div>
        <nav className="navbar navbar-expend-md">
            <a className="navbar-brand">
                Голосовалка|Личный кабинет {userEmail}
            </a>
        </nav>
        {userPolls.length > 0 && <h1>Мои опросы</h1>}
        {errorMessage && <h6 style={{color: "red"}}>{errorMessage}</h6>}
        {userPolls.map((poll) => {
            return (<div key={poll["pollId"]}>
                <PollData poll={poll}></PollData>
            </div>);
        })}
        <aside>
            <Link to="/create_poll" state={{userEmail: userEmail}}>
                <button type="button " className="btn default">Создать новый опрос</button>
            </Link>
        </aside>
    </div>);
}