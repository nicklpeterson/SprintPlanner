import React, {useEffect} from "react";
import { DragDropContext } from 'react-beautiful-dnd';
import BoardColumn from "./BoardColumn";
import Grid from '@material-ui/core/Grid';
import {useDispatch, useSelector} from "react-redux";
import {updateProgressStatus, getTicketsByProgress, getTotalPointsForUser} from "../actions/userBoard.actions";
import {idMappedToStatus, BACKLOG, DONE, IN_REVIEW, IN_PROGRESS, PAUSED} from "../constants";
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import Typography from '@material-ui/core/Typography';


// followed the react-beautiful-dnd example to create the board:
// https://codesandbox.io/s/ql08j35j3q?file=/index.js

// I moved up the groupby query for points to the team user board in order for it to have more significance
export default function UserBoard({userId, sprintId, username}) {
    const dispatch = useDispatch();
    const backlogItems = useSelector(state => state.board);
    const pausedItems = useSelector(state => state.board);
    const inProgressItems = useSelector(state => state.board);
    const inReviewItems = useSelector(state => state.board);
    const doneItems = useSelector(state => state.board);
    const points = useSelector(state => state.board);
    const [updateSuccess, setUpdateSuccess] = React.useState(false);
    const [hasError, setHasError] = React.useState(false);

    useEffect(() => {
        const fetchTickets = () => {
            try {
                dispatch(getTicketsByProgress(userId, sprintId, idMappedToStatus.BACKLOG));
                dispatch(getTicketsByProgress(userId, sprintId, idMappedToStatus.PAUSED));
                dispatch(getTicketsByProgress(userId, sprintId, idMappedToStatus.IN_PROGRESS));
                dispatch(getTicketsByProgress(userId, sprintId, idMappedToStatus.IN_REVIEW));
                dispatch(getTicketsByProgress(userId, sprintId, idMappedToStatus.DONE));
                dispatch(getTotalPointsForUser(userId, sprintId));

            } catch (e) {
                setHasError(true);
                console.log(e);
                console.log("Unable to load tickets");
            }
        };
        fetchTickets();
    }, []);

    const move = (source, destination, droppableSource, droppableDestination, ticketId) => {
        const [removed] = source.splice(droppableSource.index, 1);

        try {
            dispatch(updateProgressStatus(ticketId, droppableDestination.droppableId));
            destination.splice(droppableDestination.index, 0, removed);
            setUpdateSuccess(true);
            return {[droppableSource.droppableId]: source, [droppableDestination.droppableId]: destination};
        } catch {
            setHasError(true);
        }
    };

    const onDragEnd = (result) => {
        const { source, destination } = result;
        const idMappedToList = {
            "backlog": backlogItems[userId]?.backlogTickets ?? [],
            "paused": pausedItems[userId]?.pausedTickets ?? [],
            "inProgress": inProgressItems[userId]?.inProgressTickets ?? [],
            "inReview": inReviewItems[userId]?.inReviewTickets ?? [],
            "done": doneItems[userId]?.doneTickets ?? []
        };

        console.log("MAPPING");
        console.log(idMappedToList);

        // if it's dropped outside a list
        if (!destination || source.droppableId === destination.droppableId) {
            return;
        } else {
            move(
                idMappedToList[source.droppableId],
                idMappedToList[destination.droppableId],
                source,
                destination,
                result.draggableId
            );
        }

    };

    return (
        <div style={{marginTop: 40}}>
            <Grid container direction="row">
                <Typography variant="h5" gutterBottom={true} style={{marginLeft:5}}>{username}</Typography>
                <Typography variant="overline" gutterBottom={true} style={{marginLeft:15}}>{points[userId]?.points ?? 0} points</Typography>
            </Grid>
            <DragDropContext onDragEnd={onDragEnd}>
                <Grid container direction="row">
                    <Grid item>
                        <BoardColumn colName={BACKLOG} items={backlogItems[userId]?.backlogTickets ?? []} colId={idMappedToStatus.BACKLOG}/>
                    </Grid>
                    <Grid item>
                        <BoardColumn colName={PAUSED} items={pausedItems[userId]?.pausedTickets ?? []} colId={idMappedToStatus.PAUSED}/>
                    </Grid>
                    <Grid item>
                        <BoardColumn colName={IN_PROGRESS} items={inProgressItems[userId]?.inProgressTickets ?? []} colId={idMappedToStatus.IN_PROGRESS}/>
                    </Grid>
                    <Grid item>
                        <BoardColumn colName={IN_REVIEW} items={inReviewItems[userId]?.inReviewTickets ?? []} colId={idMappedToStatus.IN_REVIEW}/>
                    </Grid>
                    <Grid item>
                        <BoardColumn colName={DONE} items={doneItems[userId]?.doneTickets ?? []} colId={idMappedToStatus.DONE}/>
                    </Grid>
                </Grid>
                <Snackbar open={updateSuccess} onClose={() => setUpdateSuccess(false)} autoHideDuration={3000}>
                    <Alert severity="success">Successfully updated the database with new ticket status.</Alert>
                </Snackbar>
                <Snackbar open={hasError} onClose={() => setHasError(false)}>
                    <Alert severity="error">Error accessing the database.</Alert>
                </Snackbar>
            </DragDropContext>
        </div>
    );


}