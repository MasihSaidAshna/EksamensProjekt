package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.services.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import com.example.eksamensprojekt.services.ModuleService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class ModuleController {

    private final ModuleService moduleService;
    private final ProjectService projectService;
    private final HttpSession httpSession;

    public ModuleController(ModuleService moduleService, ProjectService projectService, HttpSession httpSession) {
        this.moduleService = moduleService;
        this.projectService = projectService;
        this.httpSession = httpSession;
    }


    @GetMapping("/modules/{projectID}")
    public String getModules(@PathVariable("projectID") int projectID, Model model) {
        User user = (User) httpSession.getAttribute("user");
        Project project = projectService.fetchProject(user, projectID);
        ArrayList<Module> modules = moduleService.getModules(project);
        model.addAttribute("modules", modules);
        return "view-modules";
    }





}

