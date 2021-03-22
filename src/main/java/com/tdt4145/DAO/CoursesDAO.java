package com.tdt4145.DAO;

import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;
import com.tdt4145.Models.Thread;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO extends Database {
    public static List<Course> GetCourses(int userId) {
        Database db = Database.getInstance();
        ResultSet result = db.query("SELECT Courses.CourseID, Courses.Name FROM Users RIGHT JOIN UsersInCourse ON Users.UserID = UsersInCourse.UserID INNER JOIN Courses ON Courses.CourseID = UsersInCourse.CourseID WHERE Users.UserID = " + userId);
        try {
            List<Course> Courses = new ArrayList<Course>();

            while(result.next()) {
                Courses.add(new Course(
                    result.getInt("CourseID"),
                    result.getString("Name")));
            }

            return Courses;
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }

    public static List<Folder> GetFolders(int courseId) {
        Database db = Database.getInstance();
        ResultSet result = db.query("SELECT FolderID, FolderName FROM Folders WHERE CourseID = " + courseId);
        try {
            List<Folder> folders = new ArrayList<Folder>();

            while(result.next()) {
                folders.add(new Folder(
                    result.getInt("FolderID"),
                    result.getString("FolderName")));
            }

            return folders;
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }

    public static List<Thread> GetThreads(int folderId) {
        Database db = Database.getInstance();
        ResultSet result = db.query("SELECT ThreadID, ThreadName FROM Threads WHERE FolderID = " + folderId);
        try {
            List<Thread> thread = new ArrayList<Thread>();

            while(result.next()) {
                thread.add(new Thread(
                    result.getInt("ThreadID"),
                    result.getString("ThreadName")));
            }

            return thread;
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }
}
