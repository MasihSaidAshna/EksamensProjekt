package com.example.eksamensprojekt.services;


import com.example.eksamensprojekt.models.User;
import org.springframework.stereotype.Service;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.repositories.ProjectRepository;
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


    public void createProject(User user, Project project){
        projectRepository.createProject(user, project);
    }


    public void updateProject(User user, Project project, String name) {
        projectRepository.updateProject(user, project, name);
    }


    public void deleteProject(User user, Project project) {
        projectRepository.deleteProject(user, project);
    }


}
