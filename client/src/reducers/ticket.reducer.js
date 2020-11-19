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
    points: 0,
    assigneeUsername: '',
    reporterUsername: '',
    canAssignTo: []
};

const ticketReducer = (state = initialTicket, action) => {
    if (action.type === 'GET_TICKET_BY_ID') {
        return {...state, ...action.state}
    } else if (action.type === 'GET_REPORTER_USERNAME') {
        return {...state, reporterUsername: action.state};
    } else if (action.type === 'GET_ASSIGNEE_USERNAME') {
        return {...state, assigneeUsername : action.state};
    } else if (action.type === 'UPDATE_ASSIGNEE') {
        return {...state, assigneeId : action.state};
    }else if (action.type === 'UPDATE_TICKET_POINTS') {
        return {...state, points : action.state};
    } else if (action.type === 'GET_AVAILABLE_MEMBERS') {
        return {...state, canAssignTo: action.state}
    } else {
        return {...state}
    }
};

export default ticketReducer;