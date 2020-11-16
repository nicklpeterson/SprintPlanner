import {combineReducers} from "redux";
import userReducer from "./user.reducer";
import boardReducer from "./userBoard.reducer";
import profileReducer from "./profile.reducer";
import ticketReducer from "./ticket.reducer";

export default combineReducers({
    user: userReducer,
    board: boardReducer,
    profile: profileReducer,
    ticket: ticketReducer
});