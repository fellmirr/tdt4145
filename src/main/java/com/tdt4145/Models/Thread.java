package com.tdt4145.Models;

public class Thread {
    public int ThreadID;
    public String Name;

    public Thread(int threadId, String name) {
        //TODO: Add Terms as input
        this.ThreadID = threadId;
        this.Name = name;
    }

    public int getId() {
        return ThreadID;
    }
}
