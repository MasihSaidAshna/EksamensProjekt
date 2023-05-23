package com.example.eksamensprojekt.controllers;

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

    private final UserService userService;
    private final ProjectService projectService;
    private final HttpSession httpSession;

    public ProjectController(UserService userService, ProjectService projectService, HttpSession httpSession) {
        this.userService = userService;
        this.projectService = projectService;
        this.httpSession = httpSession;
    }



    @GetMapping("/projects/{userID}")
    public String getProjects(@PathVariable("userID") int userID, Model model) {
        User user = userService.fetchUser(userID);
        ArrayList<Project> projects = projectService.getProjects();
        model.addAttribute("userID", userID);
        model.addAttribute("user", user);
        model.addAttribute("projects", projects);
        return "projects";
    }


    @GetMapping("/projects/create/{userID}")
    public String createProject(@PathVariable int userID, Model model){
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("userID", userID);
        model.addAttribute("projectForm", new Project());
        return "project-form";
    }


    @PostMapping("/projects/create/{userID}")
    public String doCreateProject(@ModelAttribute("projectForm") Project project, @ModelAttribute("userID") int uid, Model model) {
        User user = userService.fetchUser(uid);
        boolean success = projectService.createProject(user, project);
        if (success){
            return "redirect:/projects/{userID}";
        }
        else {
            model.addAttribute("errorMessage", "Failed to create project");
            return "error";
        }
    }


    @GetMapping("/projects/update/{projectID}")
    public String updateProject(@PathVariable("projectID") int projectID, @ModelAttribute("projects") ArrayList<Project> projects, Model model){
        Project project = projectService.fetchProject(projectID);
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("projectForm", project);
        return "projectupdate-form";
    }


    @PostMapping("/projects/update/{projectID}")
    public String doUpdateProject(@PathVariable("projectID") int projectID, @ModelAttribute("projectForm") Project project, Model model) {
        String newProjectName = project.getProjectName();
        LocalDate newProjectDeadline = project.getDeadline();

        boolean success = projectService.updateProject(projectID, newProjectName, newProjectDeadline);
        if (success){
            return "redirect:/projects/" + project.getUserID();
        }
        else {
            model.addAttribute("errorMessage", "Failed to update project");
            return "error";
        }
    }


    @PostMapping("/projects/delete/{projectID}")
    public String deleteProject(@PathVariable("projectID") int projectID){
        Project project = projectService.fetchProject(projectID);
        int uid = project.getUserID();
        projectService.deleteProject(projectID);
        return "redirect:/projects/" + uid;
    }


}
