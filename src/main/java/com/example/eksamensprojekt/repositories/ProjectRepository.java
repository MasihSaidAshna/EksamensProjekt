package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.*;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
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
            String SQL = "SELECT * FROM user JOIN project WHERE project.uid = ? AND user.name = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUserName());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int projectID = rs.getInt("pid");
                String projectName = rs.getString("projectName");
                int userID = rs.getInt("uid");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Project project = new Project(projectID, userID, projectName, deadline);
                projectArrayList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectArrayList;
    }


    public Project fetchProject(User user, int projectID) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project WHERE pid = ? AND project.uid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, projectID);
            pstmt.setInt(2, user.getUserID());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("uid");
                String projectName = rs.getString("projectName");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                return new Project(projectID, userID, projectName, deadline);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean createProject(User user, Project project){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO project (projectName, deadline, uid) VALUES (?, STR_TO_DATE(?,'%Y-%m-%d'), (SELECT uid FROM user WHERE name = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getDeadline().toString());
            pstmt.setString(3, user.getUserName());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateProject(User user, Project project, String name, LocalDate deadline) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE project SET projectName = ?, deadline = STR_TO_DATE(?,'%Y-%m-%d') WHERE uid = ? AND pid = ?;";
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
