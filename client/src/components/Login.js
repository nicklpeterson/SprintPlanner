import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {useDispatch, useSelector} from "react-redux";
import {login, updateUser} from "../actions/user.actions";
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from "@material-ui/lab/Alert";

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    }
}));

export default function Login() {
    const classes = useStyles();
    const dispatch = useDispatch();
    const [password, setPassword] = React.useState('');
    const [username, setUsername] = React.useState('');
    const user = useSelector(state => state.user);

    const setLoginSuccessFlag = bool => {
        user.registrationSuccessFlag = bool;
        dispatch(updateUser(Object.assign({}, user, {loginSuccessFlag: bool})));
    }

    const setLoginFailFlag = bool => {
        user.registrationFailedFlag = bool;
        dispatch(updateUser(Object.assign({}, user, {loginFailedFlag: bool})));
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(login(username, password));
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign In
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                autoComplete="username"
                                onChange ={(e) => setUsername(e.target.value)}

                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                                onChange = {(e) => setPassword(e.target.value)}
                            />
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick = {handleSubmit}
                    >
                        Sign In
                    </Button>
                    <Grid container justify="flex-end">
                        <Grid item>
                            <Link href="/register" variant="body2">
                                Not registered? Sign Up
                            </Link>
                        </Grid>
                    </Grid>
                </form>
                <Snackbar open={user.loginSuccessFlag} autoHideDuration={6000} onClose={() => setLoginSuccessFlag(false)}>
                    <Alert onClose={() => setLoginSuccessFlag(false)} severity="success">
                        Login was successful!
                    </Alert>
                </Snackbar>
                <Snackbar open={user.loginFailedFlag} autoHideDuration={6000} onClose={() => setLoginFailFlag(false)}>
                    <Alert onClose={() => setLoginFailFlag(false)} severity="error">
                        Wrong username or password. Please try again.
                    </Alert>
                </Snackbar>
            </div>
        </Container>
    );
}