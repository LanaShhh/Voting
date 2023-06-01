import React from "react";
import * as ReactDOMClient from "react-dom/client";
import RegistrationForm from "./RegistrationForm";

const app = ReactDOMClient.createRoot(document.getElementById("app"));
app.render(<RegistrationForm />);
