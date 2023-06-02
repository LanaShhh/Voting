import { Link, useLocation } from "react-router-dom";

export default function Account() {
    const { userEmail } = useLocation().state;

    return (<div>
        <h1>{userEmail}</h1>
        <aside>
            <Link to="/create_poll">
                <button type="button">Новый опрос</button>
            </Link>
        </aside>
    </div>);
}