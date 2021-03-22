package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.Models.Course;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.List;


public class HomeFrame {
    private int userID;

    static JFrame frame = new JFrame("Piazza - Hjem");

    public HomeFrame(int userID) {
        this.userID = userID;
        draw();
        populateCoursesList();
    }

    private void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Add components to frame

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void populateCoursesList() {
        final DefaultListModel coursesModel = new DefaultListModel();

        List<Course> userCourses = CoursesBLO.GetCourses(userID);

        for (int i = 0; i < userCourses.size(); i++) {
            coursesModel.addElement(userCourses.get(i).Name);
        }

        final JList courseList = new JList(coursesModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setVisibleRowCount(3);

        courseList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JList list = (JList)e.getSource();
                    int index = list.locationToIndex(e.getPoint());

                    Course selectedCourse = userCourses.get(index);
                    new FoldersFrame(selectedCourse.CourseID, selectedCourse.Name);
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
