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


    //Viser homepage
    @GetMapping("")
    public String showHomepage() {
        return "homepage";
    }


    //Viser "about" siden
    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }

    //Tjekker om user er logget ind ved at tjekke om httpSession er null
    public boolean isLoggedIn(){
        return httpSession.getAttribute("user") != null;
    }


    //GetMapping: viser signup siden
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("signupForm", new User()); //Model tilføjer et tomt User objekt som sin egen attribut og attributen hedder "signupForm"
        return "signup";
    }


    //PostMapping: udfører signup og gemmer til databasen
    @PostMapping("/signup")
    public String doSignup(@ModelAttribute("signupForm") User user, Model model) {
        int uid = user.getUserID(); //User ID som "model" fik fra signup siden
        String username = user.getUserName(); //Får fat i navnet user har skrevet
        String password = user.getPassword(); //Users adgangskode
        String email = user.getEmail(); //Email
        User.Role role = user.getRole(); //Rolle
        User userNew = new User(uid, username, password, email, role); //Laver et nyt user objekt med alle disse nye attributter

        boolean success = userService.createUser(userNew); //Gemmer user til databasen
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
        User user = userService.findUserByEmailAndPassword(email, password); //Tjekker om der er en user med intastet email og adgangskode
        if (user != null){
            this.httpSession.setAttribute("user", user); //HttpSession får en attribut: user objektet som er logget ind og som bliver navngivet "user"
            this.httpSession.setMaxInactiveInterval(60);
            return "redirect:/profile?userID=" + user.getUserID(); //Redirect til profil siden
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
