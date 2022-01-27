import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import LoginForm from './LogginForm';
import './App.css';

class App extends React.Component {
 
    render(){

        return(

            <Router> 
                <Routes>
                    <Route exact path = "/Login" element = { <LoginForm> </LoginForm>}> </Route>

                </Routes>
            </Router>

        );
    }

}

export default App;
