'use strict';

class RegistrationForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: ""
        };

        //this.handleSubmit = this.handleSubmit.bind(this);
    }

    // отправляем данные бэкенду, создаём нового пользователя
    handleSubmit = (evt) => {
        evt.preventDefault();

        let res = fetch("/register", {
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            body: JSON.stringify({
                email: "eleazar12th@ru",
                password: "122s",
            }),
        });
        let resJson = res.json();
        if (res.status === 200) {
            this.setState({
                email: "",
                password: ""
            });
        } else {
            console.log("tumba-U");
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
