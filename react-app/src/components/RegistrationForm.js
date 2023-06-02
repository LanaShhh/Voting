import React from "react";
import http from "../http-common";
import { Navigate } from 'react-router-dom';

class RegistrationForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            passwordCheck: "",
            submitted: false
        };

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(evt) {
        evt.preventDefault();

        if (this.state.password !== this.state.passwordCheck) {
            console.log("Повторите палорь правильно");
            return;
        }

        let newUser = {
            email: this.state.email,
            password: this.state.password
        };
        http.post("/register", newUser).then(
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
               <Navigate to="/" />
           );
        }

        return (<div>
            <h1>Регистрация</h1>
            <form onSubmit={this.handleSubmit}>
                <input type="email" placeholder="Почта" onChange={
                    (evt) => this.setState({email: evt.target.value})
                } required />
                <input type="password" placeholder="Пароль" onChange={
                    (evt) => this.setState({password: evt.target.value})
                } required />
                <input type="password" placeholder="Повторите пароль" onChange={
                    (evt) => this.setState({passwordCheck: evt.target.value})
                } required />
                <button type="submit">Создать аккаунт</button>
            </form>
        </div>);
    }
}

export default RegistrationForm;