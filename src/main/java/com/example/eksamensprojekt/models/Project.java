package com.example.eksamensprojekt.models;

import java.time.LocalDate;
import java.time.Period;


public class Project {

    private int projectID;
    private int userID; //Foreign key
    private String projectName;
    private String projectCreator;
    private LocalDate deadline;
    private Period timePeriod;
    private int timeEstimate;

    public Project() {
    }

    public Project(int projectID, int userID, String projectName, String projectCreator, LocalDate deadline, int timeEstimate) {
        this.projectID = projectID;
        this.userID = userID;
        this.projectName = projectName;
        this.projectCreator = projectCreator;
        this.deadline = deadline;
        this.timePeriod = calculatePeriod();
        this.timeEstimate = timeEstimate;
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

    public String getProjectCreator() {
        return projectCreator;
    }

    public void setProjectCreator(String projectCreator) {
        this.projectCreator = projectCreator;
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
}
