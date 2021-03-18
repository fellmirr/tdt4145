package com.tdt4145.DAO;

import java.sql.*;

public class Database {
    private static Database dbObject;
    private Connection conn;
    
    private Database() {
        connect();
    }

    /**
     * Checks if there exists an instance of the singleton
     * database class. If not, it creates one and returns it.
     * @return An instance of the database object
     */
    public static Database getInstance() {
        if (dbObject == null)
            dbObject = new Database();

        return dbObject;
    }

    /**
     * Queries the database with the given SQL query
     * @param sql The query to run against the DB
     * @return The results of the query
     */
    public ResultSet query(String sql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            return result;
        } catch(Exception e) {
            System.out.println("Failed to execute sql statment");
            System.out.println(sql);
            return null;
        }
    }

    /**
     * Connects to the database with the JDCB driver
     * Given this is a singleton class, we will only
     * connect once during the lifetime of the application,
     * and subsequent calls will be made with the connection
     * obtained the first time.
     */
    private void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://35.228.101.86/piazza?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=true", "root", "7pCeLJDwxe9isDco");
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect");
        }
    }
}
