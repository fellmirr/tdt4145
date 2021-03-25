package com.tdt4145.DAO;

import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {
    static private Database db = Database.getInstance();

    /**
     * Fetches the courses the given user is participating in
     * @param userId
     * @return A list of courses if successfull, null if fails
     */
    public static List<Course> GetCourses(int userId) {
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

    /**
     * Fetches a list of folders for a given course
     * @param courseId
     * @return A list of folders if sucessfull, null if fails
     */
    public static List<Folder> GetFolders(int courseId) {
        ResultSet result = db.query("SELECT FolderID, CourseID, FolderName FROM Folders WHERE CourseID = " + courseId);
        try {
            List<Folder> folders = new ArrayList<Folder>();

            while(result.next()) {
                folders.add(new Folder(
                    result.getInt("FolderID"),
                    result.getInt("CourseID"),
                    result.getString("FolderName")));
            }

            return folders;
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }
}
