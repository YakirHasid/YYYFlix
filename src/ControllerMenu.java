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
      // view.getUsernameTextfield().addActionListener(e -> saveUsername());
      // view.getPasswordTextfield().addActionListener(e -> savePassword());
      // view.getUsernameSaveButton().addActionListener(e -> saveUsername());
      // view.getPasswordSaveButton().addActionListener(e -> savePassword());
      view.getLogin().addActionListener(e -> pressLogin());
      view.getLogout().addActionListener(e -> pressLogout());      
   }

   public void cleanFields() {
      view.getUsernameTextfield().setText("");
      view.getPasswordTextfield().setText("");
      model.setUsername(view.getUsernameTextfield().getText());                  
      model.setPassword(view.getPasswordTextfield().getText());
   }

   public void updateModel() {
      model.setUsername(view.getUsernameTextfield().getText());
      model.setPassword(view.getPasswordTextfield().getText());
   }

   private void pressLogin() {
      updateModel();
      //view.getUsernameTextfield().setText("");
      //view.getPasswordTextfield().setText("");
      // JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your
      // password is: " + model.getPassword(), "Info",
      // JOptionPane.INFORMATION_MESSAGE);
   }

   private void pressLogout() {
      cleanFields();     
      // JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your
      // password is: " + model.getPassword(), "Info",
      // JOptionPane.INFORMATION_MESSAGE);
   }

   public void sayHello() {
      JOptionPane.showMessageDialog(null, "Hello " + model.getConnectedUser() + ", welcome back!", "Info",
            JOptionPane.INFORMATION_MESSAGE);            

      cleanFields();
   }

   public void sayBye() {
      if(!model.getConnectedUser().isEmpty()) {
         JOptionPane.showMessageDialog(null, "Goodbye " + model.getConnectedUser() + ", see you later!", "Info",
         JOptionPane.INFORMATION_MESSAGE);

         model.setConnectedUser("");
         view.getConnectedUserTextfield().setText(model.getConnectedUser());            
      }
      else {
         JOptionPane.showMessageDialog(null, "[ERROR] Can't logout, no user is logged in.", "Info",
         JOptionPane.INFORMATION_MESSAGE);
      }
   }

   public void connectedUser(String username) {
      view.getConnectedUserTextfield().setText(username);
      model.setConnectedUser(view.getConnectedUserTextfield().getText());
   }

   public void sayInvalidUsername() {
      JOptionPane.showMessageDialog(null, "[ERROR] Given username is invalid.", "Info",
      JOptionPane.INFORMATION_MESSAGE); 
   }

   public void sayIncorrectPassword() {
      JOptionPane.showMessageDialog(null, "[ERROR] Incorrect password", "Info",
      JOptionPane.INFORMATION_MESSAGE); 
   }
}