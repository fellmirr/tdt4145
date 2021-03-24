package com.tdt4145.BLO;

import com.tdt4145.DAO.StatisticsDAO;
import com.tdt4145.Models.StatisticsList;

public class StatisticsBLO {

    /**
     * Fetch statistics.
     * @return Returns the Statistics as a List if found, or null if not found.
     */
    public static StatisticsList GetStatistics() {
        return StatisticsDAO.GetStatistics();
    }
}
