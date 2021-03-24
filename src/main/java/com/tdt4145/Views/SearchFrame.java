package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.Models.Post;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchFrame {
    static JFrame frame = new JFrame("Piazza - search");
    static JTextField searchInput = new JTextField("");
    static JButton searchButton = new JButton("Search");

    public SearchFrame(int userId) {
        draw();
    }

    private void draw() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure components
        searchInput.setBounds(10,10, 480, 40);
        searchButton.setBounds(10, 500, 40, 60);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        //Add components to frame
        frame.add(searchInput);
        frame.add(searchButton);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void search() {
        final DefaultListModel<String> resultsModel = new DefaultListModel<String>();

        List<Post> searchResult = PostsBLO.searchPosts(searchInput.getText());

        for (int i = 0; i < searchResult.size(); i++) {
            resultsModel.addElement(String.format("%s", searchResult.get(i).PostID));
        }

        final JList<String> searchResultList = new JList<String>(resultsModel);
        searchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultList.setVisibleRowCount(3);
        
        searchResultList.setBounds(10, 60, 580, 300);

        frame.add(searchResultList);
    }
}
