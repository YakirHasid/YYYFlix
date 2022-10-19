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

    private JMenu m_Menu;
    private JMenuItem m_Menu_Register;
    private JMenuItem m_Menu_CreateContent;
    private JMenuItem m_Menu_AddContentToYourLibrary;
    private JMenuItem m_Menu_Subscribe;
    private JMenuItem m_Menu_SendNotificationToAUser;    
    private JMenu m_Menu_Print;
    private JMenuItem m_Menu_Print_MyUserDetails;
    private JMenuItem m_Menu_Print_MyLibrary;
    private JMenuItem m_Menu_Print_MySubscriptionDetails;
    private JMenuItem m_Menu_Print_MyNotifications;
    private JMenuItem m_Menu_Print_AllUsers;   
    private JMenuItem m_Menu_Print_AllUsernames;
    private JMenuItem m_Menu_Print_AllContents;
    private JMenuItem m_Menu_Print_AllSubscriptions;      
    private JMenu m_Menu_Change;
    private JMenuItem m_Menu_Change_Name;
    private JMenuItem m_Menu_Change_Password;
    private JMenuItem m_Menu_Change_PaymentMethod;

    private JMenu m_Help;
    private JMenuItem m_Help_About;

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
        m_Menu = new JMenu("Menu");

        m_Menu_Register = new JMenuItem("Register");        
        m_Menu_CreateContent = new JMenuItem("Create Content");
        m_Menu_AddContentToYourLibrary = new JMenuItem("Add Content To Your Library");
        m_Menu_Subscribe = new JMenuItem("Subscribe");
        m_Menu_SendNotificationToAUser = new JMenuItem("Send Notification To A User"); 

        m_Menu_Print = new JMenu("Print");
        m_Menu_Print_MyUserDetails = new JMenuItem("My User Details");   
        m_Menu_Print_MyLibrary = new JMenuItem("My Library");          
        m_Menu_Print_MySubscriptionDetails = new JMenuItem("My Subscription Details");          
        m_Menu_Print_MyNotifications = new JMenuItem("My Notifications");        
        m_Menu_Print_AllUsers = new JMenuItem("All Users");
        m_Menu_Print_AllUsernames = new JMenuItem("All Usernames");
        m_Menu_Print_AllContents = new JMenuItem("All Contents");
        m_Menu_Print_AllSubscriptions = new JMenuItem("All Subscriptions");
        m_Menu_Print.add(m_Menu_Print_MyUserDetails);   
        m_Menu_Print.add(m_Menu_Print_MyLibrary);
        m_Menu_Print.add(m_Menu_Print_MySubscriptionDetails);
        m_Menu_Print.add(m_Menu_Print_MyNotifications);
        m_Menu_Print.add(m_Menu_Print_AllUsers);
        m_Menu_Print.add(m_Menu_Print_AllUsernames);
        m_Menu_Print.add(m_Menu_Print_AllContents);
        m_Menu_Print.add(m_Menu_Print_AllSubscriptions);

        m_Menu_Change = new JMenu("Change");
        m_Menu_Change_Name = new JMenuItem("Name"); 
        m_Menu_Change_Password = new JMenuItem("Password"); 
        m_Menu_Change_PaymentMethod = new JMenuItem("Payment Method");  
        m_Menu_Change.add(m_Menu_Change_Name);
        m_Menu_Change.add(m_Menu_Change_Password);
        m_Menu_Change.add(m_Menu_Change_PaymentMethod);
        
        m_Menu.add(m_Menu_Register);
        m_Menu.add(m_Menu_CreateContent);
        m_Menu.add(m_Menu_AddContentToYourLibrary);
        m_Menu.add(m_Menu_Subscribe);
        m_Menu.add(m_Menu_SendNotificationToAUser);
        m_Menu.add(m_Menu_Print);
        m_Menu.add(m_Menu_Change);

        m_Help = new JMenu("Help");
        m_Help_About = new JMenuItem("About");   
        m_Help.add(m_Help_About);

        mb.add(m_Menu);
        mb.add(m_Help);
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
    
    public JMenuItem getM_Menu_Register() {
        return m_Menu_Register;
    }

    public JMenuItem getM_Menu_CreateContent() {
        return m_Menu_CreateContent;
    }

    public JMenuItem getM_Menu_AddContentToYourLibrary() {
        return m_Menu_AddContentToYourLibrary;
    }

    public JMenuItem getM_Menu_Subscribe() {
        return m_Menu_Subscribe;
    }

    public JMenuItem getM_Menu_SendNotificationToAUser() {
        return m_Menu_SendNotificationToAUser;
    }

    public JMenuItem getM_Menu_Print_MyUserDetails() {
        return m_Menu_Print_MyUserDetails;
    }

    public JMenuItem getM_Menu_Print_MyLibrary() {
        return m_Menu_Print_MyLibrary;
    }

    public JMenuItem getM_Menu_Print_SubscriptionDetails() {
        return m_Menu_Print_MySubscriptionDetails;
    }

    public JMenuItem getM_Menu_Print_Notifications() {
        return m_Menu_Print_MyNotifications;
    }

    public JMenuItem getM_Menu_Print_AllUsers() {
        return m_Menu_Print_AllUsers;
    }

    public JMenuItem getM_Menu_Print_AllUsernames() {
        return m_Menu_Print_AllUsernames;
    }

    public JMenuItem getM_Menu_Print_AllContents() {
        return m_Menu_Print_AllContents;
    }

    public JMenuItem getM_Menu_Print_AllSubscriptions() {
        return m_Menu_Print_AllSubscriptions;
    }

    public JMenuItem getM_Menu_Change_Name() {
        return m_Menu_Change_Name;
    }

    public JMenuItem getM_Menu_Change_Password() {
        return m_Menu_Change_Password;
    }

    public JMenuItem getM_Menu_Change_PaymentMethod() {
        return m_Menu_Change_PaymentMethod;
    }

    public JMenuItem getM_Help_About() {
        return m_Help_About;
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