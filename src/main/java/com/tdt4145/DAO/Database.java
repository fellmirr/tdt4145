package com.tdt4145.DAO;

import java.sql.*;

/**
 * The “Database” class is designed as a singleton, 
 * meaning that the database object is initialized only once. 
 * This is done by calling “Database.getInstance()” which 
 * will check if the database object has already been initialized 
 * and return that. If the database object has not been initialized, 
 * it will initialize the database object and return that. 
 */

public class Database {
    private static Database dbObject;
    private static Connection conn;
    
    public Database() {
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
     * Creates a prepared statement for a given
     * SQL string and returns it.
     * 
     * Use .setInt, .setString etc. methods to set the values
     * Then call .execute() to execute the query
     * 
     * @param sql The query to run against the database
     * @return The PreparedStatement object.
     */
    public PreparedStatement prepare(String sql) {
        try {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch(Exception e) {
            System.out.println("Failed to prepare sql statment");
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
