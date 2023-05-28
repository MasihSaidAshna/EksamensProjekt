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
            String SQL = "SELECT project.pid, project_name, mid, module_name, module.deadline, module.time_estimate, set_status, assign_user, module.pid, module.uid FROM project JOIN module WHERE module.pid = ? AND project_name = ?;";
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
                int timeEstimate = rs.getInt("time_estimate");
                Module.Status status = Module.Status.valueOf(rs.getString("set_status"));
                String assignUser = rs.getString("assign_user");
                module = new Module(moduleID, projectID, userID, moduleName, deadline, timeEstimate, status, assignUser);
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
                int timeEstimate = rs.getInt("time_estimate");
                Module.Status status = Module.Status.valueOf(rs.getString("set_status"));
                String assignUser = rs.getString("assign_user");
                return new Module(moduleID, projectID, userID, moduleName, deadline, timeEstimate, status, assignUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public boolean createModule(User user, Project project, Module module){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO module (module_name, deadline, time_estimate, set_status, pid, uid) VALUES (?, STR_TO_DATE(?, '%Y-%m-%d'), ?, ?, (SELECT pid FROM project WHERE project_name = ?), (SELECT uid FROM user WHERE name = ?));";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, module.getModuleName());
            pstmt.setString(2, module.getDeadline().toString());
            pstmt.setInt(3, module.getTimeEstimate());
            module.setStatus(Module.Status.TO_DO);
            pstmt.setString(4, module.getStatus().toString());
            pstmt.setString(5, project.getProjectName());
            pstmt.setString(6, user.getUserName());
            pstmt.execute();
            updateProjectTimeEstimate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateModule(Module newModule) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE module SET module_name = ?, deadline = ?, time_estimate = ?, set_status = ? WHERE module.pid = ? AND mid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, newModule.getModuleName());
            pstmt.setDate(2, Date.valueOf(newModule.getDeadline()));
            pstmt.setInt(3, newModule.getTimeEstimate());
            pstmt.setString(4, newModule.getStatus().toString());
            pstmt.setInt(5, newModule.getProjectID());
            pstmt.setInt(6, newModule.getModuleID());
            pstmt.executeUpdate();
            updateProjectTimeEstimate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteModule(int mid, int pid) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM module WHERE mid = ? AND module.pid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, mid);
            pstmt.setInt(2, pid);
            pstmt.executeUpdate();
            updateProjectTimeEstimate();
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


    public void updateProjectTimeEstimate() {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE project SET time_estimate = (SELECT SUM(time_estimate) FROM module WHERE module.pid = project.pid);";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Module> viewAssignedModules(User user) {
        ArrayList<Module> moduleArrayList = new ArrayList<>();
        Module module = null;
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM module WHERE assign_user = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, user.getUserName());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int moduleID = rs.getInt("mid");
                int projectID = rs.getInt("pid");
                int userID = rs.getInt("uid");
                String moduleName = rs.getString("module_name");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int timeEstimate = rs.getInt("time_estimate");
                Module.Status status = Module.Status.valueOf(rs.getString("set_status"));
                String assignUser = rs.getString("assign_user");
                module = new Module(moduleID, projectID, userID, moduleName, deadline, timeEstimate, status, assignUser);
                moduleArrayList.add(module);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moduleArrayList;
    }


    //For testing only
    public ArrayList<Module> getAllModules() {
        ArrayList<Module> moduleArrayList = new ArrayList<>();
        Module module = null;
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM module;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int moduleID = rs.getInt("mid");
                int projectID = rs.getInt("pid");
                int userID = rs.getInt("uid");
                String moduleName = rs.getString("module_name");
                LocalDate deadline = rs.getDate("deadline").toLocalDate();
                int timeEstimate = rs.getInt("time_estimate");
                Module.Status status = Module.Status.valueOf(rs.getString("set_status"));
                String assignUser = rs.getString("assign_user");
                module = new Module(moduleID, projectID, userID, moduleName, deadline, timeEstimate, status, assignUser);
                moduleArrayList.add(module);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moduleArrayList;
    }


    //Used only for testing. Not safe elsewhere
    //Resets module table's auto_increment for mid value
    public void resetModuleIDIncrement() {
        try(Connection con = DBManager.getConnection()) {
            String SQL1 = "SELECT MAX(mid) FROM module";
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            ResultSet rs = pstmt1.executeQuery();
            if (rs.next()){
                int maxProjectID = rs.getInt("MAX(mid)");
                String SQL2 = "ALTER TABLE module AUTO_INCREMENT = " + (maxProjectID + 1);
                PreparedStatement pstmt2 = con.prepareStatement(SQL2);
                pstmt2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
