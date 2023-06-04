import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import http from "../http-common";

export default function ChooseAnswerForm() {
    const [question, setQuestion] = useState(null);
    const [answers, setAnswers] = useState([]);
    let [chosenAnswer, setChosenAnswer] = useState(null);

    function getPollData(password) {
        http.get("/get_poll_data?password=" + password).then(
            (res) => {
                setQuestion(res.data["question"]);
                setAnswers(res.data["answers"]);
            }
        ).catch(
            (err) => {
                console.log("ERROR");
                console.log(err.response.status);
                console.log(err.response.data);
            }
        );
    }

    const [queryParameters] = useSearchParams();
    const password = queryParameters.get("password");
    useEffect(() => {
        getPollData(password);
    }, [password]);

    function handleSubmit(evt) {
        evt.preventDefault();

        http.put(`/choose_answer?password=${password}&answer=${chosenAnswer}`).then(
            (res) => {
                console.log(res.status);
                console.log(res.data);
            }
        ).catch(
            (err) => {
                console.log("ERROR");
                console.log(err.response.status);
                console.log(err.response.data);
            }
        );
    }

    return (<div>
        <form onSubmit={handleSubmit}>
            <h1>{question}</h1>
            {answers.map((ans, i) => {
                return (<div key={i}>
                    <input type="radio" id={"ans" + i} name="answer" value={"answer" + i} onChange={
                        () => setChosenAnswer(ans)
                    } required />
                    <label htmlFor={"ans" + i}>{ans}</label>
                </div>);
            })}
            <button type="submit">Проголосовать</button>
        </form>
    </div>);
}