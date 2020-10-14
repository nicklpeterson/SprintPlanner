import React from "react";
import "../styles/home.css";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import {makeStyles} from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import CssBaseline from "@material-ui/core/CssBaseline";

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    header: {
        fontWeight: 'bold'
    }
}));

export default function Home() {
    const classes = useStyles();

    return (
        <Container component="main" maxWidth="xs" className="Home">
            <CssBaseline />
            <div className={classes.paper}>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <div className="lander">
                                <h1>Sprint Planner</h1>
                                <p>A project management app</p>
                            </div>
                        </Grid>
                        <Grid item>
                            <Link href="/login" variant="body2" className={"login-link"}>
                                Sign In
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link href="/register" variant="body2" className={"register-link"}>
                                Sign Up
                            </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>
        </Container>
    );
}