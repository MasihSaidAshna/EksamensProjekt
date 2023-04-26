package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> userList = userService.getUsers();
        model.addAttribute("userList", userList);
        return "users";
    }


}
