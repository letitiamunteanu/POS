package com.example.IdentityProvider.Services;


import com.example.IdentityProvider.POJO.User;
import com.example.IdentityProvider.Repositories.UserRepository;
import identityprovider.example.com.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //create jwt
    public String createJWT(CreateJwtRequest newJwt){

        Optional<User> userToCheck = userRepository.findById(newJwt.getUsername());

        //verificam daca exista in baza de date
        if(userToCheck.isPresent()){

            //verificam daca datele coincid
            if(userToCheck.get().getPassword().equals(newJwt.getPassword())) {
                return IdentityProviderCreateJWT.createJWT("http://localhost:6527/sample", userToCheck.get().getId(), userToCheck.get().getRole());
            }
        }
        return "Acest cont nu exista!";

    }

    //verificare de jwt
    public String verifyUserJWT(VerifyJwtRequest verifyJwtRequest){

        return IdentityProviderCreateJWT.verifyJwt(verifyJwtRequest.getToken());
    }

    //create a new user
    public String addNewUser(AddUserRequest userToAdd){

        if(userRepository.existsById(userToAdd.getName()) || userToAdd.getRole() == null || userToAdd.getPassword() == null){
            return null;
        }
        else{
            User newUser = new User();
            newUser.setId(userToAdd.getName());
            newUser.setPassword(userToAdd.getPassword());
            newUser.setRole(userToAdd.getRole());
            userRepository.save(newUser);

            System.out.println("s o creat");
            //cream jwt-ul pe care il returnam
            return IdentityProviderCreateJWT.createJWT("http://localhost:6527/sample", newUser.getId(), newUser.getRole());
        }
    }

    //update de parola
    public String updatePassword(UpdatePasswordRequest updatePasswordRequest){

        //verificam daca tokenul este valid sau nu a expirat
        String response = IdentityProviderCreateJWT.verifyJwt(updatePasswordRequest.getToken());

        //in response o sa am username si role
        //si verificam daca numele este egal cu numele primit sus ca parametru
        if(response.split(" ")[0].equals(updatePasswordRequest.getName())){

            //verificam daca exista in baza de date
            //salvam user ul si apoi ii updatam parola
            Optional<User> userToUpdate = userRepository.findById(response.split(" ")[0]);
            if(userToUpdate.isPresent()){

                userToUpdate.get().setPassword(updatePasswordRequest.getNewPassword());
                userRepository.save(userToUpdate.get());

                return IdentityProviderCreateJWT.createJWT("http://localhost:6527/sample", userToUpdate.get().getId(), userToUpdate.get().getRole());
            }
        }

        return "Nu exista userul dat";

    }

    //stergere de user
    public String deleteUser(DeleteUserRequest deleteUserRequest){

        //verificam daca tokenul este valid sau nu a expirat
        String response = IdentityProviderCreateJWT.verifyJwt(deleteUserRequest.getToken());

        //in response o sa am username si role
        //si verificam daca numele este egal cu numele primit sus ca parametru
        if(response.split(" ")[0].equals(deleteUserRequest.getUsername())){

            //verificam daca exista in baza de date
            Optional<User> userToDelete = userRepository.findById(response.split(" ")[0]);
            userToDelete.ifPresent(userRepository::delete);

            return "User sters cu succes!";
        }

        return "Nu s-a putut sterge userul!";

    }


}
