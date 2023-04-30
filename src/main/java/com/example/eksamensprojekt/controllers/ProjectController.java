package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.services.UserService;
import org.springframework.stereotype.Controller;
import com.example.eksamensprojekt.models.*;
import com.example.eksamensprojekt.services.ProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/{userID}/projects")
    public String getProjects(@PathVariable("userID") int userID, Model model) {
        User user = userService.fetchUser(userID);
        ArrayList<Project> projects = projectService.getProjects(user);
        model.addAttribute("user", user);
        model.addAttribute("projects", projects);
        return "projects";
    }





}
