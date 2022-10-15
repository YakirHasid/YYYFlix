import javax.swing.JOptionPane;
public class ControllerLogin {
 private ModelLogin model;
 private ViewLogin view;
 public ControllerLogin(ModelLogin m, ViewLogin v) {
  model = m;
  view = v;
  initView();
 }
 public void initView() {
  view.getUsernameTextfield().setText(model.getUsername());
  view.getPasswordTextfield().setText(model.getPassword());
 }
 public void initController() {
  //view.getUsernameTextfield().addActionListener(e -> saveUsername());
  //view.getPasswordTextfield().addActionListener(e -> savePassword());
  //view.getUsernameSaveButton().addActionListener(e -> saveUsername());
  //view.getPasswordSaveButton().addActionListener(e -> savePassword());
  view.getHello().addActionListener(e -> sayHello());
  view.getBye().addActionListener(e -> sayBye());
 }
 private void saveUsername() {
  model.setUsername(view.getUsernameTextfield().getText());
  JOptionPane.showMessageDialog(null, "Username saved : " + model.getUsername(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void savePassword() {
  model.setPassword(view.getPasswordTextfield().getText());
  JOptionPane.showMessageDialog(null, "Password saved : " + model.getPassword(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void sayHello() {
    model.setUsername(view.getUsernameTextfield().getText());
    model.setPassword(view.getPasswordTextfield().getText());
  //JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your password is: " + model.getPassword(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void sayBye() {
  System.exit(0);
 }
}