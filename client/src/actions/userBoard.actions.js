import axios from 'axios';
import { idMappedToStatus } from "../constants";

const API_URL = process.env.API_URL;

export const updateProgressStatus = (ticketId, newStatus) => {
    const data = {
        ticketId: ticketId
    };

    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.post(API_URL + "/board/updateStatus", JSON.stringify(data), {headers, params: {status: newStatus}})
            .then(res => {
                console.log("Success");
            })
            .catch(err => {
                console.log("Failed to uppdate progress");
                console.log(err);
                // TODO: Notify User of error
            });
    }
};

export const getTicketsByProgress = (userId, sprintId, status) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getStatus/${userId}/${sprintId}/${status}`, {headers})
            .then(res => {
                console.log("Successfully got tickets");
                console.log(res.data.tickets);
                dispatch(updateTicketByProgress(res.data.tickets, status, userId));
            })
            .catch(err => {
                console.log("Failed to get tickets");
                console.log(err);
                console.log(err.toString());
                // TODO: Notify User of error
            });
    }
};


export const getAllTeamMembers = (teamId) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getTeamMembers/${teamId}`, {headers})
            .then(res => {
                console.log("Success");
                console.log(res);
                dispatch(getTeamMembers(res.data.tickets));
            })
            .catch(err => {
                console.log("Failed to get team members");
                console.log(err);
                console.log(err.toString());
            });
    }
};

export const getAllSprints = (teamId) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getAllSprints/${teamId}`, {headers})
            .then(res => {
                console.log("Successfully got sprints");
                console.log(res.data.tickets);
                dispatch(getSprints(res.data.tickets));
            })
            .catch(err => {
                console.log("Failed to get sprints");
                console.log(err);
                console.log(err.toString());
            });
    }
};



export const getTotalPointsForUser = (userId, sprintNumber) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getTotalPointsForUser/${userId}/${sprintNumber}`, {headers})
            .then(res => {
                console.log("Successfully got points");
                console.log(res);
                dispatch(getUserPoints(res.data.sum, userId));
            })
            .catch(err => {
                console.log("Failed to get sprints");
                console.log(err);
                console.log(err.toString());
            });
    }
};


export const getNumOfUserWithTickets = (sprintNumber) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getNumOfUsersWithTickets/${sprintNumber}`, {headers})
            .then(res => {
                console.log("Successfully got num og users");
                console.log(res);
                dispatch(updateNumOfUsers(res.data.sum));
            })
            .catch(err => {
                console.log("Failed to get num of use");
                console.log(err);
                console.log(err.toString());
            });
    }
};


export const getAvgPoints = (sprintNumber) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getAvgPoints/${sprintNumber}`, {headers})
            .then(res => {
                console.log("Successfully got avg. points");
                console.log(res);
                dispatch(updateUserPoints(res.data.sum));
            })
            .catch(err => {
                console.log("Failed to get avg. points");
                console.log(err);
                console.log(err.toString());
            });
    }
};

export const getAllTeams = (userId) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/board/getAllTeams/${userId}`, {headers})
            .then(res => {
                console.log("Successfully got all teams");
                console.log(res);
                dispatch(updateAllTeams(res.data.teams));
            })
            .catch(err => {
                console.log("Failed to get get all teams");
                console.log(err);
                console.log(err.toString());
                dispatch(failedToLoadTeams(err))
            });
    }
};

export const updateAllTeams = (teams) => {
    return {
        type: 'GET_ALL_TEAMS',
        state: teams
    }
};

export const failedToLoadTeams = (error) => {
    return {
        type: 'FAILED_TO_LOAD_TEAMS',
        state: error
    }
}

export const updateUserPoints = (num) => {
    return {
        type: 'GET_AVG_POINTS',
        state: num
    }
};

export const updateNumOfUsers = (num) => {
    return {
        type: 'GET_NUM_MEMBERS',
        state: num
    }
};

export const getTeamMembers = (teamIds) => {
    return {
        type: 'GET_TEAM_MEMBERS',
        state: teamIds
    }
};

export const getUserPoints = (points, userId) => {
    return {
        type: 'GET_USERS_POINTS',
        state: points,
        userId: userId
    }
};

export const getSprints = (sprints) => {
    return {
        type: 'GET_SPRINTS',
        state: sprints
    }
};

export const updateTicketByProgress = (tickets, status, userId) => {
    if (status === 'backlog') {
        console.log(tickets);
    }
    if (status === idMappedToStatus.BACKLOG) {
        return {
            type: 'GET_BACKLOG_TICKETS',
            state: tickets,
            userId: userId
        }
    } else if (status === idMappedToStatus.PAUSED) {
        return {
            type: 'GET_PAUSED_TICKETS',
            state: tickets,
            userId: userId
        }

    } else if (status === idMappedToStatus.DONE) {
        return {
            type: 'GET_DONE_TICKETS',
            state: tickets,
            userId: userId
        }

    } else if (status === idMappedToStatus.IN_PROGRESS) {
        return {
            type: 'GET_IN_PROGRESS_TICKETS',
            state: tickets,
            userId: userId
        }

    } else if (status === idMappedToStatus.IN_REVIEW) {
        return {
            type: 'GET_IN_REVIEW_TICKETS',
            state: tickets,
            userId: userId
        }

    }
}