import React, {useEffect} from "react";
import Typography from '@material-ui/core/Typography';
import Select from '@material-ui/core/Select';
import Grid from '@material-ui/core/Grid';
import MenuItem from '@material-ui/core/MenuItem';
import Container from '@material-ui/core/Container';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import {useDispatch, useSelector} from "react-redux";
import {getAllSprints, getAllTeamMembers, getNumOfUserWithTickets, getAvgPoints} from "../actions/userBoard.actions";
import UserBoard from "./UserBoard";


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
            try {
                dispatch(getAllTeamMembers(teamId));
                dispatch(getAllSprints(teamId));
            } catch (e) {
                console.log(e);
                console.log("Unable to load teamMembers");
            }
        };
        fetchTeamMembersAndSprints();
    }, []);

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

    return (
        <div style={{marginBottom: 100}}>
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
                    <Typography component="h6" variant="button">Average Number of Points: {getAveragePoints()}</Typography>
                </Grid>}
                {currentSprint &&
                <Grid item>
                    <Typography style={{marginLeft: 30}} component="h6" variant="button">Number of Members with Tickets: {getNumberOfUsersWithTickets()} </Typography>
                </Grid>
                }
            </Grid>
            {currentSprint && teamMembers.map((tm) =>  <UserBoard key={tm.id} userId={tm.id} sprintId={currentSprint} username={tm.username} />  )}
        </div>
    )

}