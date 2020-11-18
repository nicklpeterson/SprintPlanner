const sprintReducer = (state = {sprintNumber: null}, action) => {
    console.log(action);
    if (action.type === 'SET_SPRINT') {
        return {sprintNumber: action.sprintNumber}
    }
    return state;
}

export default sprintReducer;