package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.BLO.UsersBLO;
import com.tdt4145.Models.Post;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class HomeFrame implements ActionListener {
    private int userID;

    static JFrame frame = new JFrame("Piazza - Hjem");

    static JLabel userLabel = new JLabel();

    static JLabel postIdLabel = new JLabel("Post Id:");
    static JTextField postIdField = new JTextField("");
    static JButton postIdButton = new JButton("Get post");

    public HomeFrame(int userID) {
        this.userID = userID;
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

        //Add components to frame
        frame.add(userLabel);

        frame.add(postIdLabel);
        frame.add(postIdField);
        frame.add(postIdButton);

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
    }
}
