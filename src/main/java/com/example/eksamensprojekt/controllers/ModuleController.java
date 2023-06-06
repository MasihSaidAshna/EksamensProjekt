package com.example.eksamensprojekt.controllers;

import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.models.User;
import com.example.eksamensprojekt.services.ProjectService;
import com.example.eksamensprojekt.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import com.example.eksamensprojekt.services.ModuleService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class ModuleController {

    private final ModuleService moduleService;
    private final ProjectService projectService;
    private final UserService userService;
    private final HttpSession httpSession;

    public ModuleController(ModuleService moduleService, ProjectService projectService, UserService userService, HttpSession httpSession) {
        this.moduleService = moduleService;
        this.projectService = projectService;
        this.userService = userService;
        this.httpSession = httpSession;
    }


    @GetMapping("/modules/{projectID}")
    public String getModules(@PathVariable("projectID") int projectID, Model model) {
        Project project = projectService.fetchProject(projectID); //Henter projektet som ejer alle modulerne
        ArrayList<Module> modules = moduleService.getModules(project); //Henter alle moduler under projektet
        model.addAttribute("projectID", projectID);
        model.addAttribute("project", project);
        model.addAttribute("modules", modules);
        return "view-modules";
    }



    @GetMapping("/modules/create/{projectID}")
    public String createModule(@PathVariable int projectID, Model model){
        Project project = projectService.fetchProject(projectID);
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("projectID", projectID);
        model.addAttribute("project", project);
        model.addAttribute("moduleForm", new Module());
        return "module-form";
    }


    @PostMapping("/modules/create/{projectID}")
    public String doCreateModule(@PathVariable int projectID, @ModelAttribute("moduleForm") Module module, Model model) {
        Project project = projectService.fetchProject(projectID);
        User user = userService.fetchUser(project.getUserID());
        boolean success = moduleService.createModule(user, project, module); //Gemmer modulet under projektet og bruger
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
        Project project = projectService.fetchProject(projectID);
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("projectID", projectID);
        model.addAttribute("project", project);
        model.addAttribute("moduleForm", module);
        return "module-update-form";
    }


    @PostMapping("/modules/update/{projectID}/{moduleID}")
    public String doUpdateModule(@PathVariable("moduleID") int moduleID, @PathVariable("projectID") int projectID, @ModelAttribute("moduleForm") Module module, Model model) {
        int uid = projectService.fetchProject(projectID).getUserID();

        String newModuleName = module.getModuleName();
        LocalDate newModuleDeadline = module.getDeadline();
        int newTimeEstimate = module.getTimeEstimate();
        Module.Status newStatus = module.getStatus();
        String assignUser = "Unassigned";
        Module newModule = new Module(moduleID, projectID, uid, newModuleName, newModuleDeadline, newTimeEstimate, newStatus, assignUser);

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
        moduleService.deleteModule(moduleID, projectID);
        return "redirect:/modules/{projectID}";
    }

    //Assigner sig selv hvis user som er logget ind er employee rolle typen ved at bruge HttpSession.
    @GetMapping("/modules/assignself/{projectID}/{moduleID}")
    public String assignSelf(@PathVariable("projectID") int projectID, @PathVariable("moduleID") int moduleID){
        User user = (User) httpSession.getAttribute("user"); //Får fat i user gemt i HttpSession under navnet "user"
        Module module = moduleService.fetchModule(projectID, moduleID);
        moduleService.assignUser(user, module);
        return "redirect:/modules/{projectID}";
    }

    @GetMapping("/modules/assign/{projectID}/{moduleID}")
    public String assignUser(@PathVariable("projectID") int projectID, @PathVariable("moduleID") int moduleID, Model model){
        ArrayList<User> employees = (ArrayList<User>) userService.getUsers().stream()
                .filter(user -> user.getRole().equals(User.Role.EMPLOYEE))
                .collect(Collectors.toList()); //Henter en liste af alle users som har rollen "employee"
        Module module = moduleService.fetchModule(projectID, moduleID);
        model.addAttribute("module", module);
        model.addAttribute("employees", employees); //Tilføjer listen af employees til model
        return "assign-employee";
    }


    @PostMapping("/modules/assign/{projectID}/{moduleID}")
    public String doAssignUser(@PathVariable("projectID") int projectID, @PathVariable("moduleID") int moduleID,
                               @ModelAttribute("module") Module module, @ModelAttribute("employees") ArrayList<User> employees, @RequestParam("employeeID") int employeeID){
        User employee = userService.fetchUser(employeeID);
        Module m = moduleService.fetchModule(projectID, moduleID);
        m.setAssignUser(employee.getUserName());
        moduleService.assignUser(employee, m); //Assigner employee til modulet "m"
        return "redirect:/modules/{projectID}";
    }


    //Viser alle moduler man er tildelt som employee
    @GetMapping("/profile/modules")
    public String viewAssignedModules(Model model){
        User user = (User) httpSession.getAttribute("user");
        ArrayList<Module> modules = moduleService.viewAssignedModules(user);;
        model.addAttribute("modules", modules);
        return "assigned-modules";
    }

}

