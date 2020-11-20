import React, {useEffect} from "react";
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import Container from '@material-ui/core/Container';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import {useDispatch, useSelector} from "react-redux";
import {deleteSprint, getAllTeams, setTeam} from "../actions/userBoard.actions";
import TeamBoard from "./TeamBoard";
import NavBar from "./NavBar";
import Button from "@material-ui/core/Button";
import DeleteIcon from '@material-ui/icons/Delete';
import makeStyles from "@material-ui/core/styles/makeStyles";
import AddCircleIcon from '@material-ui/icons/AddCircle';
import AddTicketDialog from "./AddTicketDialog";
import {openTicketDialog} from "../actions/ticketDialog.actions";
import * as JwtParser from "../util/JwtParser";

const useStyles = makeStyles((theme) => ({
    button: {
        margin: theme.spacing(1),
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        margin: 'auto',
        width: 'fit-content',
    },
    formControl: {
        marginTop: theme.spacing(2),
        minWidth: 120,
    },
    formControlLabel: {
        marginTop: theme.spacing(1),
    }
}));

export default function SelectTeamBoard() {
    const classes = useStyles();
    const dispatch = useDispatch();
    const allTeams = useSelector(state => state.board.allTeams);
    const sprint = useSelector(state => state.sprint)
    const [team, setTeam] = React.useState('');

    useEffect(() => {
        const fetchAllTeams = () => {
            try {
                const userId = JwtParser.getUserId();
                dispatch(getAllTeams(userId));
            } catch (e) {
                console.log(e);
                console.log("Unable to load teams");
            }
        }
        fetchAllTeams();
    }, []);


    const handleChange = (event) => {
        setTeam(event.target.value);
    };

    const openAddTicketDialog = () => {
        dispatch(openTicketDialog());
    }

    const deleteCurrentSprint = () => {
        if (sprint.sprintNumber) {
            dispatch(deleteSprint(sprint));
        }
    }

    const navButtons = [
        <Button
            variant="contained"
            color="default"
            className={classes.button}
            startIcon={<AddCircleIcon />}
            onClick = {openAddTicketDialog}
        >
            Add Ticket
        </Button>,
        <Button
            variant="contained"
            color="secondary"
            className={classes.button}
            startIcon={<DeleteIcon />}
            onClick = {deleteCurrentSprint}
        >
            Delete Sprint
        </Button>
    ]

    const navBarText = team.name ? team.name : '';

    return (
        <React.Fragment>
            <NavBar text={navBarText} buttons={navButtons}/>
            <AddTicketDialog/>
            <Container style={{marginBottom: 100, marginTop: 25}}>
                <FormControl>
                    <InputLabel>Select Team</InputLabel>
                    <Select
                        value={team}
                        onChange={handleChange}
                        style={{
                            minWidth: 150,
                            marginBottom: 30
                        }}>
                        {allTeams.map((team, index) => <MenuItem key={index} value={team}>{team.name}</MenuItem>)}
                    </Select>
                </FormControl>
                {team && <TeamBoard teamId={team.teamId} teamName={team.name}/>}
            </Container>
        </React.Fragment>
    )
}