package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    //Bruges til admin siden
    @GetMapping("/admin/users")
    public String showUsers(Model model){
        ArrayList<User> users = userService.getUsers(); //Får en liste af alle users i databasen
        model.addAttribute("users", users); //Tilføjer listen til "model"
        return "users";
    }

}
