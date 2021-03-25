package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.Models.Post;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class ReplyFrame implements ActionListener {
    private int userID;
    private Post post;
    private ThreadFrame originFrame;

    private JFrame frame = new JFrame("Piazza - Reply to post");

    private JLabel originalPostLabel = new JLabel();
    private JTextArea originalPostTextArea = new JTextArea(5, 50);

    private JLabel postReplyLabel = new JLabel("Reply Text:");
    private JTextArea postReplyTextArea = new JTextArea(5,50);
    private JButton postReplyButton = new JButton("Reply");

    public ReplyFrame(Post post, int userID, ThreadFrame originFrame) {
        this.post = post;
        this.userID = userID;
        this.originFrame = originFrame;
        draw();
    }

    public void draw() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            // Reply to post successful
            originFrame.printPosts();
            frame.setVisible(false);
            frame.dispose();
        } else {
            // Reply to post unsuccesful
            showMessageDialog(null, String.format("Could not find post with post id %s", postReplyTextArea.getText()));
            postReplyButton.setText("Reply");
        }
    }
}
