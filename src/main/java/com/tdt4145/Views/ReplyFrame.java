package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.Models.Post;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class ReplyFrame implements ActionListener {
    private int userID;
    public Post post;

    static JFrame frame = new JFrame("Piazza - Reply to post");

    static JLabel originalPostLabel = new JLabel();
    static JTextArea originalPostTextArea = new JTextArea(5, 50);

    static JLabel postReplyLabel = new JLabel("Reply Text:");
    static JTextArea postReplyTextArea = new JTextArea(5,50);
    static JButton postReplyButton = new JButton("Reply");

    public ReplyFrame(Post post, int userID) {
        this.post = post;
        this.userID = userID;
        draw();
    }

    public void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure frame components
        originalPostLabel.setBounds(20, 20, 120, 40);
        originalPostLabel.setText("Post replying to:");
        originalPostTextArea.setBounds(140, 20, 180, 120);
        originalPostTextArea.setText(post.Text);
        originalPostTextArea.setWrapStyleWord(true);
        originalPostTextArea.setLineWrap(true);

        postReplyLabel.setBounds(20, 160, 120, 40);
        postReplyTextArea.setBounds(140, 160,180, 120);
        postReplyTextArea.setWrapStyleWord(true);
        postReplyTextArea.setLineWrap(true);

        postReplyButton.setBounds(50, 300, 200, 40);
        postReplyButton.addActionListener(this);

        //Add components to frame
        frame.add(originalPostLabel);
        frame.add(originalPostTextArea);

        frame.add(postReplyLabel);
        frame.add(postReplyTextArea);
        frame.add(postReplyButton);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        postReplyButton.setText("Laster...");
        int response = PostsBLO.ReplyToPost(this.post, postReplyTextArea.getText(), userID); // Creates reply
        response = PostsBLO.UpdatePostAsAnswered(this.post.PostID); // Updated replied post to be answered
        if (response == 1) {
//            Reply to post successful
            new HomeFrame(userID);
            frame.setVisible(false);
            frame.dispose();
        } else {
//            Reply to post unsuccesful
            showMessageDialog(null, String.format("Could not find post with post id %s", postReplyTextArea.getText()));
            postReplyButton.setText("Reply");
        }
    }
}
