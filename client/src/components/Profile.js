import React, {useEffect} from "react";
import "../styles/home.css";
import Container from "@material-ui/core/Container";
import CssBaseline from "@material-ui/core/CssBaseline";
import Grid from "@material-ui/core/Grid";
import Avatar from "@material-ui/core/Avatar";
import Typography from "@material-ui/core/Typography";
import {makeStyles} from "@material-ui/core/styles";
import Chip from "@material-ui/core/Chip";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import TextField from "@material-ui/core/TextField";
import AddIcon from '@material-ui/icons/Add';
import IconButton from "@material-ui/core/IconButton";
import {useDispatch, useSelector} from "react-redux";
import {addSkill, addTeam, addToTeam, removeSkill} from "../actions/profile.actions";
import axios from "axios";

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        marginBottom: theme.spacing(4)
    },
    profileDetails: {
        marginBottom: theme.spacing(4)
    },
    skills: {
        '& > *': {
            margin: theme.spacing(0.5),
            backgroundColor: '#1d5cc1'
        },
    },
    large: {
        width: theme.spacing(15),
        height: theme.spacing(15),
    },
    high: {
        backgroundColor: '#c199a8'
    },
    medium: {
        backgroundColor: '#a8c199'
    },
    low: {
        backgroundColor: '#99a8c1'
    }
}));

export default function Profile() {
    const API_URL = process.env.API_URL;

    const classes = useStyles();
    const dispatch = useDispatch();

    const [newChip, setNewChip] = React.useState('');
    const [newTeam, setNewTeam] = React.useState('');
    const [newJoinTeam, setNewJoinTeam] = React.useState('');
    const [profilePic, setProfilePic] = React.useState('');
    const profile = useSelector(state => state.profile);

    const createSkill = () => {
        event.preventDefault();
        dispatch(addSkill(newChip));
    }

    const createTeam = () => {
        event.preventDefault();
        dispatch(addTeam(newTeam));
    }

    const joinTeam = () => {
        event.preventDefault();
        dispatch(addToTeam(newJoinTeam));
    }

    const deleteSkill = (deleteChip) => () => {
        dispatch(removeSkill(deleteChip));
    };

    useEffect(() => {
        const headers = {"Content-Type": "application/json"}
        axios.get(API_URL + "/users/skills", {headers})
            .then(res => dispatch({type: "GET_SKILLS", payload: res.data.skills}))
            .catch(err => {
                console.error(err);
            });
    }, []);

    useEffect(() => {
        const headers = {"Content-Type": "application/json"}
        axios.get(API_URL + "/users/details", {headers})
            .then(res => {
                dispatch({type: "GET_USER_DETAILS", payload: res.data});
                return res;
            })
            .then(res => {
                axios.get(API_URL + "/users/profilepic/" + res.data.user.username, {headers})
                    .then(resolve => {
                        setProfilePic("data:image/png;base64," + resolve.data.pic);
                    });
                axios.get(API_URL + "/ticket/assigned/" + res.data.user.username, {headers})
                    .then(res => {
                        dispatch({type: "GET_ASSIGNED_TICKETS", payload: res.data.tickets});
                    });
                axios.get(API_URL + "/team", {headers})
                    .then(res => {
                        dispatch({type: "GET_TEAMS", payload: res.data.teams});
                    });
                axios.get(API_URL + "/team/managed", {headers})
                    .then(res => {
                        dispatch({type: "GET_MANAGED_TEAMS", payload: res.data.teams});
                    })
            })
            .catch(err => {
                console.error(err);
            });
    }, []);

    return (
        <Container component="main" maxWidth="sm" className="Profile">
            <CssBaseline/>
            <div className={classes.paper}>
                <Avatar alt="profile pic" className={classes.large} src={profilePic}/>
                <Typography variant="h3">
                    {profile.username}
                </Typography>
                <Typography variant="h6">
                    {profile.name}
                </Typography>
            </div>
            <Card className={classes.profileDetails}>
                <CardContent>
                    <Grid container spacing={1}>
                        <Grid item xs={12}>
                            <Typography gutterBottom variant="h6">
                                Tickets
                            </Typography>
                        </Grid>
                        {profile.tickets.map((ticket) => {
                            const severityClass = classes[ticket.severity]
                            return (
                                <Grid item>
                                    <Card>
                                        <CardContent className={severityClass}>
                                            <Typography gutterBottom>
                                                {ticket.ticketTitle + " (" + ticket.severity + ")"}
                                            </Typography>
                                            <Typography>
                                                {"Status: " + ticket.status}
                                            </Typography>
                                            <Typography>
                                                {"Date Issued: " + ticket.dateIssue}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                </Grid>
                            );
                        })}
                    </Grid>
                </CardContent>
            </Card>
            {profile.manager ?
                <Card className={classes.profileDetails}>
                    <CardContent>
                        <Grid container spacing={1}>
                            <Grid item xs={12}>
                                <Typography gutterBottom variant="h6">
                                    Teams Managed
                                </Typography>
                            </Grid>
                            {profile.manages.map((team) => {
                                return (
                                    <Grid item>
                                        <Card>
                                            <CardContent>
                                                <Typography gutterBottom variant="h6">
                                                    {team.organization.name}
                                                </Typography>
                                                <Typography gutterBottom>
                                                    {team.name /* TODO: add team logo */}
                                                </Typography>
                                                <Typography variant="body2">
                                                    Total sprint load: {team.sprintLoad}
                                                </Typography>
                                            </CardContent>
                                        </Card>
                                    </Grid>
                                );
                            })}
                        </Grid>
                        <form style={{paddingTop: 20}} className={classes.root} noValidate autoComplete="off"
                              onSubmit={createTeam}>
                            <TextField
                                id="filled-basic"
                                label="Create a new team"
                                onChange={(event) => setNewTeam(event.target.value)}
                            />
                            <IconButton
                                type="button"
                                onClick={createTeam}
                            >
                                <AddIcon/>
                            </IconButton>
                        </form>
                    </CardContent>
                </Card>
                : <Card className={classes.profileDetails}>
                    <CardContent>
                        <Grid container spacing={1}>
                            <Grid item xs={12}>
                                <Typography gutterBottom variant="h6">
                                    Skills
                                </Typography>
                            </Grid>
                            <Grid item className={classes.skills}>
                                {profile.skills.map((skill) => {
                                    return (
                                        <Chip key={skill.key} label={skill.description} onDelete={deleteSkill(skill)}
                                              color="primary"/>
                                    );
                                })}
                            </Grid>
                            <Grid item xs={12}>
                                <form className={classes.root} noValidate autoComplete="off" onSubmit={createSkill}>
                                    <TextField
                                        id="filled-basic"
                                        label="Add a skill"
                                        onChange={(event) => setNewChip(event.target.value)}
                                    />
                                    <IconButton
                                        type="button"
                                        onClick={createSkill}
                                    >
                                        <AddIcon/>
                                    </IconButton>
                                </form>
                            </Grid>
                        </Grid>
                    </CardContent>
                </Card>
            }
            <Card className={classes.profileDetails}>
                <CardContent>
                    <Grid container spacing={1}>
                        <Grid item xs={12}>
                            <Typography gutterBottom variant="h6">
                                Teams Joined
                            </Typography>
                        </Grid>
                        {profile.teams.map((team) => {
                            return (
                                <Grid item>
                                    <Card>
                                        <CardContent>
                                            <Typography gutterBottom variant="h6">
                                                {team.organization.name}
                                            </Typography>
                                            <Typography gutterBottom>
                                                {team.name /* TODO: add team logo */}
                                            </Typography>
                                            <Typography variant="body2">
                                                Total sprint load: {team.sprintLoad}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                </Grid>
                            );
                        })}
                    </Grid>
                    <form style={{paddingTop: 20}} className={classes.root} noValidate autoComplete="off"
                          onSubmit={joinTeam}>
                        <TextField
                            id="filled-basic"
                            label="Join an existing team"
                            onChange={(event) => setNewJoinTeam(event.target.value)}
                        />
                        <IconButton
                            type="button"
                            onClick={joinTeam}
                        >
                            <AddIcon/>
                        </IconButton>
                    </form>
                </CardContent>
            </Card>
        </Container>
    );
}