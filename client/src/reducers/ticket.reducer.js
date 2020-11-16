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
    console.log(action);
    if (action.type === 'GET_TICKET_BY_ID') {
        console.log(action.state);
        return action.state;
    } else {
        return {...state}
    }
};

export default ticketReducer;