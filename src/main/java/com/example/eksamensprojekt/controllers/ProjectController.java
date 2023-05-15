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
        int uid = projectService.findUIDFromProject(projectID);
        Project project = projectService.fetchProject(uid, projectID);
        model.addAttribute("projectForm", project);
        return "projectupdate-form";
    }


    @PostMapping("/projects/update/{projectID}")
    public String doUpdateProject(@PathVariable("projectID") int projectID, @ModelAttribute("projectForm") Project project, Model model) {
        int uid = projectService.findUIDFromProject(projectID);
        User user = userService.fetchUser(uid);
        String newProjectName = project.getProjectName();
        LocalDate newProjectDeadline = project.getDeadline();

        boolean success = projectService.updateProject(user, project, newProjectName, newProjectDeadline);
        if (success){
            return "redirect:/projects/" + user.getUserID();
        }
        else {
            model.addAttribute("errorMessage", "Failed to update project");
            return "error";
        }
    }


    @GetMapping("/projects/delete/{projectID}")
    public String deleteProject(@PathVariable("projectID") int projectID){
        int uid = projectService.findUIDFromProject(projectID);
        Project project = projectService.fetchProject(uid, projectID);
        projectService.deleteProject(uid, project);
        return "redirect:/projects/" + uid;
    }


}
