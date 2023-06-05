import React from "react";

const e = React.createElement;
let nextId = 0;

class ParticipantField extends React.Component {
    render() {
        return (
            <div className="row">
                <div className="container-fluid p-1 col-sm-10">
                    <input className="w-100 align-middle participant" type="email" placeholder='Участник опроса (почта)'/>
                </div>
                <div className="h-100 container-fluid p-1 col-sm-2">
                    <button className="btn btn-primary w-100 align-middle bg-dark" type="button"
                            onClick={() => this.props.delete_callback(this.props.id)}>
                        Удалить участника
                    </button>
                </div>
            </div>
        )
    }
}

class ParticipantContainer extends React.Component {
    constructor(props) {
        super(props);
        this.deleteParticipant = this.deleteParticipant.bind(this);
        this.state = {
            "participants": [
                e(ParticipantField,
                    {
                        id: nextId,
                        key: nextId++,
                        delete_callback: this.deleteParticipant})],
        }
        this.addParticipant = this.addParticipant.bind(this);
    }

    deleteParticipant(participant_id) {
        this.setState({
            participants: this.state.participants.filter(current_participant =>
                current_participant.props.id !== participant_id)
        })
    }

    addParticipant() {
        this.setState({
            participants: [...this.state.participants,
                e(ParticipantField, {
                    id: nextId,
                    key: nextId++,
                    delete_callback: this.deleteParticipant})]
        });
    }

    render() {
        return (
            <div className="container mt-2" id="participant-container">
                {this.state.participants}
                <button className="btn btn-primary w-100 mt-2 bg-dark" type="button" onClick={this.addParticipant}>
                    Добавить участника
                </button>
            </div>
        )
    }
}

export default ParticipantContainer;