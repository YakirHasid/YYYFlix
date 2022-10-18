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
import java.io.ObjectInputStream.GetField;

public class ViewMenu {
    // View uses Swing framework to display UI to user
    private JFrame frame;
    private JMenuBar mb;
    private JMenu m1;
    private JMenuItem m11;
    private JMenuItem m12;
    private JMenuItem m13;
    private JMenuItem m14;
    private JMenuItem m15;
    private JMenuItem m16;
    private JMenuItem m17;
    private JMenuItem m18;
    private JMenuItem m21;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel connectedUserLabel;
    private JTextField usernameTextfield;
    private JPasswordField passwordTextfield;
    private JTextField connectedUserTextfield;
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
        passwordTextfield = new JPasswordField();
        connectedUserTextfield = new JTextField();
        login = new JButton("Login");
        logout = new JButton("Logout");

        // Creating the MenuBar and adding components
        mb = new JMenuBar();
        m1 = new JMenu("Menu");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        m11 = new JMenuItem("Register");        
        m12 = new JMenuItem("Create Content");
        m13 = new JMenuItem("Add Content To Your Library");
        m14 = new JMenuItem("Subscribe");
        m15 = new JMenuItem("Send Notification To A User"); 
        m16 = new JMenuItem("Print - My Library");          
        m17 = new JMenuItem("Print - Subscription Details");          
        m18 = new JMenuItem("Print - Notifications");          
        m21 = new JMenuItem("About");   
        m1.add(m11);        
        m1.add(m12);
        m1.add(m13);
        m1.add(m14);
        m1.add(m15);        
        m1.add(m16);
        m1.add(m17);
        m1.add(m18);

        m2.add(m21);

        // Add UI element to frame
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(connectedUserLabel)
                        .addComponent(usernameLabel).addComponent(passwordLabel))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(connectedUserTextfield)
                        .addComponent(usernameTextfield).addComponent(passwordTextfield))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(login)
                        .addComponent(logout)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(connectedUserLabel)
                        .addComponent(connectedUserTextfield))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel)
                        .addComponent(usernameTextfield).addComponent(login))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel)
                        .addComponent(passwordTextfield).addComponent(logout)));

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

    public JMenuBar getMb() {
        return mb;
    }

    public void setMb(JMenuBar mb) {
        this.mb = mb;
    }

    public JMenu getMainMenu() {
        return this.m1;
    }

    public JMenuItem getMenu1() {
        return this.m11;
    }

    public JMenuItem getMenu2() {
        return this.m12;
    }

    public JMenuItem getMenu3() {
        return this.m13;
    }

    public JMenuItem getMenu4() {
        return this.m14;
    }

    public JMenuItem getMenu5() {
        return this.m15;
    }

    public JMenuItem getMenu6() {
        return this.m16;
    }
    
    public JMenuItem getMenu7() {
        return this.m17;
    }
    
    public JMenuItem getMenu8() {
        return this.m18;
    }   
    
    public JMenuItem getMenuAbout() {
        return this.m21;
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

    public JPasswordField getPasswordTextfield() {
        return passwordTextfield;
    }

    public void setPasswordTextfield(JPasswordField passwordTextfield) {
        this.passwordTextfield = passwordTextfield;
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