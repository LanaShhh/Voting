import React from "react";
import http from "../http-common";
import { Navigate } from 'react-router-dom';

class LoginForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            submitted: false
        };

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(evt) {
        evt.preventDefault();

        let someUser = {
            email: this.state.email,
            password: this.state.password
        };
        http.post("/login", someUser).then(
            (res) => {
                this.setState({submitted: true});
            }
        ).catch(
            (err) => {
                console.log("ERROR");
                console.log(err.response.status);
                console.log(err.response.data);
            }
        );
    };

    render() {
        if (this.state.submitted) {
            return (
                <Navigate to="/account" state={{userEmail: this.state.email}} />
            );
        }

        return (<div>
            <form onSubmit={this.handleSubmit}>
                <h1>Вход</h1>
                <input type="email" placeholder="Почта" onChange={
                    (evt) => this.setState({email: evt.target.value})
                } required />
                <input type="password" placeholder="Пароль" onChange={
                    (evt) => this.setState({password: evt.target.value})
                } required />
                <button type="submit">Войти</button>
            </form>
        </div>);
    }
}

export default LoginForm;