import React from "react";
import { Droppable, Draggable } from 'react-beautiful-dnd';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Chip from '@material-ui/core/Chip';
import DeleteIcon from "@material-ui/icons/Delete";


// followed the react-beautiful-dnd example to create the board:
// https://codesandbox.io/s/ql08j35j3q?file=/index.js

export default function BoardColumn({ colName, items, colId }) {
    const GRID_WIDTH = 16;

    const getListStyle = isDraggingOver => ({
        background: isDraggingOver ? 'lightblue' : '#f9f9f9',
        padding: GRID_WIDTH,
        marginLeft: 5,
        marginRight: 5,
        width: 200,
        minHeight: 200,
        borderRadius: 5
    });

    const getItemStyle = (isDragging, draggableStyle) => ({
        userSelect: 'none',
        padding: GRID_WIDTH * 2,
        paddingBottom: GRID_WIDTH,
        margin: `0 0 ${GRID_WIDTH}px 0`,
        background: isDragging ? 'lightgreen' : 'white',

        ...draggableStyle
    });

    return (
        <div>
            <Droppable droppableId={colId} type="COLUMN" direction="horizontal">
                {(provided, snapshot) => (
                    <div ref={provided.innerRef} style={getListStyle(snapshot.isDraggingOver)}>
                        <Typography variant="button" component="h6" align="center" gutterBottom={true}>{colName}</Typography>
                        {items.map((item, index) => (
                            <Draggable draggableId={item.ticketId} key={item.ticketId} index={index}>
                                {(provided, snapshot) => (
                                    <div ref={provided.innerRef}
                                         {...provided.draggableProps}
                                         {...provided.dragHandleProps}
                                         style={getItemStyle(
                                             snapshot.isDragging,
                                             provided.draggableProps.style
                                         )}>
                                        <Grid container direction="column">
                                            <Grid item><Typography component="overline" gutterBottom={true}> {item.ticketTitle} </Typography></Grid>
                                            <Grid item xs={3} align="left"><DeleteIcon/></Grid>
                                            <Grid item xs={3} align="right"><Chip size="small" label={item.points} /></Grid>
                                        </Grid>
                                    </div>
                                )}
                            </Draggable>
                        ))}
                        {provided.placeholder}
                    </div>
                )}
            </Droppable>
        </div>
    )

}