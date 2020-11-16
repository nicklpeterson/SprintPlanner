const initialTicket = {
    ticketId: '',
    ticketTitle: '',
    severity: '',
    status: '',
    projectId: '',
    sprintNumber: 0,
    dateIssue: '',
    creatorId: '',
    assigneeId: '',
    points: 0
};

const ticketReducer = (state = initialTicket, action) => {
    if (action.type === 'GET_TICKET_BY_ID') {
        return action.state;
    } else {
        return {...state}
    }
};

export default ticketReducer;