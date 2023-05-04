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

    public ArrayList<Project> getProjects(User user) {
        return projectRepository.getProjects(user);
    }


    public Project fetchProject(User user, String name) {
        return projectRepository.fetchProject(user, name);
    }


    public boolean createProject(User user, Project project){
        return projectRepository.createProject(user, project);
    }


    public void updateProjectName(User user, Project project, String name) {
        projectRepository.updateProjectName(user, project, name);
    }


    public void updateProjectDeadline(User user, Project project, LocalDate deadline) {
        projectRepository.updateProjectDeadline(user, project, deadline);
    }


    public void deleteProject(User user, Project project) {
        projectRepository.deleteProject(user, project);
    }


}
