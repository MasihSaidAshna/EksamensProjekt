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


    public boolean isLoggedIn(){
        return httpSession.getAttribute("user") != null;
    }


    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("signupForm", new UserDTO());
        return "signup";
    }


    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("signupForm") UserDTO userDTO, Model model) {
        String username = userDTO.getUsername();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        boolean success = userService.createUser(user);

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
        model.addAttribute("loginForm", new UserDTO());
        return "login";
    }


    @PostMapping("/login")
    public String doLogin(@ModelAttribute("user") UserDTO userDTO, Model model) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        User user = userService.findUserByEmailAndPassword(email, password);
        if (user != null){
            this.httpSession.setAttribute("user", user);
            this.httpSession.setMaxInactiveInterval(60);
            if (user.getUserID() == 1){
                return "redirect:/admin/profile";
            }
            else {
            return "redirect:/profile?userID=" + user.getUserID();
            }
        }
        else {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "login";
        }
    }


    @GetMapping("/admin/profile")
    public String showAdminProfile(){
        return isLoggedIn() ? "admin-profile" : "login";
    }


    @GetMapping("/profile")
    public String showProfile(){
        return isLoggedIn() ? "profile" : "login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "homepage";
    }



}
