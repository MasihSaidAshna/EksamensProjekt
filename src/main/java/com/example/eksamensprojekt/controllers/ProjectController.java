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



    //Får fat i alle projekter i databasen
    @GetMapping("/projects/{userID}")
    public String getProjects(@PathVariable("userID") int userID, Model model) {
        User user = userService.fetchUser(userID);
        ArrayList<Project> projects = projectService.getProjects(); //Henter alle projekter i databasen
        model.addAttribute("userID", userID);
        model.addAttribute("user", user);
        model.addAttribute("projects", projects); //Listen af projekter tilføjes til model som attribut
        return "projects";
    }


    @GetMapping("/projects/create/{userID}")
    public String createProject(@PathVariable int userID, Model model){
        LocalDate now = LocalDate.now(); //Lokal tid lige nu
        model.addAttribute("now", now);
        model.addAttribute("userID", userID);
        model.addAttribute("projectForm", new Project()); //Tomt projekt objekt
        return "project-form";
    }


    @PostMapping("/projects/create/{userID}")
    public String doCreateProject(@ModelAttribute("projectForm") Project project, @ModelAttribute("userID") int uid, Model model) {
        User user = userService.fetchUser(uid);
        boolean success = projectService.createProject(user, project); //Opretter objekt ud fra projektet som blev indtastet og bruger ID
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
        model.addAttribute("projectForm", project); //Henter projektet som skal blive redigeret
        return "projectupdate-form";
    }


    @PostMapping("/projects/update/{projectID}")
    public String doUpdateProject(@PathVariable("projectID") int projectID, @ModelAttribute("projectForm") Project project, Model model) {
        String newProjectName = project.getProjectName();
        LocalDate newProjectDeadline = project.getDeadline();

        boolean success = projectService.updateProject(projectID, newProjectName, newProjectDeadline); //Gemmer det nye navn og deadline til databasen
        if (success){
            return "redirect:/projects/" + project.getUserID();
        }
        else {
            model.addAttribute("errorMessage", "Failed to update project");
            return "error";
        }
    }


    @GetMapping("/projects/delete/{projectID}")
    public String deleteProject(@PathVariable("projectID") int projectID){
        Project project = projectService.fetchProject(projectID);
        int uid = project.getUserID();
        projectService.deleteProject(projectID);
        return "redirect:/projects/" + uid;
    }


}
