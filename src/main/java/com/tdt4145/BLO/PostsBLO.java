package com.tdt4145.BLO;

import com.tdt4145.DAO.PostsDAO;
import com.tdt4145.Models.Post;

public class PostsBLO {
    /**
     * Fetch a post by post id.
     * @param postId The id of the post
     * @return Returns the Post if found, or null if not found.
     */
    public static Post GetPost(int postId) {
        return PostsDAO.GetPost(postId);
    }

    /**
     * Fetch a post by post id.
     * @param post The post to reply to
     * @param replyText The text of the reply
     * @param userId The user id of the user replying
     * @return Returns 1 if successfully replied, or 0 if not.
     */
    public static int ReplyToPost(Post post, String replyText, int userId) {
        return PostsDAO.replyToPost(post, replyText, userId);
    }
}
