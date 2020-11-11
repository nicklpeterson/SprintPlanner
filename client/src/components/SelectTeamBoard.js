import React, {useEffect} from "react";
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import Container from '@material-ui/core/Container';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import {useDispatch, useSelector} from "react-redux";
import {getAllTeams} from "../actions/userBoard.actions";
import TeamBoard from "./TeamBoard";


// TODO: CREATE HOC TO PASS THE TEAM ID, BECAUSE IT'S POSSIBLE TO HAVE MULTIPLE TEAMS


export default function SelectTeamBoard() {
    const dispatch = useDispatch();
    const allTeams = useSelector(state => state.board.allTeams);
    const [team, setTeam] = React.useState('');

    // used: https://stackoverflow.com/questions/47240564/node-js-jwt-get-current-user to figure out how to decode jwt tokens
    function getCurrentUserId (){
        const tokenParts = localStorage.token.split('.');
        const encodedPayload = tokenParts[1];
        const rawPayload = atob(encodedPayload);
        const user = JSON.parse(rawPayload);
        return JSON.parse(user.sub).id;
    }


    useEffect(() => {
        const fetchAllTeams = () => {
            try {
                const userId = getCurrentUserId();
                dispatch(getAllTeams(userId));
            } catch (e) {
                console.log(e);
                console.log("Unable to load teams");
            }
        };
        fetchAllTeams();
    }, []);


    const handleChange = (event) => {
        setTeam(event.target.value);
    };

    return (
        <Container style={{marginBottom: 100}}>
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
    )

}