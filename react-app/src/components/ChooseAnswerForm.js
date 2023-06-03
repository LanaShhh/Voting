import { useSearchParams } from "react-router-dom";
import http from "../http-common";

export default function ChooseAnswerForm() {
    const [queryParameters] = useSearchParams();

    return (<div>
        <h1>Голосуй</h1>
    </div>);
}