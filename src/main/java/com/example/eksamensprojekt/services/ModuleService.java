package com.example.eksamensprojekt.services;

import com.example.eksamensprojekt.models.User;
import org.springframework.stereotype.Service;
import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.repositories.ModuleRepository;

import java.time.LocalDate;
import java.util.ArrayList;


@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }


    public ArrayList<Module> getModules(Project project) {
        return moduleRepository.getModules(project);
    }


    public Module fetchModule(int pid, int mid) {
        return moduleRepository.fetchModule(pid, mid);
    }


    public boolean createModule(User user, Project project, Module module){
        return moduleRepository.createModule(user, project, module);
    }


    public boolean updateModule(Module module) {
        return moduleRepository.updateModule(module);
    }


    public void deleteModule(Module module) {
        moduleRepository.deleteModule(module);
    }


    public void assignUser(User user, Module module) {
        moduleRepository.assignUser(user, module);
    }


}
