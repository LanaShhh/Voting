import React from "react";
import http from "./http-common"

class RegistrationForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: ""
        };

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(evt) {
        evt.preventDefault();

        let newUser = {
            email: this.state.email,
            password: this.state.password
        };
        http.post("/register", newUser);
        /*try {
            let res = await fetch("/register", {
                mode: "cors",
                method: "POST",
                body: JSON.stringify({
                    email: this.state.email,
                    password: this.state.password,
                }),
            });
            let res = await fetch("/test?x=42", {
                mode: "cors",
                method: "POST",
                body: {
                    "x": 2,
                    "y": 0.2
                }
            });
            let resJson = await res.text();
            console.log(resJson);
            if (res.status === 200) {
                console.log(resJson)
                this.setState({
                    email: "",
                    password: ""
                });
            } else {
                console.log("Some error occured");
            }
        } catch (err) {
            console.log(err);
        }*/
    };

    render() {
        return (<div className="app-div">
            <h1>Регистрация</h1>
            <form onSubmit={this.handleSubmit}>
                <input type="email" placeholder="Почта" onChange={
                    (evt) => this.setState({email: evt.target.value})
                } required />
                <input type="password" placeholder="Пароль" onChange={
                    (evt) => this.setState({password: evt.target.value})
                } required />
                <button type="submit">Создать аккаунт</button>
            </form>
        </div>);
    }
}

export default RegistrationForm;