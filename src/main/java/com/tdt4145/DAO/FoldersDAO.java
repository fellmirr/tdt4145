package com.tdt4145.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class FoldersDAO {
    static private Database db = Database.getInstance();

    public static int getFolderID(String folderName){
        int folderID = -1; 
        try{
            ResultSet result = db.query("SELECT FolderID from Folders WHERE FolderName =" +'"' + folderName + '"');
            while(result.next()){
                folderID = result.getInt("FolderID");     
            }
        }          
         catch (SQLException e3) {
            e3.printStackTrace();   
        }
        return folderID;
    } 

    public static int addFolder(String folderName) {
        int courseID = 1;
        int response = 0;

        try {
            PreparedStatement stmt = db.prepare("INSERT INTO Folders(CourseID, FolderName) VALUES" + "( (?), (?))");
            stmt.setInt(1, courseID);
            stmt.setString(2,folderName);
            stmt.execute();
            response = 1;
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }

}

