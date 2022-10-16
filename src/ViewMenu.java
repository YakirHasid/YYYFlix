import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewMenu {
    // View uses Swing framework to display UI to user
    private JFrame frame;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel connectedUserLabel;
    private JTextField usernameTextfield;
    private JTextField passwordTextfield;
    private JTextField connectedUserTextfield;
    private JButton usernameSaveButton;
    private JButton passwordSaveButton;
    private JButton login;
    private JButton logout;

    public ViewMenu(String title) {
        frame = new JFrame(title);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 160);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Create UI elements
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");
        connectedUserLabel = new JLabel("Connected User :");
        usernameTextfield = new JTextField();
        passwordTextfield = new JTextField();
        connectedUserTextfield = new JTextField();
        usernameSaveButton = new JButton("Save username");
        passwordSaveButton = new JButton("Save password");
        login = new JButton("Login");
        logout = new JButton("Logout");

        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);

        // Add UI element to frame
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(connectedUserLabel)
                        .addComponent(usernameLabel).addComponent(passwordLabel))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(connectedUserTextfield)
                        .addComponent(usernameTextfield).addComponent(passwordTextfield))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameSaveButton)
                        .addComponent(passwordSaveButton))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(login)
                        .addComponent(logout)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(connectedUserLabel)
                        .addComponent(connectedUserTextfield))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel)
                        .addComponent(usernameTextfield).addComponent(usernameSaveButton).addComponent(login))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel)
                        .addComponent(passwordTextfield).addComponent(passwordSaveButton).addComponent(logout)));

        layout.linkSize(SwingConstants.HORIZONTAL, usernameSaveButton, passwordSaveButton);
        layout.linkSize(SwingConstants.HORIZONTAL, login, logout);
        frame.getContentPane().setLayout(layout);    
        frame.setJMenuBar(mb);    
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getConnectedUserLabel() {
        return connectedUserLabel;
    }

    public void setConnectedUserLabel(JLabel connectedUserLabel) {
        this.connectedUserLabel = connectedUserLabel;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JTextField getConnectedUserTextfield() {
        return connectedUserTextfield;
    }

    public void setConnectedUserTextfield(JTextField connectedUserTextfield) {
        this.connectedUserTextfield = connectedUserTextfield;
    }

    public JTextField getUsernameTextfield() {
        return usernameTextfield;
    }

    public void setUsernameTextfield(JTextField usernameTextfield) {
        this.usernameTextfield = usernameTextfield;
    }

    public JTextField getPasswordTextfield() {
        return passwordTextfield;
    }

    public void setPasswordTextfield(JTextField passwordTextfield) {
        this.passwordTextfield = passwordTextfield;
    }

    public JButton getUsernameSaveButton() {
        return usernameSaveButton;
    }

    public void setUsernameSaveButton(JButton usernameSaveButton) {
        this.usernameSaveButton = usernameSaveButton;
    }

    public JButton getPasswordSaveButton() {
        return passwordSaveButton;
    }

    public void setPasswordSaveButton(JButton passwordSaveButton) {
        this.passwordSaveButton = passwordSaveButton;
    }

    public JButton getLogin() {
        return login;
    }

    public void setLogin(JButton login) {
        this.login = login;
    }

    public JButton getLogout() {
        return logout;
    }

    public void setLogout(JButton logout) {
        this.logout = logout;
    }
}