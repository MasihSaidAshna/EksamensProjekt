package com.example.eksamensprojekt.models;

public class Module {

    enum Status {
        TO_DO,
        DOING,
        DROPPED,
        DONE
    }

    private int moduleID;
    private String moduleName;
    private int projectID;

    public Module(int moduleID, String moduleName, int projectID) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.projectID = projectID;
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
}
