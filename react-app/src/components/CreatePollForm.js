import React, {useEffect, useState} from 'react';
import { useLocation, Navigate } from "react-router-dom";
import http from "../http-common";
import AnswerContainer from "./AnswerContainer";
import ParticipantContainer from "./ParticipantContainer";
import VotingButton from "./VotingButton";
import checkLength from "../check_format_functions/check-length";
import checkOnlySpaces from "../check_format_functions/check-only-spaces";
import translateToRussian from "../translation";

export default function CreatePollForm() {
    const locationState = useLocation().state;

    const [submitted, setSubmitted] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    function isPollCorrect(answer_elements, participant_elements, question_element) {
        if (!checkLength(question_element.value, 500)) {
            setErrorMessage("Ошибка: длина вопроса не может превышать 500 символов");
            return false;
        }

        if (!checkOnlySpaces(question_element.value)) {
            setErrorMessage("Ошибка: вопрос не может состоять из одних пробелов");
            return false;
        }

        if (answer_elements.length > 100) {
            setErrorMessage("Ошибка: вариантов ответа не может быть больше 100");
            return false;
        }

        for (let el of answer_elements) {
            if (!checkLength(el.value, 500)) {
                setErrorMessage("Ошибка: длина ответа не может превышать 500 символов");
                return false;
            }

            if (!checkOnlySpaces(el.value)) {
                setErrorMessage("Ошибка: в каждом из вариантов ответа должны быть непробельные символы");
                return false;
            }
        }

        if (participant_elements.length > 120) {
            setErrorMessage("Ошибка: участников не может быть больше 120");
            return false;
        }

        for (let el of participant_elements) {
            if (!checkLength(el.value)) {
                setErrorMessage("Ошибка: длина почты участника не может превышать 50 символов");
                return false;
            }
        }

        return true;
    }

    function handleSubmit(evt) {
        evt.preventDefault();
        setErrorMessage("");

        let answer_elements = document.getElementsByClassName('answer');
        let participant_elements = document.getElementsByClassName('participant');
        let question_element = document.getElementById('question');

        if (!isPollCorrect(answer_elements, participant_elements, question_element)) {
            return;
        }

        let newPoll = {
            creatorEmail: locationState.userEmail,
            question: question_element.value,
            answers: Array.from(answer_elements, x => { return {answerText: x.value} }),
            participants: Array.from(participant_elements, x => { return {email: x.value} })
        }
        http.post('/create_poll', newPoll).then(
            () => {
                setSubmitted(true);
            }
        ).catch(
            (err) => {
                setErrorMessage("Ошибка: " + translateToRussian(err.response.data));
            }
        );
    }

    if (!locationState) {
        // user unregistered
        return (
            <Navigate to="/login" />
        );
    }

    if (submitted) {
        return (
            <Navigate to="/account" state={{userEmail: locationState.userEmail}} />
        );
    }

    return (
        <div className="container pr-5 pl-5 pt-1">
            <form onSubmit={handleSubmit}>
                <h1>Новый опрос</h1>
                <div className="container-fluid">
                    <div className="row">
                        <div className="container-fluid p-1 col-sm-8">
                            <input id="question" className="w-100 align-middlef fs-3" placeholder='Ваш вопрос' required/>
                        </div>
                        <VotingButton />
                    </div>
                </div>

                <AnswerContainer />
                <ParticipantContainer />
            </form>
            {errorMessage && <h6 style={{color: "red"}}>{errorMessage}</h6>}
        </div>
    );
}