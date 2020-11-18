import {makeStyles} from "@material-ui/core/styles";
import React from "react";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import {Redirect} from "react-router-dom";
import DeleteIcon from "@material-ui/icons/Delete";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    }
}));

export default function NavBar(props) {
    const classes = useStyles();
    const [anchorEl, setAnchorEl] = React.useState(null);
    const [redirect, setRedirect] = React.useState(null);
    const open = Boolean(anchorEl);
    const PROFILE_URI = '/profile';
    const BOARD_URI = '/board';
    const LOGIN_URI = '/login';
    const HOME_URI = '/';

    const handleMenu = (event) => {
        console.log('menu');
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const redirectToPath = path => {
        if (window.location.pathname !== path) {
            setRedirect(path);
        } else {
            handleClose();
        }
    }

    if (redirect) {
        return <Redirect to={redirect}/>
    }

    return (
        <AppBar position="static" className={classes.root}>
            <Toolbar>
                <IconButton
                    edge="start"
                    className={classes.menuButton}
                    color="inherit"
                    aria-label="menu"
                    onClick={handleMenu}>
                    <MenuIcon/>
                </IconButton>
                <Menu
                    id="menu-appbar"
                    anchorEl={anchorEl}
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                    }}
                    open={open}
                    onClose={handleClose}
                >
                    <MenuItem onClick={() => redirectToPath(HOME_URI)}>Home</MenuItem>
                    <MenuItem onClick={() => redirectToPath(LOGIN_URI)}>Log In</MenuItem>
                    <MenuItem onClick={() => redirectToPath(PROFILE_URI)}>Profile</MenuItem>
                    <MenuItem onClick={() => redirectToPath(BOARD_URI)}>Sprint Board</MenuItem>
                </Menu>
                <Typography variant="h6" className={classes.title}>
                    {props.text}
                </Typography>
                {props.buttons.map(button => button)}

            </Toolbar>
        </AppBar>
    )
}

NavBar.defaultProps = {
    text: '',
    buttons: []
}