package com.tdt4145.DAO;

import java.sql.ResultSet;

import com.tdt4145.Models.User;
import com.tdt4145.Models.UserRole;

public class UsersDAO {
    static private Database db = Database.getInstance();

    /**
     * Fetches a user with a given ID from the database
     * @param id
     * @return A User object if found, null otherwise
     */
    public static User GetUser(int id) {
        ResultSet result = db.query("SELECT * FROM Users WHERE UserID = " + id);
        try {
            boolean next = result.next();
            if (next) {
                //User is found in result set
                int RoleID = result.getInt("RoleID");

                //Default to User role
                UserRole role = UserRole.User;
                if (RoleID == UserRole.User.getValue())
                    role = UserRole.User;
                else if (RoleID == UserRole.Instructor.getValue())
                    role = UserRole.Instructor;
                else
                    System.out.println("Unknown UserRole ID " + RoleID);

                return new User(
                    result.getInt("UserID"),
                    role,
                    result.getString("Email"),
                    result.getString("Password"),
                    result.getDate("LastLogin")); 
            } else {
                System.out.println("No user found with ID " + id);
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }

    /**
     * Checks if there exists any records in the database for a user
     * with the given email and password
     * @param email
     * @param password
     * @return The ID of the user if they exist, -1 otherwise
     */
    public static int Login(String email, String password) {
        String query = String.format("SELECT * FROM Users WHERE Email = '%s' AND Password = '%s'", email, password);
        ResultSet result = db.query(query);
        try {
            boolean next = result.next();
            if (next) {
                //User is found in result set
                return result.getInt("UserID");
            } else {
                System.out.println(String.format("No user found with username %s and password %s", email, password));
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return -1;
        }
    }
}
