// used: https://stackoverflow.com/questions/47240564/node-js-jwt-get-current-user to figure out how to decode jwt tokens
const parseToken = () => {
    const tokenParts = localStorage.token.split('.');
    const encodedPayload = tokenParts[1];
    const rawPayload = atob(encodedPayload);
    const user = JSON.parse(rawPayload);
    return JSON.parse(user.sub);
};

export const getCurrentUserId = () => {
    return parseToken().id;
};

export const getCurrentOrg = () => {
    return parseToken().organization;
};