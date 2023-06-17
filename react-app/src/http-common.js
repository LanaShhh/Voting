import axios from "axios";

export default axios.create({
    baseURL: "http://localhost:" + process.env.REACT_APP_BACKEND_PORT,
    headers: {
        "Content-type": "application/json"
    }
});