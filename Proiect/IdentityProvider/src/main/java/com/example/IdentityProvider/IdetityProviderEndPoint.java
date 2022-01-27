package com.example.IdentityProvider;

import com.example.IdentityProvider.Services.IdentityProviderCreateJWT;
import com.example.IdentityProvider.Services.UserService;
import identityprovider.example.com.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class IdetityProviderEndPoint
{
    private final UserService userService;
    private static final String NAMESPACE_URI = "http://com.example.IdentityProvider/Authentication";

    @Autowired
    public IdetityProviderEndPoint(UserService userService) {
        this.userService = userService;
    }

    //adaugam un nou user, avand ca parametru un AddUserRequest ce contine datele necesare crearii unui nou cont
    //returnam ca raspuns un AddUserResponse ce contine tokenul creat
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addNewUSer(@RequestPayload AddUserRequest user)
    {
        AddUserResponse response = new AddUserResponse();
        response.setToken(userService.addNewUser(user));
        return  response;
    }

    //cream un jwt pentru un user avand ca parametru un CreateJwtRequest ce contine userul si parola
    //returnam un CreateJwtResponse ce va contine tokenul
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createJwtRequest")
    @ResponsePayload
    public CreateJwtResponse createUserJWT(@RequestPayload CreateJwtRequest jwtRequest)
    {
        CreateJwtResponse response = new CreateJwtResponse();
        response.setToken(userService.createJWT(jwtRequest));
        return  response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "verifyJwtRequest")
    @ResponsePayload
    public VerifyJwtResponse createUserJWT(@RequestPayload VerifyJwtRequest verifyJwtRequest)
    {
        VerifyJwtResponse response = new VerifyJwtResponse();
        response.setResponse(userService.verifyUserJWT(verifyJwtRequest));
        return  response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePasswordRequest")
    @ResponsePayload
    public UpdatePasswordResponse createUserJWT(@RequestPayload UpdatePasswordRequest updatePasswordRequest)
    {
        UpdatePasswordResponse response = new UpdatePasswordResponse();
        response.setResponse(userService.updatePassword(updatePasswordRequest));
        return  response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse createUserJWT(@RequestPayload DeleteUserRequest deleteUserRequest)
    {
        DeleteUserResponse response = new DeleteUserResponse();
        response.setResponse(userService.deleteUser(deleteUserRequest));
        return  response;
    }

}
