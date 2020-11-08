import {combineReducers} from "redux";
import userReducer from "./user.reducer";
import boardReducer from "./userBoard.reducer";

export default combineReducers({
    user: userReducer,
    board: boardReducer
});