import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import http from "../http-common";
import PollData from "./PollData";

export default function Account() {
    const { userEmail } = useLocation().state;
    const [userPolls, setUserPolls] = useState([]);

    function getPolls(email) {
        http.get("/get_user_polls?email=" + email).then(
            (res) => {
                setUserPolls(res.data);
            }
        ).catch(
            (err) => {
                console.log("ERROR");
                console.log(err.response.status);
                console.log(err.response.data);
            }
        );
    }

    useEffect(() => {
        getPolls(userEmail);
    }, [userEmail]);

    return (<div>
        <h1>{userEmail}</h1>
        {userPolls.length > 0 && <h2>Мои опросы</h2>}
        {userPolls.map((poll) => {
            return (<div key={poll["pollId"]}>
                <PollData poll={poll}></PollData>
            </div>);
        })}
        <aside>
            <Link to="/create_poll" state={{userEmail: userEmail}}>
                <button type="button">Новый опрос</button>
            </Link>
        </aside>
    </div>);
}