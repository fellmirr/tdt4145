package com.tdt4145.Models;

import java.util.List;

public class Course implements IContainer {
    public int CourseID;
    public String Name;
    public List<String> Terms;

    public Course(int coursId, String name) {
        this.CourseID = coursId;
        this.Name = name;
    }

    public int getId() {
        return CourseID;
    }

    public String getName() {
        return Name;
    }
}
