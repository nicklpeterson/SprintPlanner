import React, {useEffect} from "react";
import Typography from '@material-ui/core/Typography';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import Grid from '@material-ui/core/Grid';
import Divider from '@material-ui/core/Divider';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import {useDispatch, useSelector} from "react-redux";
import { useParams } from "react-router";
import { getTicketInfo } from "../actions/ticket.actions";


export default function TicketPage() {
    const { ticketId } = useParams();
    const dispatch = useDispatch();
    const currentTicket = useSelector(state => state);

    useEffect(() => {
        const fetchTicketInfo = () => {
            console.log("USE PARAMS");
            console.log(ticketId);
            try {
                dispatch(getTicketInfo(ticketId));
            } catch (e) {
                console.log(e);
                console.log("Unable to load ticket information");
            }
        };
        fetchTicketInfo();
    }, []);


    console.log(currentTicket);


    return (
        <Container>
                <Typography component="h4" variant="h4" gutterBottom> TITLE </Typography>

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
                                <Typography>STATUS</Typography>
                            </Grid>
                        </Grid>
                        <Grid container direction="row">
                            <Grid item xs={6}>
                                <Typography style={{fontWeight:'bold'}}>Priority:</Typography>
                                <Typography gutterBottom>PRIORITY</Typography>
                            </Grid>
                            <Grid item item xs={6}>
                                <Typography style={{fontWeight:'bold'}}>References:</Typography>
                                <Typography gutterBottom >REFERENCES, GOING TO BE PILLS</Typography>
                            </Grid>
                        </Grid>

                        <Divider style={{marginTop: 40, marginBottom: 40}} />

                    <Grid item>
                        <Typography style={{fontWeight:'bold'}}>Description</Typography>
                    </Grid>
                    <Grid item>
                        <Typography>DESCRIPTION</Typography>
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
                            <Typography>ASSIGNEE</Typography>
                            <Button color="primary" style={{paddingLeft: 0}}>Update assignee</Button>
                        </Grid>
                    </Grid>

                    <Grid container direction="row">
                        <Grid item item xs={4}>
                            <Typography style={{fontWeight:'bold'}}>Reporter:</Typography>
                        </Grid>
                        <Grid item>
                            <Typography>REPORTER</Typography>
                        </Grid>
                    </Grid>

                    <Grid container direction="row">
                        <Grid item xs={4}>
                            <Typography style={{fontWeight:'bold'}}>Points: </Typography>
                        </Grid>
                        <Grid item>
                            <Typography>POINTS</Typography>
                            <Button color="primary" style={{paddingLeft: 0}}>Update points</Button>
                        </Grid>
                    </Grid>
                </Grid>

                </Grid>
                </Grid>
        </Container>
    )


}