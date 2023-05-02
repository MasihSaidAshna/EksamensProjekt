package com.example.eksamensprojekt.services;

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


    public Module fetchModule(Project project, String name) {
        return moduleRepository.fetchModule(project, name);
    }


    public void createModule(Project project, Module module){
        moduleRepository.createModule(project, module);
    }


    public void updateModuleName(Project project, Module module, String name) {
        moduleRepository.updateModuleName(project, module, name);
    }


    public void updateModuleDeadline(Project project, Module module, LocalDate deadline) {
        moduleRepository.updateModuleDeadline(project, module, deadline);
    }


    public void updateModuleStatus(Project project, Module module, Module.Status status) {
        moduleRepository.updateModuleStatus(project, module, status);
    }


    public void deleteModule(Project project, Module module) {
        moduleRepository.deleteModule(project, module);
    }


}
