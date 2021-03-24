package com.tdt4145.DAO;

import com.tdt4145.Models.Post;

import org.omg.PortableServer.ThreadPolicyOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import java.sql.Date;

public class PostsDAO extends Database {

    public static PreparedStatement regStatement;

    /**
     * Fetches a post with a given ID from the database
     * @param postId
     * @return A Post object if found, null otherwise
     */
     public static Post GetPost(int postId) {
        Database db = Database.getInstance();
        ResultSet result = db.query("SELECT * FROM Posts WHERE PostID = " + postId);
        try {
            boolean next = result.next();
            if (next) {
                return new Post(
                        result.getInt("PostID"),
                        result.getInt("UserID"),
                        result.getInt("ThreadID"),
                        result.getInt("TagID"),
                        result.getDate("Posted"),
                        result.getString("Text"),
                        result.getInt("Answered"),
                        result.getInt("Likes"),
                        result.getInt("Anonymous"));
            } else {
                System.out.println("No post found with ID " + postId);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }

    /**
     * Inserts a post into the database
     * @param threadID Thread the post belongs to
     * @param tagId Tag of the post
     * @param text The text of the post
     * @param userId The user id of the post author
     * @return Returns 1 if successfully replied, or 0 if not.
     */
    public static int addPost(int threadID, int tagID, String text, int userId) {
        int response = 0;
        try {
            regStatement = conn.prepareStatement("INSERT INTO Posts(UserID, ThreadID, TagID, Text) VALUES" +
                    " ( (?), (?), (?), (?))");
            regStatement.setInt(1, userId);
            regStatement.setInt(2,threadID);
            regStatement.setInt(3,tagID);
            regStatement.setString(4, text);
            regStatement.execute();
            response = 1;
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }
}



