const initialState = { username: undefined, name: undefined, manager: false, manages: [], teams: [], skills: [], tickets: [] };

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
        return {...state, username: action.payload.user.username, name: action.payload.user.name, manager: action.payload.user.manager};
    } else if (action.type === 'GET_ASSIGNED_TICKETS') {
        return {...state, tickets: action.payload ? action.payload : []};
    } else if (action.type === 'ADD_TEAM') {
        return {...state, manages: [...state.manages, Object.assign(action.payload.team, { sprintLoad: 0 })]}
    } else if (action.type === 'JOIN_TEAM') {
        return {...state, teams: [...state.teams, action.payload.team]}
    } else if (action.type === 'GET_TEAMS') {
        return {...state, teams: [...state.teams, ...action.payload]}
    } else if (action.type === 'GET_MANAGED_TEAMS') {
        return {...state, manages: [...state.manages, ...action.payload]}
    }
    return state;
};

export default profileReducer;