import React from "react";
import UserStore    from './UserStore';
import LoginForm    from './LogginForm';
import SubmitButton from './SubmitButton';
import { observer } from 'mobx-react'

class HomePage extends React.Component {

    async componentDidMount(){

        try{
    
          let res = await fetch('/isLoggedIn', {
    
              method: 'post',
              headers: {
    
                'Accept': 'application/json',
                'Content-Type' : 'application/json'
              }
          });
    
          let result = await res.json();
    
          if(result && result.text){
              UserStore.loading = false;
              UserStore.isLoggedIn = true;
              UserStore.username = result.username;
          }
          else{
    
            UserStore.loading = false;
            UserStore.isLoggedIn = false;
          }
    
        }
        catch(e){
            UserStore.loading = false;
            UserStore.isLoggedIn = false;
        }
      }
    
    
    
      async doLogOut(){
    
        try{
    
          let res = await fetch('/logout', {
    
              method: 'post',
              headers: {
    
                'Accept': 'application/json',
                'Content-Type' : 'application/json'
              }
          });
    
          let result = await res.json();
    
          if(result && result.text){
              UserStore.isLoggedIn = false;
              UserStore.username = '';
          }
    
        }
        catch(e){
           console.log(e);
        }
      }
      
    
    
      render(){
    
        if(UserStore.loading){
    
          return (
               <div className="app">
                 Loading, please wait
              </div>
          );
        }
        else {
    
          if(UserStore.isLoggedIn){
            return (
              <div className="app">
                Welcome {UserStore.username}
    
                <SubmitButton
                    text = {'Log out'}
                    disabled = {false}
                    onClick = {() => this.doLogOut()}
                />
             </div>
         );
    
          }
            return (
              <div className="app">
                  <div className='container'>
                      <LoginForm/>
                  </div>
              </div>
            );
        }
    }

}

export default observer(HomePage);