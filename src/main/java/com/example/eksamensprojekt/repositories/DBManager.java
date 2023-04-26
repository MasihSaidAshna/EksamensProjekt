package com.example.eksamensprojekt.repositories;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBManager {
    private static String url;
    private static String user;
    private static String pwd;
    private static Connection con;


    public static Connection getConnection() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("spring.datasource.url");
            user = properties.getProperty("spring.datasource.username");
            pwd = properties.getProperty("spring.datasource.password");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, user, pwd);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }

}
