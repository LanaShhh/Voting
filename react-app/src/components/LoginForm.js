import React from "react";
import http from "../http-common";
import { Navigate } from 'react-router-dom';
import checkLength from "../check_format_functions/check-length";
import translateToRussian from "../translation";

class LoginForm extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            submitted: false,
            errorMessage: ""
        };

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    isInputCorrect() {
        if (!checkLength(this.state.email)) {
            this.setState({errorMessage: "Ошибка: длина почты не может превышать 50 символов"});
            return false;
        }

        if (!checkLength(this.state.password)) {
            this.setState({errorMessage: "Ошибка: длина пароля не может превышать 50 символов"});
            return false;
        }

        return true;
    }

    handleSubmit(evt) {
        evt.preventDefault();
        this.setState({errorMessage: ""});

        if (!this.isInputCorrect()) {
            return;
        }

        let someUser = {
            email: this.state.email,
            password: this.state.password
        };
        http.post("/login", someUser).then(
            () => {
                this.setState({submitted: true});
            }
        ).catch(
            (err) => {
                this.setState({errorMessage: "Ошибка: " + translateToRussian(err.response.data)});
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
            <form role="form" onSubmit={this.handleSubmit}>
                <nav className="navbar navbar-expend-md">
                    <a className="navbar-brand" href="#">
                        Голосовалка | Вход
                    </a>
                </nav>
                <div className="btn-group" >
                    <div className="container">
                        <div className="row">
                            <div className="col-md-12">
                                <input type="email" placeholder="Почта" style={{marginBottom: "10px"}} onChange={
                                    (evt) => this.setState({email: evt.target.value})
                                } required />
                            </div>
                            <div className="col-md-12">
                            <input type="password" placeholder="Пароль" onChange={
                                (evt) => this.setState({password: evt.target.value})
                            } required />
                            </div>
                            <div className="col-md-12">
                            <button className="btn default" type="submit">Войти</button>
                            </div>
                    </div>

                </div>
                </div>
            </form>
            {this.state.errorMessage && <h6 style={{color: "red"}}>{this.state.errorMessage}</h6>}
        </div>);
    }
}

export default LoginForm;