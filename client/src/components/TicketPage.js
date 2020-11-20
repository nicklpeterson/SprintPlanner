import React, {useEffect} from "react";
import Typography from '@material-ui/core/Typography';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import Grid from '@material-ui/core/Grid';
import Modal from '@material-ui/core/Modal';
import Divider from '@material-ui/core/Divider';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import {useDispatch, useSelector} from "react-redux";
import { useParams } from "react-router";
import TextField from '@material-ui/core/TextField';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';




import { getTicketInfo, getUsername, updateAssignee, updatePoints, getMembersCanBeAssignedTo } from "../actions/ticket.actions";
import { statuses} from "../constants";
import Redirect from "react-router-dom/es/Redirect";


export default function TicketPage() {
    const { id } = useParams();
    const dispatch = useDispatch();
    const currentTicket = useSelector(state => state.ticket);
    const canAssignTo = useSelector(state => state.ticket.canAssignTo);
    const sprint = useSelector(state => state.sprint);
    const [shouldFetchUsernames, setShouldFetchUsernames] = React.useState(true);
    const [shouldUpdateAssignee, setShouldUpdateAssignee] = React.useState(false);
    const [shouldUpdatePoints, setShouldUpdatePoints] = React.useState(false);
    const [newPoints, setNewPoints] = React.useState("");
    const [newAssignee, setNewAssignee] = React.useState("");
    const [error, setError] = React.useState(false);
    const [success, setSuccess] = React.useState(false);

    const assigneeUsername = useSelector(state => state.ticket.assigneeUsername);
    const reporterUsername = useSelector(state => state.ticket.reporterUsername);


    useEffect(() => {
        const fetchTicketInfo = () => {
            try {
                dispatch(getTicketInfo(id));
                dispatch(getMembersCanBeAssignedTo(id))

            } catch (e) {
                console.log(e);
                console.log("Unable to load ticket information");
            }
        };
        fetchTicketInfo();
    }, [newPoints, newAssignee]);

    const handleAssigneeModal = (e) => {
        e.preventDefault();
        setShouldUpdateAssignee(true);
    };

    const handlePointsModal = (e) => {
        e.preventDefault();
        setShouldUpdatePoints(true);
    };

    const handleUpdatePoints = (e) => {
        e.preventDefault();

        if (!isNum()) {
            setError(true);
        } else {
            dispatch(updatePoints(parseInt(newPoints), currentTicket.ticketId));
            setSuccess(true);
        }

        setShouldUpdatePoints(false);
    };

    const handleUpdateAssignee = (e) => {
        e.preventDefault();

        if (newAssignee === "") {
            setError(true);
        } else {
            dispatch(updateAssignee(newAssignee, currentTicket.ticketId));
            dispatch(getUsername(newAssignee, true));
            setSuccess(true);
        }

        setShouldUpdateAssignee(false);
    };


    const getModalStyle = () => {
        return {
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            position: 'absolute',
            backgroundColor: 'white',
            border: '2px solid #000',
            width: 400,
            padding: 50,
        }
    };

    const isNum = () => {
        return newPoints.match(/^[0-9]+$/) != null;
    };


    // Hacky fix to get redux to update the usernames
    if (shouldFetchUsernames && !!currentTicket.assigneeId) {
        setShouldFetchUsernames(false);
        if (!!currentTicket.assigneeId) {
            dispatch(getUsername(currentTicket.assigneeId, true));
        }

        if (!!currentTicket.creatorId) {
            dispatch(getUsername(currentTicket.creatorId, false));
        }
    }

    if (!sprint.number) {
        console.log(sprint);
        return <Redirect to={'/board'}/>;
    }

    return (
        <Container style={{marginTop: 50, marginBottom: 100}}>
                <Typography component="h4" variant="h4" gutterBottom> {currentTicket.ticketTitle} </Typography>

            <Grid container direction="row">
                <Grid item xs={9} style={{paddingRight: 60}}>
                    <Grid direction="column">
                        <Grid container direction="row">
                            <Grid item xs={6}>
                                <Typography style={{fontWeight:'bold'}}>Project:</Typography>
                                <Typography>PROJECT</Typography>
                            </Grid>
                            <Grid item item xs={6}>
                                <Typography style={{fontWeight:'bold'}}>Status:</Typography>
                                <Typography>{statuses[currentTicket.status]}</Typography>
                            </Grid>
                        </Grid>
                        <Grid container direction="row">
                            <Grid item xs={6}>
                                <Typography style={{fontWeight:'bold'}}>Severity:</Typography>
                                <Typography gutterBottom>{currentTicket.severity}</Typography>
                            </Grid>
                        </Grid>

                        <Divider style={{marginTop: 40, marginBottom: 40}} />

                    <Grid item>
                        <Typography style={{fontWeight:'bold'}}>Description</Typography>
                    </Grid>
                    <Grid item>
                        <Typography>{currentTicket.description}</Typography>
                    </Grid>
                    </Grid>
                </Grid>

                <Grid item xs={3}>
                <Grid container direction="column">
                    <Grid container direction="row">
                        <Grid item xs={4}>
                            <Typography style={{fontWeight:'bold'}}>Assignee: </Typography>
                        </Grid>
                        <Grid item>
                            <Typography>{assigneeUsername ?? 'NONE'}</Typography>
                            <Button color="primary" onClick={handleAssigneeModal} style={{paddingLeft: 0}}>Update assignee</Button>
                        </Grid>
                    </Grid>

                    <Grid container direction="row">
                        <Grid item item xs={4}>
                            <Typography style={{fontWeight:'bold'}}>Reporter:</Typography>
                        </Grid>
                        <Grid item>
                            <Typography>{reporterUsername ?? 'NONE'}</Typography>
                        </Grid>
                    </Grid>

                    <Grid container direction="row">
                        <Grid item xs={4}>
                            <Typography style={{fontWeight:'bold'}}>Points: </Typography>
                        </Grid>
                        <Grid item>
                            <Typography>{currentTicket.points}</Typography>
                            <Button color="primary" onClick={handlePointsModal} style={{paddingLeft: 0}}>Update points</Button>
                        </Grid>
                    </Grid>
                </Grid>

                </Grid>
                </Grid>
            <Modal
                open={shouldUpdateAssignee}
                onClose={() => setShouldUpdateAssignee(false)}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <Container style={getModalStyle()}>
                    <Typography> Select new assignee: </Typography>
                    <Select
                        value={newAssignee}
                        onChange={(e) => setNewAssignee(e.target.value)}
                        style={{
                            minWidth: 150,
                            marginBottom: 30
                        }}>
                        {canAssignTo.map((member, index) => <MenuItem key={member.id} value={member.id}>{member.username}</MenuItem>)}
                    </Select>
                    <Button variant="outlined" color="primary" onClick={handleUpdateAssignee}>Update Assignee</Button>
                </Container>
            </Modal>

            <Modal
                open={shouldUpdatePoints}
                onClose={() => {setShouldUpdatePoints(false)}}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <Container style={getModalStyle()}>
                    <form noValidate autoComplete="off">
                        <Grid container direction="col" style={{align:'center', alignContent:'center', alignItems: 'center'}}/>
                        <Grid item xs={10}>
                            <Typography> Choose new points amount: </Typography>
                        </Grid>
                        <Grid item xs={10}>
                                <Grid item>
                            <TextField label="New Points Amount"
                                       defaultValue=""
                                       onChange={(e) => setNewPoints(e.target.value)}
                                       style={{marginBottom: 15}}
                            />
                                    <Button variant="outlined" color="primary" onClick={handleUpdatePoints}>Update points</Button>
                                </Grid>
                        </Grid>
                        </form>
                </Container>
            </Modal>
            <Snackbar open={success} onClose={() => setSuccess(false)} autoHideDuration={3000}>
                <Alert severity="success">Successfully updated ticket.</Alert>
            </Snackbar>
            <Snackbar open={error} onClose={() => setError(false)}>
                <Alert severity="error">Unable to update ticket.</Alert>
            </Snackbar>
        </Container>
    )


}