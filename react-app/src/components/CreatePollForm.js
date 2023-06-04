import React from 'react'
import http from "../http-common";

const e = React.createElement;

let nextId = 0;

class AnswerField extends React.Component{
    render() {
        return (
            <div className="row">
                <div className="container-fluid p-1 col-sm-10">
                    <input className="w-100 align-middle answer" placeholder='Вариант ответа'/>
                </div>
                <div className="h-100 container-fluid p-1 col-sm-2">
                    <button className="btn btn-primary w-100 align-middle" type="button"
                            onClick={() => this.props.delete_callback(this.props.id)}>
                        Удалить ответ
                    </button>
                </div>
            </div>
        )
    }
}

class AnswerContainer extends React.Component {
    constructor(props) {
        super(props);
        this.deleteAnswer = this.deleteAnswer.bind(this)
        this.state = {
            "answers": [
                e(AnswerField,
                    {
                        id: nextId,
                        key: nextId++,
                        delete_callback: this.deleteAnswer})],
        }
        this.addAnswer = this.addAnswer.bind(this)
    }

    deleteAnswer(answer_id) {
        this.setState({
            answers: this.state.answers.filter(current_answer => current_answer.props.id !== answer_id)
        })
    }

    addAnswer() {
        this.setState({
            answers: [...this.state.answers,
                e(AnswerField, {
                    id: nextId,
                    key: nextId++,
                    delete_callback: this.deleteAnswer})]
        })
    }

    render() {
        return (
            <div className="container mt-2" id="answer-container">
                {this.state.answers}
                <button className="btn btn-primary w-100 mt-2" type="button" onClick={this.addAnswer}>
                    Добавить вариант ответа
                </button>
            </div>
        )
    }
}

class CreateVotingButton extends React.Component {
    constructor(props) {
        super(props);
        this.createVoting = this.createVoting.bind(this)
    }
    async createVoting() {
        let answer_elements = document.getElementsByClassName('answer')
        let question_element = document.getElementById('question')

        const response = http.post('/create_poll', {
            creatorEmail: "a@b.c",
            question: question_element.value,
            answers: Array.from(answer_elements, x => { return {answerText: x.value} }),
            participants: [{email: "d@e.f"}]
        }).then(r=>console.log(r.data))
    }
    render() {
        return (
            <div className="h-100 container-fluid p-1 col-sm-4">
                <button className="btn btn-primary w-100 align-middle" type="button" onClick={this.createVoting}>
                    Создать
                </button>
            </div>
        )
    }
}


class CreatePollForm extends React.Component {

    render() {
        return (
            <div className="container pr-5 pl-5 pt-1">
                <h1>Новый опрос</h1>
                <div className="row">
                    <div className="container-fluid p-1 col-sm-8" id="question">
                        <input id="question" className="w-100 align-middlef fs-3" placeholder='Ваш вопрос'/>
                    </div>
                    <CreateVotingButton />
                </div>
                <AnswerContainer />
            </div>
        )
    }
}

export default CreatePollForm;