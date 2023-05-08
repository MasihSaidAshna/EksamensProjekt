package com.example.eksamensprojekt.models;

public class User {

    public enum Role { //Needs to be implemented
        ADMIN,
        MODERATOR,
        EMPLOYEE
    }

    private int userID;
    private String userName;
    private String password;
    private String email;


    public User(){}

    public User(int userID, String userName, String password, String email) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "uid='" + userID + '\'' +
                ", username='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
