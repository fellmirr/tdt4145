package com.tdt4145.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ThreadsDAO extends Database {
    public static int addThread(String threadName, int folderID) { 
        int response = -1;
        try {
            PreparedStatement regStatement = conn.prepareStatement("INSERT INTO Threads(FolderID,ThreadName) VALUES" + " ( (?), (?))",Statement.RETURN_GENERATED_KEYS);
            regStatement.setInt(1, folderID);
            regStatement.setString(2, threadName);
            regStatement.executeUpdate();
            ResultSet result = regStatement.getGeneratedKeys();
            if(result.next()){
                response = result.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }    
}
