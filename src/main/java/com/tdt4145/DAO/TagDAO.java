package com.tdt4145.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagDAO {
    public static int getTagID(String tagName){
        int tagID = -1; 
        Database db = Database.getInstance();
        try{
            ResultSet result = db.query("SELECT TagID from PostTags WHERE Name = " +'"' + tagName + '"');
            while(result.next()){
                tagID = result.getInt("TagID");     
            }
        }          
         catch (SQLException e3) {
            e3.printStackTrace();   
        }
        return tagID;
    } 
}

