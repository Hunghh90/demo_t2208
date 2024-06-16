package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/t2208";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static Connection connection; // singleton
    // fixme :  2. init connection every query -> singleton design pattern
    public static synchronized Connection getConnection(){
        if(connection == null){
            init();
        }
        return connection;
    }



    public static void init(){
        // return connection to db
        Connection newConnection = null;
        try {
            // before have connection  ,  driver class
            Class.forName(DRIVER_CLASS_NAME);
            newConnection = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            if(newConnection != null){
                System.out.println("Connection successfull ! " );
            }else {
                System.err.println("Connection failed ! " );
            }
        }catch (Exception ex){
            System.err.println("Exception : "+ex.getMessage());
        }
        connection = newConnection;
    }
}

