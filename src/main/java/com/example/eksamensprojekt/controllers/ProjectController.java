package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.DTO.ProjectDTO;
import com.example.eksamensprojekt.DTO.UserDTO;
import com.example.eksamensprojekt.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import com.example.eksamensprojekt.models.*;
import com.example.eksamensprojekt.services.ProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;


@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects/{userID}")
    public String getProjects(@PathVariable("userID") int userID, Model model, HttpSession httpSession) {
        //httpSession.setAttribute("userID", userID);
        User user = (User) httpSession.getAttribute("user");
        ArrayList<Project> projects = projectService.getProjects(user);
        model.addAttribute("userID", userID);
        model.addAttribute("user", user);
        model.addAttribute("projects", projects);
        System.out.println(model);
        return "projects";
    }


    @GetMapping("/projects/create/{userID}")
    public String createProject(@PathVariable("userID") int userID, @ModelAttribute("user") User user, Model model){
        model.addAttribute("projectForm", new ProjectDTO());
        System.out.println(model);
        return "project-form";
    }


    @PostMapping("/projects/create/{userID}")
    public String doCreateProject(@ModelAttribute("projectForm") ProjectDTO projectDTO, HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        String projectName = projectDTO.getProjectName();
        LocalDate deadline = projectDTO.getDeadline();

        //User user = userService.fetchUser(userID);
        Project project = new Project();
        project.setProjectName(projectName);
        project.setDeadline(deadline);

        boolean success = projectService.createProject(user, project);

        if (success){
            return "redirect:/projects/{userID}";
        }
        else {
            model.addAttribute("errorMessage", "Failed to create user");
            return "error";
        }
    }


}
