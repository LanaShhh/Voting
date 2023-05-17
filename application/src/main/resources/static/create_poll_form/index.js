'use strict';

const e = React.createElement;

let nextId = 0;

class AnswerField extends React.Component{
    render() {
        return (
            <div className="row">
                <div className="container-fluid p-1 col-sm-10">
                    <input id="question" className="w-100 align-middle" placeholder='Вариант ответа'/>
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
            answers: [
                e(AnswerField,
                    {
                        id: nextId,
                        key: nextId++,
                        delete_callback: this.deleteAnswer})],
        }
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
                    delete_listener: this.deleteAnswer})]
        })
    }

    render() {
        return (
            <div className="container mt-2" id="answer-container">
                {this.state.answers}
                <button className="btn btn-primary w-100 mt-2" type="button" onClick={() => this.addAnswer()}>
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
    createVoting() {
        const answers = this.props.answer_container
        console.log(anwers)
    }
    render() {
        return (
            <div className="h-100 container-fluid p-1 col-sm-4">
                <button className="btn btn-primary w-100 align-middle" type="button" onClick={this.createVoting}>
                    Создать опрос
                </button>
            </div>
        )
    }
}


class Page extends React.Component {

    render() {
        const answer_container = e(AnswerContainer);
        const create_voting_button = e(CreateVotingButton, { answer_container: answer_container });
        return (
            <div className="container pr-5 pl-5 pt-1">
                <div className="row">
                    <div className="container-fluid p-1 col-sm-8">
                        <input id="question" className="w-100 align-middlef fs-3" placeholder='Ваш вопрос...'/>
                    </div>
                    {create_voting_button}
                </div>
                {answer_container}
            </div>
        )
    }
}


const root = document.getElementById('root');
ReactDOM.render(e(Page), root);