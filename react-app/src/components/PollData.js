import React from "react";

class PollData extends React.Component {
    is_finished = this.props.poll["answerCounter"] === this.props.poll["participants"].length;

    render() {
        return (<div style={{border: "1px solid black", backgroundColor: this.is_finished ? "darkseagreen" : "lightgray"}}>
            <h3>{this.props.poll["question"]}</h3>
            <p>проголосовало: {this.props.poll["answerCounter"]}/{this.props.poll["participants"].length}</p>
            <p>статус: {
                this.is_finished ? "завершён" : "в процессе"
            }</p>
        </div>);
    }
}

export default PollData;