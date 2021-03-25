package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;
import com.tdt4145.Models.ListMode;
import com.tdt4145.BLO.UsersBLO;
import com.tdt4145.Models.User;
import com.tdt4145.Models.UserRole;

import java.awt.Cursor;

import com.tdt4145.Models.Thread;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFrame {
    private int userID;
    private User user;
    
    static JFrame frame = new JFrame("Piazza - Hjem");

    /**
     * Main menu buttons
     */
    static JButton searchButton = new JButton("Search");
    static JButton statisticsButton = new JButton("Statistics");
    static JButton makePostButton = new JButton("Create post");

    /**
     * Main list
     */
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private ListMode listMode = ListMode.COURSES;
    private List<Integer> listIds;
    private JList<String> mainList = new JList<String>(listModel);

    /**
     * Breadcrumb related stuff
     */

    private int activeCourseID = -1;
    private int activeFolderID = -1;
    private String activeCourseName = null;
    private String activeFolderName = null;
    static JLabel breadCrumbTop = new JLabel("Courses");
    static JLabel breadCrumbCourseArrow = new JLabel(">");
    static JLabel activeCourseBreadcrumb = new JLabel("");
    static JLabel breadCrumbFolderArrow = new JLabel(">");
    static JLabel activeFolderBreadcrumb = new JLabel("");
    

    static JButton addPostButton = new JButton("Add post");

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

        makePostButton.setBounds(140, 310, 120, 36);
        makePostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MakePostFrame(userID);
            }
        });

        statisticsButton.setBounds(280, 310, 120, 36);
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsFrame();
            }
        });

            //Breadcrumbs
            breadCrumbTop.setBounds(10, 4, 55, 34);
            breadCrumbTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            breadCrumbTop.addMouseListener(new MouseInputListener(){

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    activeCourseID = -1;
                    activeFolderID = -1;
                    activeCourseName = null;
                    activeFolderName = null;
                    setListCourses();
                    updateBreadcrumb();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }
                
            });
            breadCrumbCourseArrow.setBounds(75, 4, 10, 34);
            activeCourseBreadcrumb.setBounds(95, 4, 120, 34);
            activeCourseBreadcrumb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            activeCourseBreadcrumb.addMouseListener(new MouseInputListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    activeFolderID = -1;
                    activeFolderName = null;
                    setListFolders(activeCourseID);
                    updateBreadcrumb();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseDragged(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void mouseMoved(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    
                }
                
            });
            breadCrumbFolderArrow.setBounds(225, 4, 10, 34);
            activeFolderBreadcrumb.setBounds(245, 4, 120, 34);
            activeFolderBreadcrumb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            updateBreadcrumb();


        //Add components to frame
        frame.add(searchButton);
        frame.add(makePostButton);
        if (user.Role == UserRole.Instructor) {
            frame.add(statisticsButton);
        }

            //Breadcrumbs
            frame.add(breadCrumbTop);
            frame.add(breadCrumbCourseArrow);
            frame.add(activeCourseBreadcrumb);
            frame.add(breadCrumbFolderArrow);
            frame.add(activeFolderBreadcrumb);

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
                    
                    switch (listMode) {
                        case COURSES:
                            activeCourseName = listModel.get(index);
                            setListFolders(listIds.get(index));
                            updateBreadcrumb();
                            break;
                        case FOLDERS:
                            activeFolderName = listModel.get(index);
                            setListThreads(listIds.get(index));
                            updateBreadcrumb();
                            break;
                        case THREADS:
                            //TODO: Open thread frame
                            break;
                        default:
                            System.out.println("Unrecognized list mode");
                    }
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

        listIds = userCourses
            .stream()
            .map(Course::getId)
            .collect(Collectors.toList());

        mainList.setVisibleRowCount(userCourses.size());

        listMode = ListMode.COURSES;
    }

    private void setListFolders(int courseId) {
        listModel.clear();
        List<Folder> courseFolders = CoursesBLO.GetFolders(courseId);

        for (int i = 0; i < courseFolders.size(); i++) {
            listModel.addElement(courseFolders.get(i).FolderName);
        }

        listIds = courseFolders
            .stream()
            .map(Folder::getId)
            .collect(Collectors.toList());

        mainList.setVisibleRowCount(courseFolders.size());

        listMode = ListMode.FOLDERS;
        activeCourseID = courseId;
    }

    private void setListThreads(int folderId) {
        listModel.clear();
        List<Thread> folderThreads = CoursesBLO.GetThreads(folderId);

        for (int i = 0; i < folderThreads.size(); i++) {
            listModel.addElement(folderThreads.get(i).Name);
        }

        listIds = folderThreads
            .stream()
            .map(Thread::getId)
            .collect(Collectors.toList());

        mainList.setVisibleRowCount(folderThreads.size());

        activeFolderID = folderId;
        listMode = ListMode.THREADS;
    }

    private void updateBreadcrumb() {
        if (activeCourseID != -1 && activeCourseName != null) {
            breadCrumbCourseArrow.setVisible(true);
            activeCourseBreadcrumb.setText(activeCourseName);
            activeCourseBreadcrumb.setVisible(true);
        } else {
            breadCrumbCourseArrow.setVisible(false);
            activeCourseBreadcrumb.setVisible(false);
        }

        if (activeFolderID != -1 && activeFolderName != null) {
            breadCrumbFolderArrow.setVisible(true);
            activeFolderBreadcrumb.setText(activeFolderName);
            activeFolderBreadcrumb.setVisible(true);
        } else {
            breadCrumbFolderArrow.setVisible(false);
            activeFolderBreadcrumb.setVisible(false);
        }
    }
}
