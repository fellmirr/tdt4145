package com.tdt4145.BLO;

import java.util.List;

import com.tdt4145.DAO.ThreadsDAO;
import com.tdt4145.Models.Post;

public class ThreadsBLO {
    public static List<Post> getThreadPosts(int threadId) {
        return ThreadsDAO.getThreadPosts(threadId);
    }
}
