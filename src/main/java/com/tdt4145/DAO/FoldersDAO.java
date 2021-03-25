package com.tdt4145.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class FoldersDAO {
    static private Database db = Database.getInstance();

    /**
     * Fetches the folder ID of a given folder
     * @param folderName
     * @return The folder ID if found, -1 otherwise
     */
    public static int getFolderID(String folderName){
        int folderID = -1; 
        try{
            ResultSet result = db.query("SELECT FolderID from Folders WHERE FolderName =" +'"' + folderName + '"');
            if (result.next()) {
                folderID = result.getInt("FolderID");     
            }
        }          
         catch (SQLException e3) {
            e3.printStackTrace();   
        }
        return folderID;
    } 

    /**
     * Adds a folder to the database
     * @param folderName
     * @return The ID of the inserted folder if successfull, -1 otherwise
     */
    public static int addFolder(String folderName, int courseId) {
        int response = -1;

        try {
            PreparedStatement stmt = db.prepare("INSERT INTO Folders(CourseID, FolderName) VALUES ( (?), (?))");
            stmt.setInt(1, courseId);
            stmt.setString(2,folderName);
            stmt.execute();
            
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                response = result.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }
}

