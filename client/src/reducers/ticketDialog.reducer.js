const ticketDialogState = {
    open: false
}

const ticketDialogReducer = (state = ticketDialogState, action) => {
    if (action.type === 'CLOSE_TICKET_DIALOG') {
        return {open: false};
    }
    else if (action.type === 'OPEN_TICKET_DIALOG') {
        return {open: true};
    }
    return state;
}

export default ticketDialogReducer;