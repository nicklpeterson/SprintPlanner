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
