const initialState = { teamMembers: [], sprints: [], usersWithTickets: 0, avgPoints: 0, allTeams:[]};

const boardReducer = (state = initialState, action) => {
    if (!!action.userId && !state[action.userId]) {
        state[action.userId] = {backlogTickets: [], pausedTickets: [], doneTickets: [], inProgressTickets: [], inReviewTickets: [], points: 0};
    }

    // TODO: Refactor to swicth
    if (action.type === 'GET_BACKLOG_TICKETS') {
        return {...state, [action.userId] : { ... state[action.userId], backlogTickets: action.state}};
    } else if (action.type === 'GET_DONE_TICKETS') {
        return {...state, [action.userId] : { ... state[action.userId], doneTickets: action.state}};
    } else if (action.type === 'GET_PAUSED_TICKETS') {
        return {...state, [action.userId] : { ... state[action.userId], pausedTickets: action.state}};
    } else if (action.type === 'GET_IN_REVIEW_TICKETS') {
        return {...state, [action.userId] : { ... state[action.userId], inReviewTickets: action.state}};
    } else if (action.type === 'GET_IN_PROGRESS_TICKETS') {
        return {...state, [action.userId] : { ... state[action.userId], inProgressTickets: action.state}};
    } else if (action.type === 'GET_TEAM_MEMBERS') {
        return {...state, teamMembers: action.state}
    } else if (action.type === 'GET_SPRINTS') {
        return {...state, sprints: action.state}
    } else if (action.type === 'GET_USERS_POINTS') {
        return {...state, [action.userId] : { ... state[action.userId], points: action.state}};
    } else if (action.type === 'GET_AVG_POINTS') {
        return {...state , avgPoints: action.state};
    } else if (action.type === 'GET_NUM_MEMBERS') {
        return {...state, usersWithTickets: action.state};
    } else if (action.type === 'GET_ALL_TEAMS') {
        return {...state, allTeams: action.state};
    }

    return state;
};

export default boardReducer;