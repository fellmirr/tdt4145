package com.tdt4145.BLO;

import java.util.List;

import com.tdt4145.DAO.FoldersDAO;
import com.tdt4145.DAO.PostsDAO;
import com.tdt4145.DAO.TagDAO;
import com.tdt4145.DAO.ThreadsDAO;
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
        return PostsDAO.addPost(post.ThreadID, post.TagID, replyText, userId);
    }

    /**
     * Updates a post to be "Answered".
     * @param postId The post id replied to
     * @return Returns 1 if successfully updated, or 0 if not.
     */
    public static int UpdatePostAsAnswered(int postId) {
        return PostsDAO.UpdatePostAsAnswered(postId);
    }

    /**
     * Searches for posts with text matching the query
     * @param searchString
     * @return Returns a list of maximum 10 posts found matching the query
     */
    public static List<Post> searchPosts(String searchString) {
        return PostsDAO.searchPosts(searchString);
    }
    
    /**
     * Creates a post and a thread.
     * Creates folders and tags if the supplied folder and tag is not found in the database.
     * 
     * @param tagName Tag of the post
     * @param userID ID of the author of the post
     * @param text Text in the post
     * @param folderName Name of the folder the post belongs to
     * @param threadName Name of the thread 
     * @return true if successfull, false if it fails
     */
    public static boolean MakePost(String tagName,int userID, String text, String folderName, String threadName){
        /**
         * Note: Ideally this should be a transaction,
         * given that we might want to roll back any changes
         * made to the database if any of the steps fails.
         */

        // Attempt to fetch the folder from the database
        // If the folder does not exist, create a new folder
        // The folder is hard coded to belong to the course TDTD4145
        int folderID = FoldersDAO.getFolderID(folderName);
        if (folderID == -1) 
            folderID = FoldersDAO.addFolder(folderName, 1);

        if (folderID == -1)
            return false;

        // Attempt to fetch the tag ID from the database
        // If the tag does not exist, create a new tag
        int tagID = TagDAO.getTagID(tagName);
        if (tagID ==-1) 
            tagID = TagDAO.addTag(tagName);

        if (tagID == -1)
            return false;

        // Create the thread for which the post belongs to
        int threadID = ThreadsDAO.addThread(threadName, folderID);

        if (threadID == -1)
            return false;

        // Finally create the post
        int result = PostsDAO.addPost(threadID, tagID, text, userID);

        if(result == 1)
            return true;

        return false;
    }
}
