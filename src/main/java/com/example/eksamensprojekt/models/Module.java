package com.example.eksamensprojekt.models;

import java.time.LocalDate;
import java.time.Period;

public class Module {

    public enum Status {
        TO_DO,
        DOING,
        DROPPED,
        DONE
    }

    private int moduleID;
    private String moduleName;
    private int projectID;
    private LocalDate deadline;
    private Status status;

    public Module(int moduleID, String moduleName, int projectID, LocalDate deadline, Status status) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.projectID = projectID;
        this.deadline = deadline;
        this.status = status;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Period calculateTime(){
        return Period.between(LocalDate.now(), deadline);
    }

}
