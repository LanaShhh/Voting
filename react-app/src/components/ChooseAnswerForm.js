import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import http from "../http-common";
import translateToRussian from "../translation";

export default function ChooseAnswerForm() {
    const [question, setQuestion] = useState(null);
    const [answers, setAnswers] = useState([]);
    const [chosenAnswer, setChosenAnswer] = useState(null);
    const [access, setAccess] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    function getPollData(password) {
        http.get("/get_poll_data?password=" + password).then(
            (res) => {
                setAccess(true);
                setQuestion(res.data["question"]);
                setAnswers(res.data["answers"]);
            }
        ).catch(
            (err) => {
                setErrorMessage("Ошибка: " + translateToRussian(err.response.data));
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

        setErrorMessage("");
        setSuccessMessage("");

        http.put(`/choose_answer?password=${password}&answer=${chosenAnswer}`).then(
            () => {
                setSuccessMessage("Ваш ответ успешно записан");
            }
        ).catch(
            (err) => {
                setErrorMessage("Ошибка: " + translateToRussian(err.response.data));
            }
        );
    }

    if (!access) {
        return (<div>
            <h6 style={{color: "red"}}>{errorMessage}</h6>
        </div>);
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
        {errorMessage && <h6 style={{color: "red"}}>{errorMessage}</h6>}
        {successMessage && <h6 style={{color: "green"}}>{successMessage}</h6>}
    </div>);
}