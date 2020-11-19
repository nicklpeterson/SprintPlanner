import {combineReducers} from "redux";
import userReducer from "./user.reducer";
import boardReducer from "./userBoard.reducer";
import profileReducer from "./profile.reducer";
import ticketDialogReducer from "./ticketDialog.reducer";
import sprintReducer from "./sprintReducer";
import ticketReducer from "./ticket.reducer";

export default combineReducers({
    user: userReducer,
    board: boardReducer,
    profile: profileReducer,
    ticketDialog: ticketDialogReducer,
    sprint: sprintReducer,
    ticket: ticketReducer
});