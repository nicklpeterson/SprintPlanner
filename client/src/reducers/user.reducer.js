const initialUser = {
    registrationSuccessFlag: false,
    registrationFailedFlag: false,
    loginSuccessFlag: false,
    loginFailedFlag: false
}

const userReducer = (user = initialUser, action) => {
    if (action.type === 'UPDATE_USER') {
        return Object.assign({}, user, action.user);
    }
    return user;
}

export default userReducer;