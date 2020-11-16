// used: https://stackoverflow.com/questions/47240564/node-js-jwt-get-current-user to figure out how to decode jwt tokens
export const getCurrentUserId = () => {
    const tokenParts = localStorage.token.split('.');
    const encodedPayload = tokenParts[1];
    const rawPayload = atob(encodedPayload);
    const user = JSON.parse(rawPayload);
    return JSON.parse(user.sub).id;
}