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

    static JFrame frame = new JFrame("Piazza - Make new post");

    static JLabel postLabel = new JLabel();
    static JTextArea postTextArea = new JTextArea(5, 50);

    static JLabel tagLabel = new JLabel();
    static JTextArea tagTextArea = new JTextArea(5, 10);

    static JLabel folderLabel = new JLabel();
    static JTextArea folderTextArea = new JTextArea(5, 50);

    static JLabel threadLabel = new JLabel();
    static JTextArea threadTextArea = new JTextArea(5, 50);


    static JButton postButton = new JButton("Post");

    public MakePostFrame(int userID) {
        this.userID = userID;
        draw();
    }

    public void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure frame components
        postLabel.setBounds(20, 20, 120, 40);
        postLabel.setText("Post a question");
        postTextArea.setBounds(140, 20, 180, 120);
        postTextArea.setWrapStyleWord(true);
        postTextArea.setLineWrap(true);

        tagLabel.setBounds(340, 10, 120, 40);
        tagLabel.setText("Tag; ");
        tagTextArea.setBounds(390, 20, 100, 30);
        tagTextArea.setWrapStyleWord(true);
        tagTextArea.setLineWrap(true);

        folderLabel.setBounds(340, 50, 120, 40);
        folderLabel.setText("Folder: ");
        folderTextArea.setBounds(390, 65, 100, 30);
        folderTextArea.setWrapStyleWord(true);
        folderTextArea.setLineWrap(true);
        
        threadLabel.setBounds(340, 105, 120, 40);
        threadLabel.setText("Thread: ");
        threadTextArea.setBounds(390, 110, 100, 30);
        threadTextArea.setWrapStyleWord(true);
        threadTextArea.setLineWrap(true);
        
        
        

        postButton.setBounds(50, 300, 200, 40);
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
    //public static boolean MakePost(String tagName,int userID, String text, String folderName, String threadName)
    

    @Override
    public void actionPerformed(ActionEvent e) {
        postButton.setText("Laster...");

        boolean response = PostsBLO.MakePost(tagTextArea.getText(), this.userID, postTextArea.getText(),folderTextArea.getText(),threadTextArea.getText());

        if (response == true) {
//            Make post successfull
            System.out.println("Post successfully added to DB");
            new HomeFrame(userID);
            frame.setVisible(false);
            frame.dispose();
        } else {
//            Make post unsuccesfull
            showMessageDialog(null, String.format("You must fill out all the boxes"));
            postButton.setText("Post");
        }
    }
}

