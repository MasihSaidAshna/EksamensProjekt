package com.example.eksamensprojekt.controllers;

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
    private final HttpSession httpSession;

    public ProjectController(ProjectService projectService, HttpSession httpSession) {
        this.projectService = projectService;
        this.httpSession = httpSession;
    }

    @GetMapping("/projects/{userID}")
    public String getProjects(@PathVariable("userID") int userID, Model model) {
        User user = (User) httpSession.getAttribute("user");
        ArrayList<Project> projects = projectService.getProjects(user);
        model.addAttribute("userID", userID);
        model.addAttribute("user", user);
        model.addAttribute("projects", projects);
        System.out.println(model);
        return "projects";
    }


    @GetMapping("/projects/create/{userID}")
    public String createProject(@ModelAttribute("user") User user, Model model){
        model.addAttribute("projectForm", new Project());
        System.out.println(model);
        return "project-form";
    }


    @PostMapping("/projects/create/{userID}")
    public String doCreateProject(@ModelAttribute("projectForm") Project Project, Model model) {
        User user = (User) httpSession.getAttribute("user");
        String projectName = Project.getProjectName();
        LocalDate deadline = Project.getDeadline();

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


    @GetMapping("/projects/update/{projectID}")
    public String updateProject(@PathVariable("projectID") int projectID, Model model){
        User user = (User) httpSession.getAttribute("user");
        Project project = projectService.fetchProject(user, projectID);
        model.addAttribute("projectForm", project);
        return "projectupdate-form";
    }


    @PostMapping("/projects/update/{projectID}")
    public String doUpdateProject(@PathVariable("projectID") int projectID, @ModelAttribute("projectForm") Project project, HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        String newProjectName = project.getProjectName();
        LocalDate newProjectDeadline = project.getDeadline();

        boolean success = projectService.updateProject(user, project, newProjectName, newProjectDeadline);
        if (success){
            return "redirect:/projects/" + user.getUserID();
        }
        else {
            model.addAttribute("errorMessage", "Failed to create user");
            return "error";
        }
    }


    @GetMapping("/projects/delete/{projectID}")
    public String deleteProject(@PathVariable("projectID") int projectID, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        Project project = projectService.fetchProject(user, projectID);
        projectService.deleteProject(user, project);
        return "projects";
    }


}
