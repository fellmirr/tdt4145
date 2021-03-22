package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.Models.Thread;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.List;

public class ThreadsFrame {
    private int folderId;
    private String courseName;
    static JFrame frame = new JFrame("Piazza - ");
    static JLabel courseLabel = new JLabel("");

    public ThreadsFrame(int folderId, String courseName) {
        this.folderId = folderId;
        this.courseName = courseName;
        draw();
        populateFolderList();
    }

    private void draw() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null);

        //Configure components
        courseLabel.setText(this.courseName);
        courseLabel.setBounds(10,10, 480, 40);

        frame.setTitle("Piazza - " + this.courseName);

        //Add components to frame
        frame.add(courseLabel);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void populateFolderList() {
        final DefaultListModel threadsModel = new DefaultListModel();

        List<Thread> folderThreads = CoursesBLO.GetThreads(this.folderId);

        for (int i = 0; i < folderThreads.size(); i++) {
            threadsModel.addElement(folderThreads.get(i).Name);
        }

        final JList threadList = new JList(threadsModel);
        threadList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        threadList.setVisibleRowCount(3);

        threadList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JList list = (JList)e.getSource();
                    int index = list.locationToIndex(e.getPoint());
                    System.out.println(folderThreads.get(index).Name);
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
        
        threadList.setBounds(10, 60, 580, 300);

        frame.add(threadList);
    }
}
