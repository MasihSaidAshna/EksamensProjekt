package com.example.eksamensprojekt.repositories;

import com.example.eksamensprojekt.models.Module;
import com.example.eksamensprojekt.models.Project;
import com.example.eksamensprojekt.models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
            String SQL = "SELECT project.pid, projectName, mid, moduleName, module.deadline, setstatus, module.pid, module.uid FROM project JOIN module WHERE module.pid = ? AND projectName = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project.getProjectID());
            pstmt.setString(2, project.getProjectName());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int moduleID = rs.getInt("mid");
                int projectID = rs.getInt("pid");
                int userID = rs.getInt("uid");
                String moduleName = rs.getString("moduleName");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Module.Status status = Module.Status.valueOf(rs.getString("setstatus"));
                module = new Module(moduleID, projectID, userID, moduleName, deadline, status);
                moduleArrayList.add(module);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moduleArrayList;
    }


    public Module fetchModule(int pid, int mid) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM module WHERE mid = ? AND module.pid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, mid);
            pstmt.setInt(2, pid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int moduleID = rs.getInt("mid");
                int userID = rs.getInt("uid");
                int projectID = rs.getInt("pid");
                String moduleName = rs.getString("moduleName");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Module.Status status = Module.Status.valueOf(rs.getString("setstatus"));
                return new Module(moduleID, projectID, userID, moduleName, deadline, status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean createModule(User user, Project project, Module module){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO module (moduleName, deadline, setstatus, pid, uid) VALUES (?, STR_TO_DATE(?, '%Y-%m-%d'), ?, (SELECT pid FROM project WHERE projectName = ?), (SELECT uid FROM user WHERE name = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, module.getModuleName());
            pstmt.setString(2, module.getDeadline().toString());
            module.setStatus(Module.Status.TO_DO);
            pstmt.setString(3, module.getStatus().toString());
            pstmt.setString(4, project.getProjectName());
            pstmt.setString(5, user.getUserName());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateModule(Module newModule) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE module SET moduleName = ?, deadline = ?, setstatus = ? WHERE module.pid = ? AND mid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, newModule.getModuleName());
            pstmt.setDate(2, Date.valueOf(newModule.getDeadline()));
            pstmt.setString(3, newModule.getStatus().toString());
            pstmt.setInt(4, newModule.getProjectID());
            pstmt.setInt(5, newModule.getModuleID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*    public void updateModuleName(Project project, Module module, String name) {
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
    }*/


    public void deleteModule(Module module) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM module WHERE mid = ? AND moduleName = ? AND module.pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, module.getModuleID());
            pstmt.setString(2, module.getModuleName());
            pstmt.setInt(3, module.getProjectID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
