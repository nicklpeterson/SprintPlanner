import axios from "axios";
import {getTicketsByProgress} from "./userBoard.actions";
import {idMappedToStatus} from "../constants";
const API_URL = process.env.API_URL;

export const saveTicket = (data) => {
    console.log(data);
    const headers = {"Content-Type": "application/json"}
    return dispatch => {
        console.log('sending');
        axios.post(API_URL + "/ticket/new", JSON.stringify(data), {headers})
            .then(res => {
                console.log(res);
                dispatch(getTicketsByProgress(data.assigneeId, data.sprintNumber, data.projectId, idMappedToStatus.BACKLOG));
            })
            .catch(err => {
                console.error(err);
            });
    }
}

export const openTicketDialog = () => {
    return {
        type: 'OPEN_TICKET_DIALOG'
    }
}

export const closeTicketDialog = () => {
    return {
        type: 'CLOSE_TICKET_DIALOG'
    }
}