import javax.swing.JOptionPane;
import javax.swing.JTextField;
public class ControllerMenu {
 private ModelMenu model;
 private ViewMenu view;
 public ControllerMenu(ModelMenu m, ViewMenu v) {
  model = m;
  view = v;
  initView();
 }
 public void initView() {
  view.getUsernameTextfield().setText(model.getUsername());
  view.getPasswordTextfield().setText(model.getPassword());
  view.getConnectedUserTextfield().setEditable(false);
  view.getConnectedUserTextfield().setBorder(null);
 }
 public void initController() {
  //view.getUsernameTextfield().addActionListener(e -> saveUsername());
  //view.getPasswordTextfield().addActionListener(e -> savePassword());
  //view.getUsernameSaveButton().addActionListener(e -> saveUsername());
  //view.getPasswordSaveButton().addActionListener(e -> savePassword());
  view.getLogin().addActionListener(e -> pressLogin());
  view.getLogout().addActionListener(e -> pressLogout());
 }
 private void saveUsername() {
  model.setUsername(view.getUsernameTextfield().getText());
  JOptionPane.showMessageDialog(null, "Username saved : " + model.getUsername(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void savePassword() {
  model.setPassword(view.getPasswordTextfield().getText());
  JOptionPane.showMessageDialog(null, "Password saved : " + model.getPassword(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void pressLogin() {
    model.setUsername(view.getUsernameTextfield().getText());
    model.setPassword(view.getPasswordTextfield().getText());
    //JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your password is: " + model.getPassword(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void pressLogout() {
    view.getUsernameTextfield().setText("");
    view.getPasswordTextfield().setText("");
    model.setUsername(view.getUsernameTextfield().getText());
    model.setPassword(view.getPasswordTextfield().getText());

    this.connectedUser("");
    //JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your password is: " + model.getPassword(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void sayBye() {
  System.exit(0);
 }
 public void connectedUser(String username) {
    view.getConnectedUserTextfield().setText(username);    
    model.setConnectedUser(view.getConnectedUserTextfield().getText());
 }
}