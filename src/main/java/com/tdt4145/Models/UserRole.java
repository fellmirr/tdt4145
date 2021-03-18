package com.tdt4145.Models;

public enum UserRole {
    User(1),
    Instructor(2);

    private final int id;
    UserRole(int id) { this.id = id; }
    public int getValue() { return id; }
}