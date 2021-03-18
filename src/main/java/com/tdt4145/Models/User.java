package com.tdt4145.Models;

import java.util.Date;

public class User {
    public int ID;
    public UserRole Role;
    public String Email;
    public String Password;
    public Date LastLogin;

    public User(int ID, UserRole Role, String Email, String Password, Date LastLogin) {
        this.ID = ID;
        this.Role = Role;
        this.Email = Email;
        this.Password = Password;
        this.LastLogin = LastLogin;
    }
}
