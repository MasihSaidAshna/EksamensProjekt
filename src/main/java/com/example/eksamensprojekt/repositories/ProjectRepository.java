package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class ProjectRepository {

    public ProjectRepository() {
    }


    public int findUIDFromProject(int pid) {
        ArrayList<Project> projects = getProjects();
        int uid = 0;
        for (Project p : projects){
            if (p.getProjectID() == pid){
                uid = p.getUserID();
            }
        }
        return uid;
    }


    public ArrayList<Project> getProjects() {
        ArrayList<Project> projectArrayList = new ArrayList<>();
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM productmanagementtooldatabase.project;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int projectID = rs.getInt("pid");
                String projectName = rs.getString("project_name");
                String projectCreator = rs.getString("project_creator");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int userID = rs.getInt("uid");
                Project project = new Project(projectID, userID, projectName, projectCreator, deadline);
                projectArrayList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectArrayList;
    }


    public Project fetchProject(int userID, int projectID) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project WHERE pid = ? AND project.uid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, projectID);
            pstmt.setInt(2, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int uid = rs.getInt("uid");
                String projectName = rs.getString("project_name");
                String projectCreator = rs.getString("project_creator");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                return new Project(projectID, uid, projectName, projectCreator, deadline);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean createProject(User user, Project project){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO project (project_name, project_creator, deadline, uid) VALUES (?, ?, STR_TO_DATE(?,'%Y-%m-%d'), (SELECT uid FROM user WHERE name = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, project.getDeadline().toString());
            pstmt.setString(4, user.getUserName());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateProject(User user, Project project, String name, LocalDate deadline) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE project SET project_name = ?, deadline = STR_TO_DATE(?,'%Y-%m-%d') WHERE uid = ? AND pid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setString(2, deadline.toString());
            pstmt.setInt(3, user.getUserID());
            pstmt.setInt(4, project.getProjectID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProject(int uid, Project project) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM project WHERE project_name = ? AND uid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, project.getProjectName());
            pstmt.setInt(2, uid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
