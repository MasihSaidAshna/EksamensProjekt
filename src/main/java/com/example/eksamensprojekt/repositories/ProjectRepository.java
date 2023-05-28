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


    public ArrayList<Project> getProjects() {
        ArrayList<Project> projectArrayList = new ArrayList<>();
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int projectID = rs.getInt("pid");
                String projectName = rs.getString("project_name");
                String projectCreator = rs.getString("project_creator");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int timeEstimate = rs.getInt("time_estimate");
                int userID = rs.getInt("uid");
                Project project = new Project(projectID, userID, projectName, projectCreator, deadline, timeEstimate);
                projectArrayList.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projectArrayList;
    }


    public Project fetchProject(int projectID) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project WHERE pid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, projectID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int uid = rs.getInt("uid");
                String projectName = rs.getString("project_name");
                String projectCreator = rs.getString("project_creator");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int timeEstimate = rs.getInt("time_estimate");
                return new Project(projectID, uid, projectName, projectCreator, deadline, timeEstimate);
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


    public boolean updateProject(int pid, String projectName, LocalDate deadline) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE project SET project_name = ?, deadline = STR_TO_DATE(?,'%Y-%m-%d') WHERE pid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, projectName);
            pstmt.setString(2, deadline.toString());
            pstmt.setInt(3, pid);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProject(int pid) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM project WHERE pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, pid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Used only for testing. Not safe elsewhere
    //Resets project table's auto_increment for pid value
    public void resetProjectIDIncrement() {
        try(Connection con = DBManager.getConnection()) {
            String SQL1 = "SELECT MAX(pid) FROM project";
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            ResultSet rs = pstmt1.executeQuery();
            if (rs.next()){
                int maxProjectID = rs.getInt("MAX(pid)");
                String SQL2 = "ALTER TABLE project AUTO_INCREMENT = " + (maxProjectID + 1);
                PreparedStatement pstmt2 = con.prepareStatement(SQL2);
                pstmt2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
