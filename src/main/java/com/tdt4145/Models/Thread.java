package com.tdt4145.Models;

public class Thread {
    public int ThreadID;
    public int FolderID;
    public String ThreadName;

    public Thread(int threadID, int folderID, String threadName){
        ThreadID = threadID;
        FolderID = folderID;
        ThreadName = threadName;
    }

    public int getId() {
        return ThreadID;
    }
}
