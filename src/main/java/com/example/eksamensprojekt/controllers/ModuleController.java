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

import java.time.LocalDate;
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
        model.addAttribute("projectID", projectID);
        model.addAttribute("project", project);
        model.addAttribute("modules", modules);
        return "view-modules";
    }



    @GetMapping("/modules/create/{projectID}")
    public String createModule(@PathVariable int projectID, Model model){
        model.addAttribute("projectID", projectID);
        model.addAttribute("moduleForm", new Module());
        return "module-form";
    }


    @PostMapping("/modules/create/{projectID}")
    public String doCreateModule(@PathVariable int projectID, @ModelAttribute("moduleForm") Module module, Model model) {
        User user = (User) httpSession.getAttribute("user");
        Project project = projectService.fetchProject(user, projectID);
        boolean success = moduleService.createModule(user, project, module);
        if (success){
            return "redirect:/modules/{projectID}";
        }
        else {
            model.addAttribute("errorMessage", "Failed to create module");
            return "error";
        }
    }


    @GetMapping("/modules/update/{projectID}/{moduleID}")
    public String updateModule(@PathVariable("moduleID") int moduleID, @PathVariable("projectID") int projectID, Model model){
        Module module = moduleService.fetchModule(projectID, moduleID);
        model.addAttribute("projectID", projectID);
        model.addAttribute("moduleForm", module);
        return "module-update-form";
    }


    @PostMapping("/modules/update/{projectID}/{moduleID}")
    public String doUpdateModule(@PathVariable("moduleID") int moduleID, @PathVariable("projectID") int projectID, @ModelAttribute("moduleForm") Module module, HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        String newModuleName = module.getModuleName();
        LocalDate newModuleDeadline = module.getDeadline();
        Module.Status newStatus = module.getStatus();
        String assignUser = "Unassigned";
        Module newModule = new Module(moduleID, projectID, user.getUserID(), newModuleName, newModuleDeadline, newStatus, assignUser);

        boolean success = moduleService.updateModule(newModule);
        if (success){
            return "redirect:/modules/{projectID}";
        }
        else {
            model.addAttribute("errorMessage", "Failed to update module");
            return "error";
        }
    }


    @GetMapping("/modules/delete/{projectID}/{moduleID}")
    public String deleteModule(@PathVariable("projectID") int projectID, @PathVariable("moduleID") int moduleID){
        Module module = moduleService.fetchModule(projectID, moduleID);
        moduleService.deleteModule(module);
        return "redirect:/modules/{projectID}";
    }


    @GetMapping("/modules/assign/{projectID}/{moduleID}")
    public String assignUser(@PathVariable("projectID") int projectID, @PathVariable("moduleID") int moduleID){
        User user = (User) httpSession.getAttribute("user");
        Module module = moduleService.fetchModule(projectID, moduleID);
        moduleService.assignUser(user, module);
        return "redirect:/modules/{projectID}";
    }


}

