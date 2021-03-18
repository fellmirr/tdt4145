package com.tdt4145.BLO;

import com.tdt4145.DAO.UsersDAO;
import com.tdt4145.Models.User;

public class UsersBLO {
    /**
     * Finds user with email and password in database.
     * @param email The users email
     * @param password The users password
     * @return Returns the ID if found, or -1 if not found.
     */
    public static int Login(String email, String password) {
        return UsersDAO.Login(email, password);
    }
    
    /**
     * Fetch a user by ID
     * @param id
     * @return The user object
     */
    public static User GetUser(int id) {
        return UsersDAO.GetUser(id);    
    }
}
