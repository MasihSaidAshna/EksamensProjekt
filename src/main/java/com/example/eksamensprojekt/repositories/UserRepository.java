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
            String SQL = "SELECT * FROM user;"; //SQL statement for at vise alt indhold fra "user" tabellen
            PreparedStatement pstmt = con.prepareStatement(SQL);    //Fortæller programmet at det er et SQL statement
            ResultSet rs = pstmt.executeQuery();    //Prepared statement vises i en tabel: resultset
            while (rs.next()) { //While loop: mens resultset har en række skal den gøre dette:
                int ID = rs.getInt("uid"); //Hvor der står "uid" i tabellen skal være ID variablen
                String username = rs.getString("name"); //Hvor der står "username" skal det være "username"
                String password = rs.getString("password"); //Hvor der står "password" skal det være "password"
                String email = rs.getString("email"); //Hvor der står "email" skal det være "email"
                User.Role role = User.Role.valueOf(rs.getString("role")); //Hvor der står en string med navnet af en Role enumerate skal det være "role"
                users.add(new User(ID, username, password, email, role)); //Opretter et user objekt med alle disse attributter
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users; //Returnerer listen af users i databasen
    }


    //Gør det samme som getUsers() bortset for at den ikke bruger et whileloop men et "if-statement". Det betyder at der kun er 1 række den kigger på i resultset.
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
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE email=? AND password=?"); //Find alt fra user hvor email og password er lig noget vi vælger
            stmt.setString(1, email); //Første spørgsmålstegn i prepareStatement skal være emeail
            stmt.setString(2, password); //Andet spørgsmålstegn i prepareStatement skal være password
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { //Får fat i user ID, username og rolle hvis der findes en user
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
            String SQL = "INSERT INTO user(uid, name, password, email, role) VALUES(?, ?, ?, ?, ?)"; //Indsætter en ny user i user tabellen fra databasen med værdier som vælges i preparestatement med spørgsmålstegnene
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getRole().toString()); //Enumerate skal konverteres til en string for at kunne virke i databasen
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void editUser(int ID, String name, String password, String email, User.Role role) {
        try(Connection con = DBManager.getConnection()) {
            String SQL = "UPDATE user SET name = ?, password = ?, email = ?, role = ? WHERE uid = ?;"; //Opdaterer navn, password, email og rolle.
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
            String SQL = "DELETE FROM user WHERE uid = ?"; //Sletter en user med user ID man vælger
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
