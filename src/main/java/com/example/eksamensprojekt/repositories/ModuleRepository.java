package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.models.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class ModuleRepository {

    private final ProjectRepository projectRepository;


    public ModuleRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public ArrayList<Module> getModules(Project project) {
        ArrayList<Module> moduleArrayList = new ArrayList<>();
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM project JOIN module WHERE module.pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project.getProjectID());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int moduleID = rs.getInt("mid");
                String moduleName = rs.getString("moduleName");
                int projectID = rs.getInt("pid");
                Module module = new Module(moduleID, moduleName, projectID);
                moduleArrayList.add(module);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moduleArrayList;
    }


    public Module fetchModule(Project project, String name) {
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
                return new Module(moduleID, moduleName, projectID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void createModule(Project project, Module module){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO module (moduleName, pid) VALUES (?, (SELECT pid FROM module WHERE projectName = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, module.getModuleName());
            pstmt.setString(1, project.getProjectName());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateModule(Project project, Module module, String name) {
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
