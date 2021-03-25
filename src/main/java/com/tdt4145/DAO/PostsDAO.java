package com.tdt4145.DAO;

import com.tdt4145.Models.Post;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostsDAO {
    static private Database db = Database.getInstance();

    /**
     * Fetches a post with a given ID from the database
     * @param postId
     * @return A Post object if found, null otherwise
     */
     public static Post GetPost(int postId) {
        ResultSet result = db.query("SELECT * FROM Posts WHERE PostID = " + postId);
        try {
            boolean next = result.next();
            if (next) {
                Post post = getPost(result);
                if (post != null)
                    return post;
                else
                    throw new Exception("Failed to fetch post from database result");
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
            PreparedStatement stmt = db.prepare("INSERT INTO Posts(UserID, ThreadID, TagID, Text) VALUES ( (?), (?), (?), (?))");
            stmt.setInt(1, userId);
            stmt.setInt(2,threadID);
            stmt.setInt(3,tagID);
            stmt.setString(4, text);
            stmt.execute();
            response = 1;
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }

    /**
     * Searches for posts containing the search string in the database, both
     * with like based search and mysql fulltext search. Returns a maximum of
     * 20 posts.
     * @param searchString
     * @return A list of posts matching the search string
     */
    public static List<Post> searchPosts(String searchString) {
        ResultSet result = db.query("SELECT * FROM piazza.Posts WHERE MATCH (`Text`) AGAINST('" + searchString + "' IN NATURAL LANGUAGE MODE) OR `Text` LIKE '%" + searchString + "%' LIMIT 20;");
        try {
            List<Post> Posts = new ArrayList<Post>();

            while(result.next()) {
                Post post = getPost(result);
                if (post != null)
                    Posts.add(post);
                else
                    throw new Exception("Failed to fetch post from database result");
            }

            return Posts;
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }

    /**
     * Updates a post to be "Answered".
     * @param postId The post id replied to
     * @return Returns 1 if successfully updated, or 0 if not.
     */
    public static int UpdatePostAsAnswered(int postId) {
        int response = 0;
        try {
            PreparedStatement stmt = db.prepare("UPDATE Posts SET Answered = ? WHERE PostID = ?");
            stmt.setInt(1, 1);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
            response = 1;
        } catch (Exception e) {
            System.out.println("Failed to update post as answered");
            response = 0;
        }
        return response;
    }

    /**
     * Takes in a result row from a database query and attempts to extract
     * a Post object from the result row
     * @param result The result set row
     * @return A Post
     */
    private static Post getPost(ResultSet result) {
        try {
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
        } catch(Exception e) {
            System.out.println("Failed to fetch post from database result row.");
            return null;
        }
    }
}



