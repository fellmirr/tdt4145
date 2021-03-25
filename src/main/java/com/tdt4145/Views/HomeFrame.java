package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.BLO.ThreadsBLO;
import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;
import com.tdt4145.Models.ListMode;
import com.tdt4145.BLO.UsersBLO;
import com.tdt4145.Models.User;
import com.tdt4145.Models.UserRole;

import java.awt.Cursor;

import com.tdt4145.Models.Thread;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
    static JLabel breadCrumbActiveCourse = new JLabel("");
    static JLabel breadCrumbFolderArrow = new JLabel(">");
    static JLabel breadCrumbActiveFolder = new JLabel("");
    

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

        statisticsButton.setBounds(270, 310, 120, 36);
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticsFrame();
            }
        });

            //Breadcrumbs
            breadCrumbTop.setBounds(10, 4, 55, 34);
            breadCrumbTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            breadCrumbTop.addMouseListener(new MouseInputAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    activeCourseID = -1;
                    activeFolderID = -1;
                    activeCourseName = null;
                    activeFolderName = null;
                    setListCourses();
                    updateBreadcrumb();
                }
            });
            breadCrumbCourseArrow.setBounds(75, 4, 10, 34);
            breadCrumbActiveCourse.setBounds(95, 4, 120, 34);
            breadCrumbActiveCourse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            breadCrumbActiveCourse.addMouseListener(new MouseInputAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    activeFolderID = -1;
                    activeFolderName = null;
                    setListFolders(activeCourseID);
                    updateBreadcrumb();
                }
            });
            breadCrumbFolderArrow.setBounds(225, 4, 10, 34);
            breadCrumbActiveFolder.setBounds(245, 4, 120, 34);
            breadCrumbActiveFolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            frame.add(breadCrumbActiveCourse);
            frame.add(breadCrumbFolderArrow);
            frame.add(breadCrumbActiveFolder);

        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void initializeList() {
        mainList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainList.addMouseListener(new MouseInputAdapter(){
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
                            new ThreadFrame(listIds.get(index));
                            break;
                        default:
                            System.out.println("Unrecognized list mode");
                    }
                }
            }
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
        List<Thread> folderThreads = ThreadsBLO.GetThreads(folderId);

        for (int i = 0; i < folderThreads.size(); i++) {
            listModel.addElement(folderThreads.get(i).ThreadName);
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
            breadCrumbActiveCourse.setText(activeCourseName);
            breadCrumbActiveCourse.setVisible(true);
        } else {
            breadCrumbCourseArrow.setVisible(false);
            breadCrumbActiveCourse.setVisible(false);
        }

        if (activeFolderID != -1 && activeFolderName != null) {
            breadCrumbFolderArrow.setVisible(true);
            breadCrumbActiveFolder.setText(activeFolderName);
            breadCrumbActiveFolder.setVisible(true);
        } else {
            breadCrumbFolderArrow.setVisible(false);
            breadCrumbActiveFolder.setVisible(false);
        }
    }
}
