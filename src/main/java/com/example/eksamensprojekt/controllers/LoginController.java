package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.DTO.*;
import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class LoginController {

    private final UserService userService;
    private final HttpSession httpSession;
    public LoginController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }


    @GetMapping("")
    public String showHomepage() {
        return "homepage";
    }


    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }

    public boolean isLoggedIn(){
        return httpSession.getAttribute("user") != null;
    }


    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("signupForm", new User());
        return "signup";
    }


    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("signupForm") User user, Model model) {
        int uid = user.getUserID();
        String username = user.getUserName();
        String password = user.getPassword();
        String email = user.getEmail();
        User.Role role = user.getRole();
        User userNew = new User(uid, username, password, email, role);

        boolean success = userService.createUser(userNew);
        if (success){
            return "redirect:/login";
        }
        else {
            model.addAttribute("errorMessage", "Failed to create user");
            return "signup";
        }
    }


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new User());
        return "login";
    }


    @PostMapping("/login")
    public String doLogin(@ModelAttribute("loginForm") User userModel, Model model) {
        String email = userModel.getEmail();
        String password = userModel.getPassword();
        User user = userService.findUserByEmailAndPassword(email, password);
        if (user != null){
            this.httpSession.setAttribute("user", user);
            this.httpSession.setMaxInactiveInterval(60);
            return "redirect:/profile?userID=" + user.getUserID();
        }
        else {
            model.addAttribute("errorMessage", "Invalid email or password");
            System.out.println(model);
            return "login";
        }
    }


    @GetMapping("/profile")
    public String showProfile(){
        return isLoggedIn() ? "profile" : "login";
    }


    @GetMapping("/logout")
    public String logout() {
        this.httpSession.invalidate();
        return "homepage";
    }

}
