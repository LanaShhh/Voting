import { useSearchParams } from "react-router-dom";
import http from "../http-common";

function getPoll(password) {
    http.get("/get_poll?password=" + password).then(
        (res) => {
            this.setState({submitted: true});
        }
    ).catch(
        (err) => {
            console.log("ERROR");
            console.log(err.response.status);
            console.log(err.response.data);
        }
    );
}

export default function ChooseAnswerForm() {
    const [queryParameters] = useSearchParams();
    const password = queryParameters.get("password");

    http.get("/get_poll?password=" + password).then(
        (res) => {
            this.setState({submitted: true});
        }
    ).catch(
        (err) => {
            console.log("ERROR");
            console.log(err.response.status);
            console.log(err.response.data);
        }
    );

    return (<div>
        <h1>Голосуй</h1>
        <form>
            <
        </form>
    </div>);
}