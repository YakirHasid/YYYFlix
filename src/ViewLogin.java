import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class ViewLogin {
 // View uses Swing framework to display UI to user
 private JFrame frame;
 private JLabel usernameLabel;
 private JLabel passwordLabel;
 private JTextField usernameTextfield;
 private JTextField passwordTextfield;
 private JButton usernameSaveButton;
 private JButton passwordSaveButton;
 private JButton hello;
 private JButton bye;
 public ViewLogin(String title) {
  frame = new JFrame(title);
  frame.getContentPane().setLayout(new BorderLayout());
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setSize(500, 120);
  frame.setLocationRelativeTo(null);
  frame.setVisible(true);
  // Create UI elements
  usernameLabel = new JLabel("Username :");
  passwordLabel = new JLabel("Password :");
  usernameTextfield = new JTextField();
  passwordTextfield = new JTextField();
  usernameSaveButton = new JButton("Save username");
  passwordSaveButton = new JButton("Save password");
  hello = new JButton("Hello!");
  bye = new JButton("Bye!");
  // Add UI element to frame
  GroupLayout layout = new GroupLayout(frame.getContentPane());
  layout.setAutoCreateGaps(true);
  layout.setAutoCreateContainerGaps(true);
  layout.setHorizontalGroup(layout.createSequentialGroup()
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameLabel)
    .addComponent(passwordLabel))
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameTextfield)
    .addComponent(passwordTextfield))
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameSaveButton)
    .addComponent(passwordSaveButton))
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(hello)
    .addComponent(bye)));
  layout.setVerticalGroup(layout.createSequentialGroup()
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel)
    .addComponent(usernameTextfield).addComponent(usernameSaveButton).addComponent(hello))
    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel)
    .addComponent(passwordTextfield).addComponent(passwordSaveButton).addComponent(bye)));
  layout.linkSize(SwingConstants.HORIZONTAL, usernameSaveButton, passwordSaveButton);
  layout.linkSize(SwingConstants.HORIZONTAL, hello, bye);
  frame.getContentPane().setLayout(layout);
 }
 public JFrame getFrame() {
  return frame;
 }
 public void setFrame(JFrame frame) {
  this.frame = frame;
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
 public JButton getHello() {
  return hello;
 }
 public void setHello(JButton hello) {
  this.hello = hello;
 }
 public JButton getBye() {
  return bye;
 }
 public void setBye(JButton bye) {
  this.bye = bye;
 }
}