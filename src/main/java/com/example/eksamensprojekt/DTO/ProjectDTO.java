package com.example.eksamensprojekt.DTO;

import java.time.LocalDate;
import java.time.Period;

public class ProjectDTO {
    private String projectName;
    private LocalDate deadline;
    private Period timeEstimate;

    public ProjectDTO() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
