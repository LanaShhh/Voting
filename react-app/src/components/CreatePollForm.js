import React, { useState } from 'react';
import { useLocation, Navigate } from "react-router-dom";
import http from "../http-common";
import AnswerContainer from "./AnswerContainer";
import ParticipantContainer from "./ParticipantContainer";
import VotingButton from "./VotingButton";

export default function CreatePollForm() {
    const { userEmail } = useLocation().state;
    const [submitted, setSubmitted] = useState(false);

    function handleSubmit(evt) {
        evt.preventDefault();

        let answer_elements = document.getElementsByClassName('answer');
        let participant_elements = document.getElementsByClassName('participant');
        let question_element = document.getElementById('question');

        let newPoll = {
            creatorEmail: userEmail,
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
                console.log("ERROR");
                console.log(err.response.status);
                console.log(err.response.data);
            }
        );
    }

    if (submitted) {
        return (
            <Navigate to="/account" state={{userEmail: userEmail}} />
        );
    }

    return (
        <div className="container pr-5 pl-5 pt-1">
            <form onSubmit={handleSubmit}>
                <h1>Новый опрос</h1>
                <div className="row">
                    <div className="container-fluid p-1 col-sm-8">
                        <input id="question" className="w-100 align-middlef fs-3" placeholder='Ваш вопрос'/>
                    </div>
                    <VotingButton />
                </div>
                <AnswerContainer />
                <ParticipantContainer />
            </form>
        </div>
    );
}