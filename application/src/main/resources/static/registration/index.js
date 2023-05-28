'use strict';

class RegistrationForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: ""
        };
    }

    // отправляем данные бэкенду, создаём нового пользователя
    handleSubmit = async (evt) => {
        evt.preventDefault();
        console.log("start handle");

        let res = await fetch("/register", {
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            body: JSON.stringify({
                email: this.state.email,
                password: this.state.password,
            }),
        });
        console.log("doing handling");
        let resJson = await res.json();
        if (res.status === 200) {
            this.setState({
                email: "",
                password: ""
            });
        } else {
            console.log(resJson);
            console.log(res);
        }
    };

    // создаём форму
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

const e = React.createElement;
const app = document.getElementById("app");
ReactDOM.render(e(RegistrationForm), app);
