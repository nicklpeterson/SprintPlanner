const initialState = { username: undefined, name: undefined, skills: [], tickets: [] };

const profileReducer = (state = initialState, action) => {
    if (action.type === 'ADD_SKILL') {
        return {...state, skills: [...state.skills, {key: action.payload.description, description: action.payload.description}]}
    } else if (action.type === 'REMOVE_SKILL') {
        const newChipArray = state.skills.filter((chip) => chip.key !== action.payload.key)
        return {...state, skills: newChipArray}
    } else if (action.type === 'GET_SKILLS') {
        return {...state, skills: action.payload.map((skill, index) => {
            return {key: index, description: skill};
            })}
    } else if (action.type === 'GET_USER_DETAILS') {
        return {...state, username: action.payload.username, name: action.payload.name};
    } else if (action.type === 'GET_ASSIGNED_TICKETS') {
        return {...state, tickets: action.payload ? action.payload : []};
    }
    return state;
};

export default profileReducer;