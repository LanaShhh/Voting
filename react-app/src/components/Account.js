import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import * as http from "http";

export default function Account() {
    const { userEmail } = useLocation().state;
    const [userPolls, setUserPolls] = useState([]);

    function getUserPolls() {
        http.get("/get_info?email=" + userEmail);
    }

    return (<div>
        <h1>{userEmail}</h1>
        <aside>
            <Link to="/create_poll">
                <button type="button">Новый опрос</button>
            </Link>
        </aside>
    </div>);
}