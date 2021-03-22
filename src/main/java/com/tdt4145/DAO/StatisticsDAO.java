package com.tdt4145.DAO;

import com.tdt4145.Models.StatisticsItem;
import com.tdt4145.Models.StatisticsList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StatisticsDAO {
    public static StatisticsList GetStatistics() {
        List<StatisticsItem> temp = new ArrayList<>();
        Database db = Database.getInstance();
        ResultSet result = db.query("SELECT	Email, COUNT(DISTINCT UsersViewedPosts.PostID) AS PostsViewed, " +
                "COUNT(DISTINCT Posts.PostID) AS PostsCreated " +
                "FROM	(Users LEFT OUTER JOIN UsersViewedPosts ON Users.UserID=UsersViewedPosts.UserID) " +
                "LEFT OUTER JOIN Posts ON Users.UserID=Posts.UserID " +
                "GROUP BY	Users.UserID ORDER BY PostsViewed DESC");
        try {
            boolean next = result.next();
            while (next) {
                temp.add(new StatisticsItem(
                        result.getString("Email"),
                        result.getInt("PostsViewed"),
                        result.getInt("PostsCreated")
                ));
                next = result.next();
            }
            if (temp.isEmpty()) {
                return (StatisticsList) Collections.emptyList();
            }
            return new StatisticsList(temp);
        } catch (Exception e) {
            System.out.println("Failed to fetch row from result set");
            return null;
        }
    }
}
