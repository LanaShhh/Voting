class RegistrationForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            email: "",
            password: ""
        };
    }

    handleSubmit = async (evt) => {
        evt.preventDefault();
        console.log("start handle");
        try {
            let res = await fetch("/register", {
                mode: "cors",
                method: "POST",
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
                console.log("Some error occured");
            }
        } catch (err) {
            console.log(err);
        }
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