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
                console.log("failed");
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
                console.log("Success");
                dispatch(updateTicketByProgress(res.data.tickets, status));
            })
            .catch(err => {
                console.log("failed");
                console.log(err);
                console.log(err.toString());
                // TODO: Notify User of error
            });
    }
};

export const updateTicketByProgress = (tickets, status) => {
    console.log(tickets);
    console.log(status);
    if (status === idMappedToStatus.BACKLOG) {
        return {
            type: 'GET_BACKLOG_TICKETS',
            state: tickets
        }
    } else if (status === idMappedToStatus.PAUSED) {
        return {
            type: 'GET_PAUSED_TICKETS',
            state: tickets
        }

    } else if (status === idMappedToStatus.DONE) {
        return {
            type: 'GET_DONE_TICKETS',
            state: tickets
        }

    } else if (status === idMappedToStatus.IN_PROGRESS) {
        return {
            type: 'GET_IN_PROGRESS_TICKETS',
            state: tickets
        }

    } else if (status === idMappedToStatus.IN_REVIEW) {
        return {
            type: 'GET_IN_REVIEW_TICKETS',
            state: tickets
        }

    }
}