package com.tdt4145.Views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;  
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tdt4145.BLO.ThreadsBLO;
import com.tdt4145.Models.Post;

public class ThreadFrame {
    private int threadId;
    private int userId;
    private JFrame frame = new JFrame("Piazza - Thread");
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel threadPanel;

    public ThreadFrame(int threadId, int userId) {
        this.threadId = threadId;
        this.userId = userId;
        draw();
        printPosts();
    }

    public void draw() {
        //Exit application when frame closed
        frame.setLayout(null);
        frame.setMinimumSize(new Dimension(440, 500));
        frame.setMaximumSize(new Dimension(440, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Configure frame components
        scrollPane.setBounds(0, 0, 440, 480);
        scrollPane.setMaximumSize(new Dimension(440, 480));
        scrollPane.setAlignmentY(JScrollPane.TOP_ALIGNMENT);
        
        //Add frame components
        frame.add(scrollPane);
        
        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true); 
    }

    public void printPosts() {
        if (threadPanel != null)
            scrollPane.remove(threadPanel);

        List<Post> posts = ThreadsBLO.getThreadPosts(threadId);
        threadPanel = new JPanel();

        threadPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        scrollPane.getViewport().add(threadPanel);
        
        for (Post post : posts) {
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

            JTextArea textArea = new JTextArea(post.Text);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setOpaque(false);
            textArea.setPreferredSize(new Dimension(420, 20));
            

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setSize(new Dimension(420, 40));

            JButton answerButton = new JButton("Answer");

            ThreadFrame frame = this;
            answerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ReplyFrame(post, userId, frame);
                }
            });

            buttonPanel.add(answerButton);

            postPanel.add(textArea);
            postPanel.add(buttonPanel);

            gbc.gridy = ++gbc.gridy;
            threadPanel.add(postPanel, gbc);
        }

        scrollPane.setBounds(0,0,440,480);
    }
}
