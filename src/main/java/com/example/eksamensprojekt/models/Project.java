package com.example.eksamensprojekt.models;

public class Project {

    private int projectID;
    private String projectName;
    private int userID;

    public Project(int projectID, String projectName, int userID) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.userID = userID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
