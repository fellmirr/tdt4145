package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.Models.Course;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class HomeFrame {
    private int userID;

    static JFrame frame = new JFrame("Piazza - Hjem");
    static JButton searchButton = new JButton("Search");

    public HomeFrame(int userID) {
        this.userID = userID;
        draw();
        populateCoursesList();
    }

    private void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 380);
        frame.setLayout(null);

        //Configure components
        searchButton.setBounds(10, 400, 120, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchFrame(userID);
            }
        });

        //Add components to frame
        frame.add(searchButton);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void populateCoursesList() {
        final DefaultListModel<String> coursesModel = new DefaultListModel<String>();

        List<Course> userCourses = CoursesBLO.GetCourses(userID);

        for (int i = 0; i < userCourses.size(); i++) {
            coursesModel.addElement(userCourses.get(i).Name);
        }

        final JList<String> courseList = new JList<String>(coursesModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setVisibleRowCount(3);

        courseList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JList<String> list = (JList<String>)e.getSource();
                    int index = list.locationToIndex(e.getPoint());

                    Course selectedCourse = userCourses.get(index);
                    new FoldersFrame(selectedCourse.CourseID, selectedCourse.Name);
                    //frame.setVisible(false); 
                    //frame.dispose();
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        
        courseList.setBounds(10, 10, 580, 300);

        frame.add(courseList);
    }
}
