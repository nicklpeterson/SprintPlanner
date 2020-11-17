import React, {useEffect} from "react";
import Typography from '@material-ui/core/Typography';
import Select from '@material-ui/core/Select';
import Grid from '@material-ui/core/Grid';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import {useDispatch, useSelector} from "react-redux";
import {
    getAllSprints,
    getAllTeamMembers,
    getNumOfUserWithTickets,
    getAvgPoints,
    getTicketsByProgress, getTotalPointsForUser
} from "../actions/userBoard.actions";
import UserBoard from "./UserBoard";
import {idMappedToStatus} from "../constants";


// TODO: CREATE HOC TO PASS THE TEAM ID, BECAUSE IT'S POSSIBLE TO HAVE MULTIPLE TEAMS


export default function TeamBoard({ teamId, teamName }) {
    const dispatch = useDispatch();
    const teamMembers = useSelector(state => state.board.teamMembers);
    const sprints = useSelector(state => state.board.sprints);
    const avgPoints = useSelector(state => state.board.avgPoints);
    const usersWithTickets = useSelector(state => state.board.usersWithTickets);
    const [currentSprint, setCurrentSprint] = React.useState('');


    useEffect(() => {
        const fetchTeamMembersAndSprints = () => {
            setCurrentSprint("");
            try {
                dispatch(getAllTeamMembers(teamId));
                dispatch(getAllSprints(teamId));
            } catch (e) {
                console.log(e);
                console.log("Unable to load teamMembers");
            }
        };
        fetchTeamMembersAndSprints();
    }, [teamId]);

    const handleChange = (event) => {
        setCurrentSprint(event.target.value);
    };

    const getAveragePoints = () => {
        dispatch(getAvgPoints(currentSprint));
        return avgPoints;
    };

    const getNumberOfUsersWithTickets = () => {
        dispatch(getNumOfUserWithTickets(currentSprint));
        return usersWithTickets;
    };

    const renderUserBoards = (sprintId, teamMembers) => {
        return teamMembers.map((tm) => {
            dispatch(getTicketsByProgress(tm.id, sprintId, idMappedToStatus.BACKLOG));
            dispatch(getTicketsByProgress(tm.id, sprintId, idMappedToStatus.PAUSED));
            dispatch(getTicketsByProgress(tm.id, sprintId, idMappedToStatus.IN_PROGRESS));
            dispatch(getTicketsByProgress(tm.id, sprintId, idMappedToStatus.IN_REVIEW));
            dispatch(getTicketsByProgress(tm.id, sprintId, idMappedToStatus.DONE));
            dispatch(getTotalPointsForUser(tm.id, sprintId));

            return <UserBoard key={tm.id} userId={tm.id} sprintId={currentSprint} username={tm.username} />

        })
    };

    return (
        <div style={{marginBottom: 100}}>
            <Typography align="left" component="h4" variant="h4" gutterBottom={true}>Team {teamName}</Typography>
            <FormControl>
                <InputLabel>Sprint Number</InputLabel>
                <Select
                    value={currentSprint}
                    onChange={handleChange}
                    style={{
                        minWidth: 150,
                        alignContent: 'center', marginBottom: 30
                    }}>
                    {sprints.map((sprint, index) => <MenuItem key={index} value={sprint.sprintNumber}>{sprint.sprintNumber}</MenuItem>)}
                </Select>
            </FormControl>
            <Grid container direction="row">
                {currentSprint &&
                <Grid item>
                    <Typography component="h6" variant="button">Average Number of Points: {getAveragePoints() ?? 0}</Typography>
                </Grid>}
                {currentSprint &&
                <Grid item>
                    <Typography style={{marginLeft: 30}} component="h6" variant="button">Number of Members with Tickets: {getNumberOfUsersWithTickets() ?? 0} </Typography>
                </Grid>
                }
            </Grid>
            {currentSprint && renderUserBoards(currentSprint, teamMembers)}
        </div>
    )

}