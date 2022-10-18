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
  view.getLogin().addActionListener(e -> pressLogin());
  view.getBye().addActionListener(e -> sayBye());
 }
 private void pressLogin() {
    model.setUsername(view.getUsernameTextfield().getText());
    model.setPassword(view.getPasswordTextfield().getText());
    //JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your password is: " + model.getPassword(), "Info", JOptionPane.INFORMATION_MESSAGE);
 }
 private void sayBye() {
  System.exit(0);
 }
}