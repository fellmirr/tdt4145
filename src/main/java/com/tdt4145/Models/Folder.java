package com.tdt4145.Models;

public class Folder {
    public int FolderID;
    public String Name;

    public Folder(int folderId, String name) {
        this.FolderID = folderId;
        this.Name = name;
    }

    public int getId() {
        return FolderID;
    }
}
