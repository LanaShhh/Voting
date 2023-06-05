import React from "react";

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
        this.deleteAnswer = this.deleteAnswer.bind(this);
        this.state = {
            "answers": [
                e(AnswerField,
                    {
                        id: nextId,
                        key: nextId++,
                        delete_callback: this.deleteAnswer})],
        }
        this.addAnswer = this.addAnswer.bind(this);
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
        });
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

export default AnswerContainer;