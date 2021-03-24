package com.tdt4145.Views;

import com.tdt4145.BLO.StatisticsBLO;
import com.tdt4145.Models.StatisticsList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatisticsFrame {
    final static String[] columnNames = {
        "User Email",
        "Posts Viewed",
        "Posts Created"
    };

    DefaultTableModel model = new DefaultTableModel();
    static JFrame frame = new JFrame("Piazza - Statistics");
    static JTable statisticsTable = new JTable();

    public StatisticsFrame() {
        draw();
        loadStatistics();
    }

    private void loadStatistics() {
        StatisticsList statistics = StatisticsBLO.GetStatistics();
        Object[][] statisticsTableData = changeToTableDataFormat(statistics);
        model.setDataVector(statisticsTableData, columnNames);
        statisticsTable.setModel(model);
    }

    private void draw() {
        //Exit application when frame closed
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        //Configure components
        model.setColumnIdentifiers(columnNames);
        statisticsTable.setFillsViewportHeight(true);

        //Add components to frame
        frame.add(statisticsTable.getTableHeader(), BorderLayout.PAGE_START);
        frame.add(statisticsTable, BorderLayout.CENTER);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    /**
     * Converts a StatisticsList to an Object[][] to be used in a JTable
     *
     * @param statistics StatisticsList to convert to an Object[][]
     * @return An Object[][] of the statistics
     */
    private Object[][] changeToTableDataFormat(StatisticsList statistics) {
        Object[][] o = new Object[statistics.statistics.size()][3];
        for (int i = 0; i < statistics.statistics.size(); i++) {
            o[i][0] = statistics.statistics.get(i).Email;
            o[i][1] = statistics.statistics.get(i).PostsViewed;
            o[i][2] = statistics.statistics.get(i).PostsCreated;
        }
        return o;
    }
}
