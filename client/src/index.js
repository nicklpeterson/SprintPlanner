import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from "redux";
import reducers from './reducers/reducers';
import thunkMiddleware from 'redux-thunk'

ReactDOM.render(
    <Provider store={createStore(
        reducers,
        applyMiddleware(thunkMiddleware)
    )}>
        <App />
    </Provider>,
    document.getElementById('app')
);