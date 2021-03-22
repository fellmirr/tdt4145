package com.tdt4145.BLO;

import java.util.List;

import com.tdt4145.DAO.CoursesDAO;
import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;
import com.tdt4145.Models.Thread;

public class CoursesBLO {
    /**
     * Fetches all the courses a given user participates in
     * @param userId
     * @return A list of courses
     */
    public static List<Course> GetCourses(int userId) {
        return CoursesDAO.GetCourses(userId);
    }

    /**
     * Fetches all the folders in a course
     * @param courseId
     * @return A list of folders
     */
    public static List<Folder> GetFolders(int courseId) {
        return CoursesDAO.GetFolders(courseId);
    }

    /**
     * Fetches all the threads in a folder
     * @param folderId
     * @return A list of threads in a folder
     */
    public static List<Thread> GetThreads(int folderId) {
        return CoursesDAO.GetThreads(folderId);
    }
}
