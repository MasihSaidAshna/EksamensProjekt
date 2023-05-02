package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.models.User;
import jdk.jshell.Snippet;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class ModuleRepository {

    private final ProjectRepository projectRepository;


    public ModuleRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public ArrayList<Module> getModules(Project project) {
        ArrayList<Module> moduleArrayList = new ArrayList<>();
        Module module = null;
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project JOIN module WHERE module.pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project.getProjectID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int moduleID = rs.getInt("mid");
                String moduleName = rs.getString("moduleName");
                int projectID = rs.getInt("pid");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Module.Status status = Module.Status.valueOf(rs.getString("setstatus"));
                module = new Module(moduleID, moduleName, projectID, deadline, status);
                moduleArrayList.add(module);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moduleArrayList;
    }


    public Module fetchModule(Project project, String name) {
        Module module = null;
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM module WHERE moduleName = ? AND module.pid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(1, project.getProjectID());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int moduleID = rs.getInt("mid");
                String moduleName = rs.getString("moduleName");
                int projectID = rs.getInt("pid");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Module.Status status = Module.Status.valueOf(rs.getString("setstatus"));
                module = new Module(moduleID, moduleName, projectID, deadline, status);
                return module;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void createModule(Project project, Module module){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO module (moduleName, deadline, setstatus, pid) VALUES (?, ?, ?, (SELECT pid FROM module WHERE projectName = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, module.getModuleName());
            pstmt.setString(2, module.getDeadline().toString());
            pstmt.setString(3, module.getStatus().toString());
            pstmt.setString(4, project.getProjectName());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateModuleName(Project project, Module module, String name) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE module SET moduleName = ?, WHERE pid = ? AND mid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, project.getProjectID());
            pstmt.setInt(3, module.getModuleID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateModuleDeadline(Project project, Module module, LocalDate deadline) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE module SET deadline = ?, WHERE pid = ? AND mid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, deadline.toString());
            pstmt.setInt(2, project.getProjectID());
            pstmt.setInt(3, module.getModuleID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateModuleStatus(Project project, Module module, Module.Status status) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE module SET setstatus = ?, WHERE pid = ? AND mid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, status.toString());
            pstmt.setInt(2, project.getProjectID());
            pstmt.setInt(3, module.getModuleID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteModule(Project project, Module module) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM module WHERE moduleName = ? AND pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, module.getModuleName());
            pstmt.setInt(2, project.getProjectID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
