import axios from 'axios';
const API_URL = process.env.API_URL;

export const registerUser = (email, username, password) => {
    const data = {
        username: username,
        password: password,
        email: email
    }
    const headers = {"Content-Type" : "application/json"}
    return dispatch => {
        axios.post(API_URL + "/users/signup", JSON.stringify(data), {headers})
            .then(res => {
                console.log("Success");
                // TODO: Login user
            })
            .catch(err => {
                console.log("failed");
                console.log(err);
                // TODO: Notify User of error
            })
    }
}

export const login = (username, password) => {
    const data = {
        username: username,
        password: password
    }
    const headers = {"Content-Type" : "application/json"}
    return dispatch => {
        axios.post(API_URL + "/login", JSON.stringify(data), {headers})
            .then(res => {
                console.log("Success");
                console.log(res.headers);
                // TODO: Send user to dashboard
            })
            .catch(err => {
                console.log("failed");
                // TODO: Notify User of error
            })
    }
}