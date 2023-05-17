package com.example.eksamensprojekt.services;


import com.example.eksamensprojekt.models.User;
import org.springframework.stereotype.Service;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public ArrayList<Project> getProjects() {
        return projectRepository.getProjects();
    }


    public Project fetchProject(int pid) {
        return projectRepository.fetchProject(pid);
    }

    public boolean createProject(User user, Project project){
        return projectRepository.createProject(user, project);
    }

    public boolean updateProject(int pid, String name, LocalDate deadline) {
        return projectRepository.updateProject(pid, name, deadline);
    }

    public void deleteProject(int pid) {
        projectRepository.deleteProject(pid);
    }

}
