package com.example.eksamensprojekt.services;

import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }

    public User fetchUser(int userID) {
        return userRepository.fetchUser(userID);
    }

    public User findUserByEmailAndPassword(String email, String password){
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public boolean createUser(User user) {
        return userRepository.createUser(user);
    }

    public void editUser(int ID, String name, String password, String email, User.Role role) {
        userRepository.editUser(ID, name, password, email, role);
    }

    public void deleteUser(int userID) {
        userRepository.deleteUser(userID);
    }


}
