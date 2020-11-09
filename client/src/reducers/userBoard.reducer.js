const initialState = { backlogTickets: [], pausedTickets: [], doneTickets: [], inReviewTickets: [], inProgressTickets: []}

const boardReducer = (state = initialState, action) => {
    if (action.type === 'GET_BACKLOG_TICKETS') {
        return {...state, backlogTickets: action.state};
    } else if (action.type === 'GET_DONE_TICKETS') {
        return {...state, doneTickets: action.state}
    } else if (action.type === 'GET_PAUSED_TICKETS') {
        return {...state, pausedTickets: action.state}
    } else if (action.type === 'GET_IN_REVIEW_TICKETS') {
        return {...state, inReviewTickets: action.state}
    } else if (action.type === 'GET_IN_PROGRESS_TICKETS') {
        return {...state, inProgressTickets: action.state}
    }

    return state;
};

export default boardReducer;