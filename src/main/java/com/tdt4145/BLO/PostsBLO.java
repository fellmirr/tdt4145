package com.tdt4145.BLO;

import com.tdt4145.DAO.PostsDAO;
import com.tdt4145.Models.Post;

public class PostsBLO {

    public static Post GetPost(int postId) {
        return PostsDAO.GetPost(postId);
    }

    public static int ReplyToPost(Post post, String replyText, int userId) {
        return PostsDAO.replyToPost(post, replyText, userId);
    }
}
