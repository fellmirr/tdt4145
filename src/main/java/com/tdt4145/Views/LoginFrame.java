package com.tdt4145.Views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.tdt4145.BLO.UsersBLO;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginFrame implements ActionListener {
    static JFrame frame = new JFrame("Piazza - Login");

    static JLabel emailLabel = new JLabel("Email:");
    static JTextField emailField = new JTextField("");

    static JLabel passwordLabel = new JLabel("Password:");
    static JPasswordField passwordField = new JPasswordField("");

    static JButton loginButton = new JButton("Login");

    public LoginFrame() {
        draw();
    }

    public void draw() {
        //Exit application when frame closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,240);
        frame.setLayout(null);

        //Configure frame components
        emailLabel.setBounds(20, 20, 80, 40);
        emailField.setBounds(100, 20,180, 40);
        
        passwordLabel.setBounds(20, 80, 80, 40);
        passwordField.setBounds(100, 80, 180, 40);

        loginButton.setBounds(50, 140, 200, 40);
        loginButton.addActionListener(this);

        //Add frame components
        frame.add(emailLabel);
        frame.add(emailField);

        frame.add(passwordLabel);
        frame.add(passwordField);

        frame.add(loginButton);
        
        //Set frame to center of screen
        frame.setLocationRelativeTo(null);

        frame.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loginButton.setText("Laster...");
        
        int userID = UsersBLO.Login(
            emailField.getText(), 
            new String(passwordField.getPassword()));

        if (userID != -1) {
            //Login successfull
            new HomeFrame(userID);

            frame.setVisible(false); 
            frame.dispose();
        } else {
            //Login unsuccesfull
            showMessageDialog(null, "Wrong username / password");
            loginButton.setText("Login");
        }
    }
}
