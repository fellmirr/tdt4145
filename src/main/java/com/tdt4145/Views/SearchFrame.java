package com.tdt4145.Views;

import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.Models.Post;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchFrame {
    private JFrame frame = new JFrame("Piazza - search");
    private JTextField searchInput = new JTextField("");
    private JButton searchButton = new JButton("Search");

    DefaultListModel<String> resultsModel = new DefaultListModel<String>();
    JList<String> searchResultList = new JList<String>(resultsModel);

    public SearchFrame(int userId) {
        draw();
    }

    private void draw() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure components
        searchInput.setBounds(10,10, 450, 40);
        searchButton.setBounds(470, 10, 120, 40);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        searchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultList.setBounds(10, 60, 580, 300);

        //Add components to frame
        frame.add(searchInput);
        frame.add(searchButton);
        frame.add(searchResultList);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void search() {
        resultsModel.clear();
        List<Post> searchResult = PostsBLO.searchPosts(searchInput.getText());

        for (int i = 0; i < searchResult.size(); i++) {
            resultsModel.addElement(String.format("%s", searchResult.get(i).PostID));
        }

        if (searchResult.size() == 0)
            resultsModel.addElement("No results");
        
        searchResultList.setVisibleRowCount(searchResult.size());
    }
}
