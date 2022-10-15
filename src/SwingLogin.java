import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SwingLogin extends Frame {
    private TextField pelet;
    private Button chPelet;
    private Button bexit;
    int count = 0;
    SwingLogin(String koteret) {
    super(koteret);
    setSize(300,200);
    pelet = new TextField();
    chPelet = new Button("lchatz");
    bexit = new Button("exit");
    setLayout(new FlowLayout());
    add(pelet); add(chPelet); add(bexit);
    LoginPressListener acl = new LoginPressListener();
    chPelet.addActionListener(acl);
    //CloseListen ccl = new CloseListen();
    //bexit.addActionListener(ccl);
    }

    private class LoginPressListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
        pelet.setText(count+" is an even number");
        }
    }
} 