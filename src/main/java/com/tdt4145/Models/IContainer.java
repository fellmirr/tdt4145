package com.tdt4145.Models;

/**
 * An interface for all the database entities that contain other entities in
 * the piazza hieararchy, f. ex. courses contain folders. Folders contains threads.
 * Threads contain posts.
 */
public interface IContainer {
    public int getId();
    public String getName();
}
