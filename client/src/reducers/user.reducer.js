const userReducer = (user = {registrationSuccessFlag: false, registrationFailedFlag: false}, action) => {
    if (action.type === 'UPDATE_USER') {
        return action.user;
    }
    return user;
}

export default userReducer;