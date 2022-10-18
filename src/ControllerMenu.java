import javax.swing.JOptionPane;

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
      view.getMenu1_1().addActionListener(e -> pressMenu());
      view.getMenu1_2().addActionListener(e -> pressMenu());
      view.getMenu1_3().addActionListener(e -> pressMenu());
      view.getMenu1_4().addActionListener(e -> pressMenu());
      view.getMenu1_5().addActionListener(e -> pressMenu());
      view.getMenu1_7_1().addActionListener(e -> pressMenu());
      view.getMenu1_7_2().addActionListener(e -> pressMenu());
      view.getMenu1_7_3().addActionListener(e -> pressMenu());
      view.getLogin().addActionListener(e -> pressLogin());
      view.getLogout().addActionListener(e -> pressLogout());   
      view.getMenu2_1().addActionListener(e -> pressMenuAbout());   
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
   }

   private void pressLogout() {
      cleanFields();     
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