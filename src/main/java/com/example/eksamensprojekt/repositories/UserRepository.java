package com.example.eksamensprojekt.repositories;

import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;
import com.example.eksamensprojekt.models.*;


@Repository
public class UserRepository {

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM user;";
            PreparedStatement pstmt = con.prepareStatement(SQL);    //Fortæller programmet at det er et SQL statement
            ResultSet rs = pstmt.executeQuery();    //Prepared statement vises i en tabel: resultset
            while (rs.next()) {
                int ID = rs.getInt("uid");
                String username = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                User.Role role = User.Role.valueOf(rs.getString("role"));
                users.add(new User(ID, username, password, email, role));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public User fetchUser(int ID) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "SELECT * FROM user WHERE uid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("uid");
                String username = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                User.Role role = User.Role.valueOf(rs.getString("role"));
                return new User(userID, username, password, email, role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public User findUserByEmailAndPassword(String email, String password) {
        try (Connection con = DBManager.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int uid = rs.getInt("uid");
                String username = rs.getString("name");
                User.Role role = User.Role.valueOf(rs.getString("role"));
                return new User(uid, username, password, email, role);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    public boolean createUser(User user){
        try(Connection con = DBManager.getConnection()) {
            String SQL = "INSERT INTO user(uid, name, password, email, role) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getRole().toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void editUser(int ID, String name, String password, String email, User.Role role) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE user SET name = ?, password = ?, email = ?, role = ? WHERE uid = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, role.toString());
            pstmt.setInt(5, ID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteUser(int userID) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "DELETE FROM user WHERE uid = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
