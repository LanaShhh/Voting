class RegistrationForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: ""
        };
    }

    handleSubmit() {
        evt.preventDefault();
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "https://reqbin.com/echo/post/json");
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                console.log(xhr.status);
                console.log(xhr.responseText);
            }
        };

        let data = `{
            "email": ma@m,
            "password": 78912
        }`;

        xhr.send(data);


        /* console.log("start handle");
        try {
            let res = fetch("/register", {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    email: this.state.email,
                    password: this.state.password,
                }),
            });
            console.log("doing handling");
            let resJson = res.json();
            if (res.status === 200) {
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
                } required/>
                <input type="password" placeholder="Пароль" onChange={
                    (evt) => this.setState({password: evt.target.value})
                } required/>
                <button type="submit">Создать аккаунт</button>
            </form>
        </div>);
    }
}

export default RegistrationForm;