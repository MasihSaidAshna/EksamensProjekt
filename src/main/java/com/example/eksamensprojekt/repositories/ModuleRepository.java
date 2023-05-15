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
            String SQL = "SELECT project.pid, project_name, mid, module_name, module.deadline, set_status, assign_user, module.pid, module.uid FROM project JOIN module WHERE module.pid = ? AND project_name = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, project.getProjectID());
            pstmt.setString(2, project.getProjectName());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int moduleID = rs.getInt("mid");
                int projectID = rs.getInt("pid");
                int userID = rs.getInt("uid");
                String moduleName = rs.getString("module_name");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Module.Status status = Module.Status.valueOf(rs.getString("set_status"));
                String assignUser = rs.getString("assign_user");
                module = new Module(moduleID, projectID, userID, moduleName, deadline, status, assignUser);
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
                String moduleName = rs.getString("module_name");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                Module.Status status = Module.Status.valueOf(rs.getString("set_status"));
                String assignUser = rs.getString("assign_user");
                return new Module(moduleID, projectID, userID, moduleName, deadline, status, assignUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean createModule(User user, Project project, Module module){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO module (module_name, deadline, set_status, pid, uid) VALUES (?, STR_TO_DATE(?, '%Y-%m-%d'), ?, (SELECT pid FROM project WHERE project_name = ?), (SELECT uid FROM user WHERE name = ?));";
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
            String SQL = "UPDATE module SET module_name = ?, deadline = ?, set_status = ? WHERE module.pid = ? AND mid = ?;";
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


    public void deleteModule(Module module) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM module WHERE mid = ? AND module_name = ? AND module.pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, module.getModuleID());
            pstmt.setString(2, module.getModuleName());
            pstmt.setInt(3, module.getProjectID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void assignUser(User user, Module module) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE module SET assign_user = ? WHERE module.pid = ? AND mid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, user.getUserName());
            pstmt.setInt(2, module.getProjectID());
            pstmt.setInt(3, module.getModuleID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
