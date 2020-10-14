import React from "react";
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    navBar: {
        backgroundColor: '#0747a6'
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));

export default function Navigation() {
    const classes = useStyles();

    return (
        <AppBar position="static" className={classes.navBar}>
            <Toolbar>
                <Typography variant="h6" className={classes.title} href='/'>
                    Sprint Planner
                </Typography>
                <Button color="inherit" href='/login'>Login</Button>
                <Button color="inherit" href='/register'>Register</Button>
            </Toolbar>
        </AppBar>
    );
}