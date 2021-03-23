package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.BLO.StatisticsBLO;
import com.tdt4145.BLO.UsersBLO;
import com.tdt4145.Models.Post;
import com.tdt4145.Models.StatisticsList;
import com.tdt4145.Models.User;
import com.tdt4145.Models.UserRole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class HomeFrame implements ActionListener {
    private int userID;
    private User user;

    static JFrame frame = new JFrame("Piazza - Hjem");

    static JLabel userLabel = new JLabel();

    static JLabel postIdLabel = new JLabel("Post Id:");
    static JTextField postIdField = new JTextField("");
    static JButton postIdButton = new JButton("Get Post");

    static JLabel statisticsLabel = new JLabel("Go to Statistics");
    static JButton statisticsButton = new JButton("To Statistics");

    public HomeFrame(int userID) {
        this.userID = userID;
        this.user = UsersBLO.GetUser(userID);
        draw();
    }

    private void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure components
        userLabel.setBounds(20, 20, 200, 40);
        userLabel.setText(String.format("User ID: %s", userID));

        postIdLabel.setBounds(20, 80, 80, 40);
        postIdField.setBounds(100, 80, 180, 40);
        postIdButton.setBounds(300, 80, 200, 40);
        postIdButton.addActionListener(this);

        statisticsLabel.setBounds(20, 140, 150, 40);
        statisticsButton.setBounds(300, 140, 200, 40);
        statisticsButton.addActionListener(this);

        //Add components to frame
        frame.add(userLabel);

        frame.add(postIdLabel);
        frame.add(postIdField);
        frame.add(postIdButton);

        if (user.Role.getValue() == UserRole.Instructor.getValue()) {
            frame.add(statisticsLabel);
            frame.add(statisticsButton);
        }
        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Action performed when button for replying is clicked
        if (e.getSource() == postIdButton) {
            postIdButton.setText("Laster...");

            Post post = PostsBLO.GetPost(Integer.parseInt(postIdField.getText()));

            if (post != null) {
//            Get post successfull
                new ReplyFrame(post, userID);
                postIdButton.setText("Get post");
                frame.setVisible(false);
                frame.dispose();
            } else {
//            Get post unsuccesfull
                showMessageDialog(null, String.format("Could not find post with post id %s", postIdField.getText()));
                postIdButton.setText("Get post");
            }
        }
        // Action performed when statistics button is clicked
        if (e.getSource() == statisticsButton) {
            StatisticsList statistics = StatisticsBLO.GetStatistics();
            Object[][] statisticsInput = changeToTableDataFormat(statistics);
            new StatisticsFrame(statisticsInput);
        }
    }

    /**
     * Converts a StatisticsList to an Object[][] to be used in a JTable in StatisticsFrame
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
