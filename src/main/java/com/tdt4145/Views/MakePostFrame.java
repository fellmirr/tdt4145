package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.Models.Post;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class MakePostFrame implements ActionListener {
    private int userID;
    public Post post;

    private JFrame frame = new JFrame("Piazza - Make new post");

    private JLabel postLabel = new JLabel();
    private JTextArea postTextArea = new JTextArea(5, 50);

    private JLabel tagLabel = new JLabel();
    private JTextArea tagTextArea = new JTextArea(5, 10);

    private JLabel folderLabel = new JLabel();
    private JTextArea folderTextArea = new JTextArea(5, 50);

    private JLabel threadLabel = new JLabel();
    private JTextArea threadTextArea = new JTextArea(5, 50);


    private JButton postButton = new JButton("Post");

    public MakePostFrame(int userID) {
        this.userID = userID;
        draw();
    }

    public void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure frame components
        postLabel.setBounds(20, 10, 120, 40);
        postLabel.setText("Post text:");

        postTextArea.setBounds(20, 50, 560, 200);
        postTextArea.setWrapStyleWord(true);
        postTextArea.setLineWrap(true);

        tagLabel.setBounds(20, 266, 120, 40);
        tagLabel.setText("Tag:");
        tagTextArea.setBounds(60, 272, 100, 30);
        tagTextArea.setWrapStyleWord(true);
        tagTextArea.setLineWrap(true);

        folderLabel.setBounds(170, 266, 120, 40);
        folderLabel.setText("Folder: ");
        folderTextArea.setBounds(230, 272, 100, 30);
        folderTextArea.setWrapStyleWord(true);
        folderTextArea.setLineWrap(true);
        
        threadLabel.setBounds(340, 266, 120, 40);
        threadLabel.setText("Thread: ");
        threadTextArea.setBounds(400, 272, 100, 30);
        threadTextArea.setWrapStyleWord(true);
        threadTextArea.setLineWrap(true);

        postButton.setBounds(460, 320, 120, 40);
        postButton.addActionListener(this);

        //Add components to frame
    

        frame.add(postLabel);
        frame.add(postTextArea);
        frame.add(postButton);
        frame.add(tagLabel);
        frame.add(tagTextArea);
        frame.add(folderLabel);
        frame.add(folderTextArea);
        frame.add(threadLabel);
        frame.add(threadTextArea);
        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        postButton.setText("Laster...");

        boolean response = PostsBLO.MakePost(tagTextArea.getText(), this.userID, postTextArea.getText(),folderTextArea.getText(),threadTextArea.getText());

        if (response == true) {
            // Make post successfull
            System.out.println("Post successfully added to DB");
            new HomeFrame(userID);
            frame.setVisible(false);
            frame.dispose();
        } else {
            // Make post unsuccesfull
            showMessageDialog(null, String.format("You must fill out all the boxes"));
            postButton.setText("Post");
        }
    }
}

