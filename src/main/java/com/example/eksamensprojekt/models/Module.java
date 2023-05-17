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
    private int projectID;
    private int userID;
    private String moduleName;
    private LocalDate deadline;
    private Period timePeriod;
    private int timeEstimate;
    private Status status;
    private String assignUser;


    public Module() {
    }

    public Module(int moduleID, int projectID, int userID, String moduleName, LocalDate deadline,  int timeEstimate, Status status, String assignUser) {
        this.moduleID = moduleID;
        this.projectID = projectID;
        this.userID = userID;
        this.moduleName = moduleName;
        this.deadline = deadline;
        this.timePeriod = calculatePeriod();
        this.timeEstimate = timeEstimate;
        this.status = status;
        this.assignUser = assignUser;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public Period calculatePeriod(){
        return Period.between(LocalDate.now(), deadline);
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getTimePeriod(){
        String years = timePeriod.getYears() == 1 ? timePeriod.getYears() + " year" : timePeriod.getYears() + " years";
        String months = timePeriod.getMonths() == 1 ? timePeriod.getMonths() + " month" : timePeriod.getMonths() + " months";
        String days = timePeriod.getDays() == 1 ? timePeriod.getDays() + " day" : timePeriod.getDays() + " days";
        String retval = String.format("Deadline is %s, %s, %s from now.",
                years, months, days);
        if (retval.contains("0 years,")){
            retval = retval.replace("0 years,", "");
        }
        if (retval.contains("0 months,")){
            retval = retval.replace("0 months,", "");
        }
        return retval;
    }

    public void setTimePeriod(Period timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getTimeEstimate() {
        return timeEstimate;
    }

    public void setTimeEstimate(int timeEstimate) {
        this.timeEstimate = timeEstimate;
    }
}
