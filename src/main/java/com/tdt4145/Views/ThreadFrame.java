package com.tdt4145.Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tdt4145.BLO.ThreadsBLO;
import com.tdt4145.Models.Post;

public class ThreadFrame {
    private int threadId;
    static JFrame frame = new JFrame("Piazza - Thread");
    static JScrollPane scrollPane = new JScrollPane();

    public ThreadFrame(int threadId) {
        this.threadId = threadId;
        draw();
        printPosts();
    }

    public void draw() {
        //Exit application when frame closed
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setMinimumSize(new Dimension(420, 500));

        //Configure frame components
        scrollPane.setBounds(0,0,420,600);

        //Add frame components
        frame.add(scrollPane);
        
        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true); 
    }

    private void printPosts() {
        List<Post> posts = ThreadsBLO.getThreadPosts(threadId);
        JPanel threadPanel = new JPanel();
        threadPanel.setLayout(new BoxLayout(threadPanel, BoxLayout.Y_AXIS));
        scrollPane.getViewport().add(threadPanel);
        scrollPane.setSize(new Dimension(420, 600));
        scrollPane.setMaximumSize(new Dimension(420, 1600));
        for (Post post : posts) {
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

            JTextArea textArea = new JTextArea(post.Text);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setOpaque(false);
            

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
            buttonPanel.setSize(new Dimension(420, 40));

            JButton answerButton = new JButton("Answer");
            answerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReplyFrame(post, 1);
                }
            });

            buttonPanel.add(answerButton);

            postPanel.add(textArea);
            postPanel.add(buttonPanel);

            threadPanel.add(postPanel);

            System.out.println(post.Text);
        }

        frame.pack();
    }
}
