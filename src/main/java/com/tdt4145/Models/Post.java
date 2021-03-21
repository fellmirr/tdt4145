package com.tdt4145.Models;

import java.util.Date;

public class Post {
    public int PostID;
    public int UserID;
    public int ThreadID;
    public int TagID;
    public Date Posted;
    public String Text;
    public int Answered;
    public int Likes;
    public int Anonymous;

    public Post(int postID, int userID, int threadID, int tagID, Date posted, String text, int answered, int likes, int anonymous) {
        PostID = postID;
        UserID = userID;
        ThreadID = threadID;
        TagID = tagID;
        Posted = posted;
        Text = text;
        Answered = answered;
        Likes = likes;
        Anonymous = anonymous;
    }
}
