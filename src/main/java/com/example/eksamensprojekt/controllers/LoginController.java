package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.DTO.*;
import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("")
public class LoginController {

    private final UserService userService;


    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String showHomepage() {
        return "homepage";
    }


    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("signupForm", new UserSignupDTO());
        return "signup";
    }


    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("signupForm") UserSignupDTO userSignupDTO, Model model) {
        String username = userSignupDTO.getUsername();
        String email = userSignupDTO.getEmail();
        String password = userSignupDTO.getPassword();

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
    public String doLogin(@ModelAttribute("user") UserDTO userDTO, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        User user = userService.findUserByEmailAndPassword(email, password);
        if (user != null){
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(60);
            redirectAttributes.addAttribute("userID", user.getUserID());
            return "redirect:/profile";
        }
        else {
            model.addAttribute("errorMessage", "Invalid email or password");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "homepage";
    }

    @GetMapping("/profile")
    public String showProfile(User user, Model model){
        model.addAttribute("user", user);
        return "profile";
    }


}
