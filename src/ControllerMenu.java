import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;

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
      view.getMenu1().addActionListener(e -> pressMenu());
      view.getMenu2().addActionListener(e -> pressMenu());
      view.getMenu3().addActionListener(e -> pressMenu());
      view.getMenu4().addActionListener(e -> pressMenu());
      view.getMenu5().addActionListener(e -> pressMenu());
      view.getLogin().addActionListener(e -> pressLogin());
      view.getLogout().addActionListener(e -> pressLogout());   
      view.getMenuAbout().addActionListener(e -> pressMenuAbout());   
   }

   private void pressMenuAbout() {
      String message = "YYYFlix inc." + "\n" +
                       "Developed by: " + "\n" +
                       "Yuval Yacobi," + " AKA 'Gangster'" + "\n" +
                       "Yosi Yishaev," + " AKA 'Begemot'" + "\n" +
                       "Yakir Hasid," + " AKA '// TODO: Implement'" + "\n";
      JOptionPane.showMessageDialog(null, message, "Credits", JOptionPane.INFORMATION_MESSAGE);      
  }

   private void pressMenu() {
       JOptionPane.showMessageDialog(null, "Check Console Command For Further Actions.", "Info", JOptionPane.INFORMATION_MESSAGE);      
   }

   public void cleanFields() {
      view.getUsernameTextfield().setText("");
      view.getPasswordTextfield().setText("");
      updateModel();
   }

   public void updateModel() {
      model.setUsername(view.getUsernameTextfield().getText());
      model.setPassword(String.valueOf(view.getPasswordTextfield().getPassword()));
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

   public void sayNotLoggedIn() {
      JOptionPane.showMessageDialog(null, "[ERROR] Please Login before using the system.", "Info",
      JOptionPane.INFORMATION_MESSAGE); 
   }

   public void returnToGUIMessage() {
      JOptionPane.showMessageDialog(null, "Returned to GUI, select an action from the menu.", "Info",
      JOptionPane.INFORMATION_MESSAGE);       
   }

   public void returnToGUIMessage(String message) {
      JOptionPane.showMessageDialog(null, message, "Info",
      JOptionPane.INFORMATION_MESSAGE);       
   }
}