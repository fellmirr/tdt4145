package com.tdt4145.Views;

import com.tdt4145.BLO.CoursesBLO;
import com.tdt4145.BLO.ThreadsBLO;
import com.tdt4145.Models.Course;
import com.tdt4145.Models.Folder;
import com.tdt4145.Models.IContainer;
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
    
    private JFrame frame = new JFrame("Piazza - Hjem");

    /**
     * Main menu buttons
     */
    private JButton searchButton = new JButton("Search");
    private JButton statisticsButton = new JButton("Statistics");
    private JButton makePostButton = new JButton("Create post");

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
    private JLabel breadCrumbTop = new JLabel("Courses");
    private JLabel breadCrumbCourseArrow = new JLabel(">");
    private JLabel breadCrumbActiveCourse = new JLabel("");
    private JLabel breadCrumbFolderArrow = new JLabel(">");
    private JLabel breadCrumbActiveFolder = new JLabel("");

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

            //Setup breadcrumbs
            breadCrumbTop.setBounds(10, 4, 55, 34);
            breadCrumbTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            //When the toplevel breadcrumb is clicked, return to displaying the courses available to the user
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
            //When the active course breadcrump is clicked, return to displaying the folders for that course
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
            //Sets the breadcrumbs to their default state
            updateBreadcrumb();


        //Add components to frame
        frame.add(searchButton);
        frame.add(makePostButton);

        //Statistics are only available to instructors
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

    /**
     * Initializes the main list in the home frame.
     * Adds a listener to the list so that when the user
     * clicks an item in the list, open that specific course, folder
     * or thread.
     */
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
                            new ThreadFrame(listIds.get(index), userID);
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

    /**
     * Fill the main list with the courses available to the user
     */
    private void setListCourses() {
        List<Course> userCourses = CoursesBLO.GetCourses(userID);
        setListItems(userCourses);
        listMode = ListMode.COURSES;
    }

    /**
     * Fill the main list with the folders of a given course
     */
    private void setListFolders(int courseId) {
        List<Folder> courseFolders = CoursesBLO.GetFolders(courseId);
        setListItems(courseFolders);
        listMode = ListMode.FOLDERS;
        activeCourseID = courseId;
    }

    /**
     * Fill the list with the threads in a given folder
     */
    private void setListThreads(int folderId) {
        List<Thread> folderThreads = ThreadsBLO.GetThreads(folderId);
        setListItems(folderThreads);
        activeFolderID = folderId;
        listMode = ListMode.THREADS;
    }

    /**
     * Takes a list of items implementing IContainer interface
     * and populates the list with their names. Also updates
     * the local state variable listIds to reference their IDs
     * @param items A list of items implementing IContainer interface
     */
    private void setListItems(List<? extends IContainer> items) {
        listModel.clear();

        for (int i = 0; i < items.size(); i++) {
            listModel.addElement(items.get(i).getName());
        }

        listIds = items
            .stream()
            .map(IContainer::getId)
            .collect(Collectors.toList());

        mainList.setVisibleRowCount(items.size());
    }

    /**
     * Displays the apropriate breadcrumbs dependent on the local state variables
     * We keep track of wether a certain course has been selected or a certain folder
     * is selected, and show breadcrumbs accordingly
     */
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
