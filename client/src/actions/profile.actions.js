import axios from 'axios';
const API_URL = process.env.API_URL;

export const addSkill = (skill) => {
    const data = {
        description: skill
    }
    const headers = {"Content-Type": "application/json"}
    return dispatch => {
        axios.post(API_URL + "/users/skill", JSON.stringify(data), {headers})
            .then(res => {
                if (res.data.success) {
                    dispatch({type: "ADD_SKILL", payload: {description: skill}})
                } else {
                    console.log(res.data.error);
                }
            })
            .catch(err => {
                console.error(err);
            });
    }
}

export const addTeam = (team) => {
    const data = {
        name: team,
    }
    const headers = {"Content-Type": "application/json"}
    return dispatch => {
        axios.post(API_URL + "/team", JSON.stringify(data), {headers})
            .then(res => {
                if (res.data.success) {
                    dispatch({type: "ADD_TEAM", payload: {team: res.data.team}})
                } else {
                    console.log(res.data.error);
                }
            })
            .catch(err => {
                console.error(err);
            });
    }
}

export const addToTeam = (team) => {
    const data = {
        name: team,
    }
    const headers = {"Content-Type": "application/json"}
    return dispatch => {
        axios.post(API_URL + "/team/join", JSON.stringify(data), {headers})
            .then(res => {
                if (res.data.success) {
                    dispatch({type: "JOIN_TEAM", payload: {team: res.data.team}})
                } else {
                    console.log(res.data.error);
                }
            })
            .catch(err => {
                console.error(err);
            });
    }
}

export const removeSkill = (skill) => {
    const headers = {"Content-Type" : "application/json"}
    const data = {
        description: skill.description
    }
    return dispatch => {
        axios.post(API_URL + "/users/skill/delete", JSON.stringify(data), {headers})
            .then(res => {
                if (res.data.success) {
                    dispatch({type: "REMOVE_SKILL", payload: skill})
                } else {
                    console.log(res.data.error);
                }
            })
            .catch(err => {
                console.error(err);
            });
    }
}
