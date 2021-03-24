package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;
import com.tdt4145.BLO.StatisticsBLO;
import com.tdt4145.BLO.UsersBLO;
import com.tdt4145.Models.StatisticsList;
import com.tdt4145.Models.User;
import com.tdt4145.Models.UserRole;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class HomeFrame {
    private int userID;
    private User user;
    private DefaultListModel<String> listModel = new DefaultListModel<String>();

    private JList<String> mainList = new JList<String>(listModel);

    static JFrame frame = new JFrame("Piazza - Hjem");
    static JButton searchButton = new JButton("Search");

    static JLabel userLabel = new JLabel();

    static JLabel postIdLabel = new JLabel("Post Id:");
    static JTextField postIdField = new JTextField("");
    static JButton postIdButton = new JButton("Get Post");

    static JButton statisticsButton = new JButton("Statistics");

    public HomeFrame(int userID) {
        this.userID = userID;
        this.user = UsersBLO.GetUser(userID);
        draw();
        initializeList();
        setListCourses();
    }

    private void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 380);
        frame.setLayout(null);

        //Configure components
        searchButton.setBounds(10, 310, 120, 36);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchFrame(userID);
            }
        });

        statisticsButton.setBounds(140, 310, 120, 36);
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsFrame();
            }
        });

        //Add components to frame
        frame.add(searchButton);
        if (user.Role == UserRole.Instructor) {
            frame.add(statisticsButton);
        }

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void initializeList() {
        mainList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = mainList.locationToIndex(e.getPoint());
                    System.out.println(index);
                    setListFolders(1);
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {}

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {}

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {}

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {}
        });
        mainList.setBounds(10, 42, 580, 260);
        frame.add(mainList);
    }

    private void setListCourses() {
        listModel.clear();
        List<Course> userCourses = CoursesBLO.GetCourses(userID);

        for (int i = 0; i < userCourses.size(); i++) {
            listModel.addElement(userCourses.get(i).Name);
        }

        mainList.setVisibleRowCount(userCourses.size());
    }

    private void setListFolders(int courseId) {
        listModel.clear();
        List<Folder> courseFolders = CoursesBLO.GetFolders(courseId);

        for (int i = 0; i < courseFolders.size(); i++) {
            listModel.addElement(courseFolders.get(i).Name);
        }

        mainList.setVisibleRowCount(courseFolders.size());
    }
}
