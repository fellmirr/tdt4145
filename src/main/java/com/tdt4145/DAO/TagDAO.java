package com.tdt4145.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class TagDAO {
    static private Database db = Database.getInstance();

    /**
     * Gets the ID of a given tag name, if it exists
     * @param tagName
     * @return The ID of the tag, -1 if it does not exist
     */
    public static int getTagID(String tagName) {
        int tagID = -1; 
        Database db = Database.getInstance();
        try{
            ResultSet result = db.query("SELECT TagID from PostTags WHERE Name = " +'"' + tagName + '"');
            if (result.next()){
                tagID = result.getInt("TagID");     
            }
        } catch (SQLException e3) {
            e3.printStackTrace();   
        }
        return tagID;
    } 

    /**
     * Inserts a new tag to the database
     * @param tagName
     * @return The ID of the inserted tag if successfull, -1 otherwise
     */
    public static int addTag(String tagName) {
        int response = -1;
        try {
            PreparedStatement stmt = db.prepare("INSERT INTO PostTags(Name) VALUES ((?))");
            stmt.setString(1, tagName);
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

