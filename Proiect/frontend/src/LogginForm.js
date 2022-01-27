import React from 'react';
import InputField from './InputField';
import SubmitButton from './SubmitButton';
import UserStore from './UserStore';

class LoginForm extends React.Component {

    constructor(props){

        super(props);
        this.state = {

            username: '',
            password: '',
            buttonDisabled: false
        }
    }

    setInputValue(property, val){

        val = val.trim();
        if(val.length > 12){
            return;
        }
        this.setState({
            [property]: val
        })
    }

    resetForm() {

        this.setState({
            username: '',
            password: '',
            buttonDisabled: false
        })
    }

    async doLogin(){

        if(!this.state.username){
            return;
        }

        if(!this.state.password){
            return;
        }

        this.setState({
            buttonDisabled: true
        })


        let url = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
        "                            xmlns:us=\"http://com.example.IdentityProvider/Authentication\">\n" +
        "                <soapenv:Header/>\n" +
        "                   <soapenv:Body>\n" +
        "                      <us:createJwtRequest>\n" +
        "                            <us:username>" + this.state.username + "</us:username>" +
        "                            <us:password>" + this.state.password + "</us:password>" +
        "                     </us:createJwtRequest>\n" +
        "                  </soapenv:Body>\n" +
        "               </soapenv:Envelope>";
        


        try{

            let res = await fetch('/sample', {

                method: 'post',
                headers: {
                    'Accept': '*/*',
                    'Content-Type': 'text/xml',
                    'mode': 'no-cors',

                },

                body: url
            });

            var result = await res.text();
            var jwt = result.substring(
                result.lastIndexOf("<ns2:token>") + "<ns2:token>".length, 
                result.lastIndexOf("</ns2:token>"));

            // let result = await res.text();
            
            console.log(jwt);

            if(jwt){

                UserStore.isLoggedIn = true;
                // UserStore.username = res.username;
            }
            else {

                this.resetForm();

            }
        }

        catch (e){
            console.log(e);
            this.resetForm();
        }
    }

    render(){
        return (
            <div className="loginForm">

                Log in 
                <InputField
                    type = 'text'
                    placeholder = 'Username'
                    value = {this.state.username ? this.state.username : ''}
                    onChange = { (val) =>  this.setInputValue('username', val)}
                />

                <InputField
                    type = 'password'
                    placeholder = 'Password'
                    value = {this.state.password ? this.state.password : ''}
                    onChange = { (val) =>  this.setInputValue('password', val)}
                />

                <SubmitButton
                    text = 'Login'
                    disabled = {this.state.buttonDisabled}
                    onClick = { () => this.doLogin() }
                />


            
            </div>
        );
    }
  
}

export default LoginForm;
