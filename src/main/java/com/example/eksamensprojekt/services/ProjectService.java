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

    public int findUIDFromProject(int pid){return projectRepository.findUIDFromProject(pid);}

    public ArrayList<Project> getProjects() {
        return projectRepository.getProjects();
    }


    public Project fetchProject(int uid, int pid) {
        return projectRepository.fetchProject(uid, pid);
    }

    public boolean createProject(User user, Project project){
        return projectRepository.createProject(user, project);
    }

    public boolean updateProject(User user, Project project, String name, LocalDate deadline) {
        return projectRepository.updateProject(user, project, name, deadline);
    }

    public void deleteProject(int uid, Project project) {
        projectRepository.deleteProject(uid, project);
    }

}
