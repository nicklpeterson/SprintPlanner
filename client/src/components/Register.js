import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {useDispatch, useSelector} from "react-redux";
import {registerUser, updateUser} from "../actions/user.actions";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Redirect from "react-router-dom/es/Redirect";

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
    },
}));

export default function Register() {
    const classes = useStyles();
    const dispatch = useDispatch();
    const [password, setPassword] = React.useState('');
    const [username, setUsername] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [organizationName, setOrganizationName] = React.useState('');
    const [isManager, setIsManager] = React.useState(false);
    const user = useSelector(state => state.user);

    const handleSubmit = (e) => {
        e.preventDefault();
        dispatch(registerUser(email, username, password, organizationName, isManager));
    }

    const setRegisterSuccessFlag = bool => {
        user.registrationSuccessFlag = bool;
        dispatch(updateUser(Object.assign({}, user, {registrationSuccessFlag: bool})));
    }

    const setRegisterFailWarning = bool => {
        user.registrationFailedFlag = bool;
        dispatch(updateUser(Object.assign({}, user, {registrationFailedFlag: bool})));
    }

    if (user.registrationSuccessFlag) {
        return <Redirect to="/login" />
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign Up
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
                                onChange={(e) => setUsername(e.target.value)}
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
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="organizationName"
                                label="Organization Name"
                                name="email"
                                autoComplete="email"
                                onChange={(e) => setOrganizationName(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        required
                                        name="isManager"
                                        onChange={(e) => setIsManager(e.target.checked)}
                                    />
                                }
                                label="I am a manager"
                            />
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={handleSubmit}
                    >
                        Sign Up
                    </Button>
                    <Grid container justify="flex-end">
                        <Grid item>
                            <Link href="/login" variant="body2">
                                Already have an account? Sign in
                            </Link>
                        </Grid>
                    </Grid>
                </form>
                <Snackbar open={user.registrationSuccessFlag} autoHideDuration={6000} onClose={() => setRegisterSuccessFlag(false)}>
                    <Alert onClose={() => setRegisterSuccessFlag(false)} severity="success">
                        Registration was successful!
                    </Alert>
                </Snackbar>
                <Snackbar open={user.registrationFailedFlag} autoHideDuration={6000} onClose={() => setRegisterFailWarning(false)}>
                    <Alert onClose={() => setRegisterFailWarning(false)} severity="error">
                        Oops, looks like you're already registered.
                    </Alert>
                </Snackbar>
            </div>
        </Container>
    );
}