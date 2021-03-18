package com.tdt4145.Views;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HomeFrame {
    private int userID;
    static JFrame frame = new JFrame("Piazza - Hjem");
    
    static JLabel userLabel = new JLabel();

    public HomeFrame(int userID) {
        this.userID = userID;
        draw();
    }

    private void draw() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLayout(null);

        //Configure components
        userLabel.setBounds(20,20,200,40);
        userLabel.setText(String.format("User ID: %s", userID));

        //Add components to frame
        frame.add(userLabel);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
