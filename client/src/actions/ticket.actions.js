import axios from 'axios';
const API_URL = process.env.API_URL;

export const getTicketInfo = (ticketId) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/ticket/${ticketId}`, {headers})
            .then(res => {
                dispatch(updateTicket(res.data.ticket));
            })
            .catch(err => {
                console.log("Failed to get the ticket");
                console.log(err);
                console.log(err.toString());
            });
    }
};

export const getMembersCanBeAssignedTo = (ticketId) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/ticket/getAvailableMembers/${ticketId}`, {headers})
            .then(res => {
                console.log(res.data);
                dispatch({type: 'GET_AVAILABLE_MEMBERS', state: res.data.members});
            })
            .catch(err => {
                console.log("Failed to get team members");
                console.log(err.toString());
            });
    }
};


export const getUsername = (uuid, isAssignee) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/users/getUsername/${uuid}`, {headers})
            .then(res => {
                dispatch(updateUsername(res.data.username, isAssignee));
            })
            .catch(err => {
                console.log("I FAILED");
                console.log("Failed to get the username");
                console.log(err.toString());
            });
    }
};

export const updateUsername = (username, isAssignee) => {
    return {
        type: isAssignee ? 'GET_ASSIGNEE_USERNAME' : 'GET_REPORTER_USERNAME',
        state: username
    }
};

export const updateTicket = (ticket) => {
    return {
        type: 'GET_TICKET_BY_ID',
        state: ticket
    }
};


export const updatePoints = (newPoints, ticketId) => {
    const data = {
        ticketId: ticketId
    };

    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.post(API_URL + `/ticket/updatePoints/`, JSON.stringify(data), {headers, params: {points: newPoints}})
            .then(res => {
                dispatch({type: 'UPDATE_TICKET_POINTS', state: newPoints});
                console.log("Success");
            })
            .catch(err => {
                console.log("Failed to uppdate points");
                console.log(err);
            });
    }
};

export const updateAssignee = (newAssignee, ticketId) => {
    const data = {
        ticketId: ticketId
    };

    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.post(API_URL + `/ticket/updateAssignee/`, JSON.stringify(data), {headers, params: {userId: newAssignee}})
            .then(res => {
                dispatch({type: 'UPDATE_ASSIGNEE', state: newAssignee});
                console.log("Success");
            })
            .catch(err => {
                console.log("Failed to uppdate points");
                console.log(err);
            });
    }
};