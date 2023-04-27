package com.example.eksamensprojekt.services;

import com.example.eksamensprojekt.repositories.DBManager;
import org.springframework.stereotype.Service;
import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.repositories.ModuleRepository;
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


    public Module fetchModule(Project project, String name) {
        return moduleRepository.fetchModule(project, name);
    }


    public void createModule(Project project, Module module){
        moduleRepository.createModule(project, module);
    }


    public void updateModule(Project project, Module module, String name) {
        moduleRepository.updateModule(project, module, name);
    }


    public void deleteModule(Project project, Module module) {
        moduleRepository.deleteModule(project, module);
    }


}
