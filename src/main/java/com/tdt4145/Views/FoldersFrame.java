package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.BLO.PostsBLO;
import com.tdt4145.Models.Folder;
import com.tdt4145.Models.Post;

import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class FoldersFrame {
    private int courseId;
    private String courseName;
    static JFrame frame = new JFrame("Piazza - ");
    static JLabel courseLabel = new JLabel("");

    public FoldersFrame(int courseId, String courseName) {
        this.courseId = courseId;
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
        final DefaultListModel foldersModel = new DefaultListModel();

        List<Folder> courseFolders = CoursesBLO.GetFolders(this.courseId);

        for (int i = 0; i < courseFolders.size(); i++) {
            foldersModel.addElement(courseFolders.get(i).Name);
        }

        final JList folderList = new JList(foldersModel);
        folderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        folderList.setVisibleRowCount(3);

        folderList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JList list = (JList)e.getSource();
                    int index = list.locationToIndex(e.getPoint());
                    
                    Folder selectedFolder = courseFolders.get(index);
                    new ThreadsFrame(selectedFolder.FolderID, selectedFolder.Name);
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
        
        folderList.setBounds(10, 60, 580, 300);

        frame.add(folderList);
    }
}
