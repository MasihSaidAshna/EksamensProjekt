package com.example.eksamensprojekt.models;

public class User {

    public enum Role {
        ADMIN,
        MANAGER,
        EMPLOYEE
    }

    private int userID;
    private String userName;
    private String password;
    private String email;
    private Role role;

    public User(){}

    public User(int userID, String userName, String password, String email, Role role) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + userID + '\'' +
                ", username='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", User role='" + role + '\'' +
                '}';
    }

}
