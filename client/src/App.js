import React from "react";
import "./styles/app.css";
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Register from "./components/Register";
import Login from "./components/Login";
import Home from "./components/Home";

function App() {
    return (
        <BrowserRouter>
            <div className="app-container">
                <Switch>
                    <Route path="/" component={Home} exact/>
                    <Route path="/login" component={Login} exact/>
                    <Route path="/register" component={Register} exact/>
                </Switch>
            </div>
        </BrowserRouter>
    );
}

export default App;