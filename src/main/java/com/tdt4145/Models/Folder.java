package com.tdt4145.Models;

public class Folder implements IContainer {
    public int FolderID;
    public int CourseID;
    public String FolderName;

    public Folder(int folderID,int courseID,String folderName){
        FolderID = folderID;
        CourseID = courseID;
        FolderName = folderName;

    }
    
    public int getId() {
        return FolderID;
    }

    public String getName() {
        return FolderName;
    }
}
