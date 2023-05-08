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
    private Period timeEstimate;
    private Status status;

    public Module(int moduleID, String moduleName, int projectID, LocalDate deadline, Status status) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.projectID = projectID;
        this.deadline = deadline;
        this.timeEstimate = calculatePeriod();
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

    public Period calculatePeriod(){
        return Period.between(LocalDate.now(), deadline);
    }


    public String getTimeEstimate(){
        String years = timeEstimate.getYears() == 1 ? timeEstimate.getYears() + " year" : timeEstimate.getYears() + " years";
        String months = timeEstimate.getMonths() == 1 ? timeEstimate.getMonths() + " month" : timeEstimate.getMonths() + " months";
        String days = timeEstimate.getDays() == 1 ? timeEstimate.getDays() + " day" : timeEstimate.getDays() + " days";
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

    public void setTimeEstimate(Period timeEstimate) {
        this.timeEstimate = timeEstimate;
    }

}
