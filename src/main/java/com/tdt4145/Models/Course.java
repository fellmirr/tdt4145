package com.tdt4145.Models;

import java.util.List;

public class Course {
    public int CourseID;
    public String Name;
    public List<String> Terms;

    public Course(int coursId, String name) {
        //TODO: Add Terms as input
        this.CourseID = coursId;
        this.Name = name;
    }
}
