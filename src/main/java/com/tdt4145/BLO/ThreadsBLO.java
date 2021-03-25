package com.tdt4145.BLO;

import java.util.List;

import com.tdt4145.DAO.ThreadsDAO;
import com.tdt4145.Models.Post;
import com.tdt4145.Models.Thread;

public class ThreadsBLO {
    /**
     * Fetches all the posts in a thread
     * @param threadId
     * @return A list of posts in a thread
     */
    public static List<Post> getThreadPosts(int threadId) {
        return ThreadsDAO.getThreadPosts(threadId);
    }

    /**
     * Fetches all the threads in a folder
     * @param folderId
     * @return A list of threads in a folder
     */
    public static List<Thread> GetThreads(int folderId) {
        return ThreadsDAO.GetThreads(folderId);
    }
}
