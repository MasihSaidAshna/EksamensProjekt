package com.example.eksamensprojekt.models;

import java.time.LocalDate;
import java.time.Period;


public class Project {

    private int projectID;
    private int userID; //Foreign key
    private String projectName;
    private LocalDate deadline;
    private Period timeEstimate;

    public Project() {
    }

    public Project(int projectID, int userID, String projectName, LocalDate deadline) {
        this.projectID = projectID;
        this.userID = userID;
        this.projectName = projectName;
        this.deadline = deadline;
        this.timeEstimate = calculatePeriod();
    }


    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
