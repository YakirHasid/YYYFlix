import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.*;

public class ViewMenu {
    // View uses Swing framework to display UI to user
    private JFrame frame;
    private JMenuBar mb;

    private JMenu m1;
    private JMenuItem m1_1;
    private JMenuItem m1_2;
    private JMenuItem m1_3;
    private JMenuItem m1_4;
    private JMenuItem m1_5;
    private JMenu m1_6;
    private JMenuItem m1_6_1;
    private JMenuItem m1_6_2;
    private JMenuItem m1_6_3;
    private JMenuItem m1_6_4;    
    private JMenu m1_7;
    private JMenuItem m1_7_1;
    private JMenuItem m1_7_2;
    private JMenuItem m1_7_3;

    private JMenu m2;
    private JMenuItem m2_1;

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

        m1_1 = new JMenuItem("Register");        
        m1_2 = new JMenuItem("Create Content");
        m1_3 = new JMenuItem("Add Content To Your Library");
        m1_4 = new JMenuItem("Subscribe");
        m1_5 = new JMenuItem("Send Notification To A User"); 

        m1_6 = new JMenu("Print");
        m1_6_1 = new JMenuItem("My User Details");   
        m1_6_2 = new JMenuItem("My Library");          
        m1_6_3 = new JMenuItem("Subscription Details");          
        m1_6_4 = new JMenuItem("Notifications");
        m1_6.add(m1_6_1);   
        m1_6.add(m1_6_2);
        m1_6.add(m1_6_3);
        m1_6.add(m1_6_4);

        m1_7 = new JMenu("Change");
        m1_7_1 = new JMenuItem("Name"); 
        m1_7_2 = new JMenuItem("Password"); 
        m1_7_3 = new JMenuItem("Payment Method");  
        m1_7.add(m1_7_1);
        m1_7.add(m1_7_2);
        m1_7.add(m1_7_3);
        
        m1.add(m1_1);
        m1.add(m1_2);
        m1.add(m1_3);
        m1.add(m1_4);
        m1.add(m1_5);
        m1.add(m1_6);
        m1.add(m1_7);

        m2 = new JMenu("Help");
        m2_1 = new JMenuItem("About");   
        m2.add(m2_1);

        mb.add(m1);
        mb.add(m2);
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

    public JMenuItem getMenu1_1() {
        return this.m1_1;
    }

    public JMenuItem getMenu1_2() {
        return this.m1_2;
    }

    public JMenuItem getMenu1_3() {
        return this.m1_3;
    }

    public JMenuItem getMenu1_4() {
        return this.m1_4;
    }

    public JMenuItem getMenu1_5() {
        return this.m1_5;
    }

    public JMenuItem getMenu1_6_1() {
        return this.m1_6_1;
    }

    public JMenuItem getMenu1_6_2() {
        return this.m1_6_2;
    }

    public JMenuItem getMenu1_6_3() {
        return this.m1_6_3;
    }

    public JMenuItem getMenu1_6_4() {
        return this.m1_6_4;
    }
    
    public JMenuItem getMenu1_7_1() {
        return this.m1_7_1;
    }
    
    public JMenuItem getMenu1_7_2() {
        return this.m1_7_2;
    }  

    public JMenuItem getMenu1_7_3() {
        return this.m1_7_3;
    }    
    
    public JMenuItem getMenu2_1() {
        return this.m2_1;
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