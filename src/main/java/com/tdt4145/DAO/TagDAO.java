package com.tdt4145.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class TagDAO extends Database{
    public static PreparedStatement regStatement;
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

    public static int addTag(String tagName) {
        int response = 0;
        try {
            regStatement = conn.prepareStatement("INSERT INTO PostTags(Name) VALUES" +
                    " ( (?))");
            regStatement.setString(1, tagName);
            regStatement.execute();
            response = 1;
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }

}

