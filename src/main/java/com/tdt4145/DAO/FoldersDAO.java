package com.tdt4145.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoldersDAO {
    public static int getFolderID(String folderName){
        int folderID = -1; 
        Database db = Database.getInstance();
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
}

