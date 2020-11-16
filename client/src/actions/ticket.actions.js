import axios from 'axios';
import {updateAllTeams} from "./userBoard.actions";
const API_URL = process.env.API_URL;

export const getTicketInfo = (ticketId) => {
    const headers = {"Content-Type" : "application/json"};

    return dispatch => {
        axios.get(`${API_URL}/ticket/${ticketId}`, {headers})
            .then(res => {
                console.log("Successfully got the ticket info");
                console.log(res);
                dispatch(updateTicket(res.data.ticket));
            })
            .catch(err => {
                console.log("Failed to get the ticket");
                console.log(err);
                console.log(err.toString());
            });
    }
};

export const updateTicket = (ticket) => {
    return {
        type: 'GET_TICKET_BY_ID',
        state: ticket
    }
};