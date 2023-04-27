package com.example.eksamensprojekt.controllers;

import org.springframework.stereotype.Controller;
import com.example.eksamensprojekt.models.*;
import com.example.eksamensprojekt.services.ProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{name}/projects")
    public String getProjects(@PathVariable String name, User user, Model model) {
        model.addAttribute(projectService.getProjects(user));
        return "projects";
    }





}
