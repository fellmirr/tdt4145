package com.tdt4145.Views;

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

    private Object[][] statistics;

    DefaultTableModel model = new DefaultTableModel();

    static JFrame frame = new JFrame("Piazza - Statistics");

    static JTable statisticsTable = new JTable();


    public StatisticsFrame(Object[][] statistics) {
        this.statistics = statistics;
        model.setDataVector(statistics, columnNames);
        draw();
    }

    private void draw() {
        //Exit application when frame closed
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        //Configure components
        model.setColumnIdentifiers(columnNames);
        statisticsTable.setModel(model);
        statisticsTable.setFillsViewportHeight(true);


        frame.add(statisticsTable.getTableHeader(), BorderLayout.PAGE_START);
        frame.add(statisticsTable, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

}
