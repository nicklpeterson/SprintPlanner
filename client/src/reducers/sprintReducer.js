const sprintReducer = (state = {}, action) => {
    if (action.type === 'SET_SPRINT') {
        return action.sprint;
    }
    return state;
}

export default sprintReducer;