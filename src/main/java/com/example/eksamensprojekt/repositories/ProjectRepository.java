package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class ProjectRepository {

    private final UserRepository userRepository;

    public ProjectRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ArrayList<Project> getProjects(User user) {
        ArrayList<Project> projectArrayList = new ArrayList<>();
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM user JOIN project WHERE project.uid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, user.getUserID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int projectID = rs.getInt("pid");
                String projectName = rs.getString("projectName");
                int userID = rs.getInt("uid");
                Project project = new Project(projectID, projectName, userID);
                projectArrayList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectArrayList;
    }


    public Project fetchProject(User user, String name) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project WHERE projectName = ? AND project.uid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(1, user.getUserID());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int projectID = rs.getInt("pid");
                String projectName = rs.getString("projectName");
                int userID = rs.getInt("uid");
                return new Project(projectID, projectName, userID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void createProject(User user, Project project){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO project (projectName, uid) VALUES (?, (SELECT uid FROM user WHERE name = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(1, user.getUserName());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateProject(User user, Project project, String name) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE project SET projectName = ?, WHERE uid = ? AND pid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, user.getUserID());
            pstmt.setInt(3, project.getProjectID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProject(User user, Project project) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM project WHERE projectName = ? AND uid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, project.getProjectName());
            pstmt.setInt(2, user.getUserID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}