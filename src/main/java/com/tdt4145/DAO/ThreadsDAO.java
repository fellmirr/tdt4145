package com.tdt4145.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.tdt4145.Models.Thread;

import com.tdt4145.Models.Post;

public class ThreadsDAO extends Database {
    static private Database db = Database.getInstance();

    public static int addThread(String threadName, int folderID) { 
        int response = -1;
        try {
            PreparedStatement stmt = db.prepare("INSERT INTO Threads(FolderID,ThreadName) VALUES" + " ( (?), (?))");
            stmt.setInt(1, folderID);
            stmt.setString(2, threadName);
            stmt.executeUpdate();
            ResultSet result = stmt.getGeneratedKeys();
            if(result.next()){
                response = result.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into Posts");
        }
        return response;
    }

    public static List<Post> getThreadPosts(int threadId) {
        List<Post> resultList = new ArrayList<Post>();

        try {
            ResultSet result = db.query(String.format("SELECT * FROM Posts WHERE ThreadID = %s", threadId));
            while (result.next()) {
                resultList.add(getPost(result));
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch thread posts from db");
        }

        return resultList;
    }

    public static List<Thread> GetThreads(int folderId) {
        ResultSet result = db.query("SELECT ThreadID, FolderID ThreadName FROM Threads WHERE FolderID = " + folderId);
        try {
            List<Thread> thread = new ArrayList<Thread>();

            while(result.next()) {
                thread.add(new Thread(
                    result.getInt("ThreadID"),
                    result.getInt("FolderID"),
                    result.getString("ThreadName")));
            }

            return thread;
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
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
