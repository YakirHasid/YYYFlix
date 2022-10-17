import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCreateContent {
    // View uses Swing framework to display UI to user
    private JFrame frame;
    private JLabel contentTypeLabel;
    private JLabel formatLabel;
    private JLabel subtitlesFileNameLabel;
    private JLabel nameLabel;
    private JLabel lengthLabel;
    private JTextField contentTypeTextfield;
    private JTextField formatTextfield;
    private JTextField subtitlesFileNameTextfield;
    private JTextField nameTextfield;
    private JTextField lengthTextfield;
    //private JButton usernameSaveButton;
    //private JButton passwordSaveButton;
    private JButton submit;
    private JButton reset;

    public ViewCreateContent(String title) {
        frame = new JFrame(title);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 160);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Create UI elements
        contentTypeLabel = new JLabel("Content Type :");
        formatLabel = new JLabel("Format :");
        subtitlesFileNameLabel = new JLabel("Subtitles File Name :");
        nameLabel = new JLabel("Name :");
        lengthLabel = new JLabel("Length (Minutes) :");
        contentTypeTextfield = new JTextField();
        formatTextfield = new JTextField();
        subtitlesFileNameTextfield = new JTextField();
        nameTextfield = new JTextField();
        lengthTextfield = new JTextField();
        //usernameSaveButton = new JButton("Save username");
        //passwordSaveButton = new JButton("Save password");
        submit = new JButton("Submit");
        reset = new JButton("Reset");

        // Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Menu");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Login/Logout");
        JMenuItem m12 = new JMenuItem("Create Content");
        JMenuItem m13 = new JMenuItem("Add Content To Library");
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);

        // Add UI element to frame
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(contentTypeLabel)
                    .addComponent(formatLabel)
                    .addComponent(subtitlesFileNameLabel)
                    .addComponent(nameLabel)
                    .addComponent(lengthLabel)
                    .addComponent(submit))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(contentTypeTextfield)
                    .addComponent(formatTextfield)
                    .addComponent(subtitlesFileNameTextfield)
                    .addComponent(nameTextfield)
                    .addComponent(lengthTextfield)
                    .addComponent(reset)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(contentTypeLabel)
                    .addComponent(contentTypeTextfield))

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(formatLabel)
                    .addComponent(formatTextfield))                        

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(subtitlesFileNameLabel)
                    .addComponent(subtitlesFileNameTextfield))       

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextfield))      
                
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lengthLabel)
                    .addComponent(lengthTextfield))                 

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(submit)
                    .addComponent(reset)));

        //layout.linkSize(SwingConstants.HORIZONTAL, usernameSaveButton, passwordSaveButton);
        layout.linkSize(SwingConstants.HORIZONTAL, submit, reset);
        frame.getContentPane().setLayout(layout);    
        frame.setJMenuBar(mb);    
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getContentTypeLabel() {
        return contentTypeLabel;
    }

    public void setContentTypeLabel(JLabel contentTypeLabel) {
        this.contentTypeLabel = contentTypeLabel;
    }

    public JLabel getFormatLabel() {
        return formatLabel;
    }
    
    public void setFormatLabel(JLabel formatLabel) {
        this.formatLabel = formatLabel;
    }

    public JLabel getSubtitlesFileNameLabel() {
        return subtitlesFileNameLabel;
    }

    public void setSubtitlesFileNameLabel(JLabel subtitlesFileNameLabel) {
        this.subtitlesFileNameLabel = subtitlesFileNameLabel;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getLengthLabel() {
        return lengthLabel;
    }

    public void setLengthLabel(JLabel lengthLabel) {
        this.lengthLabel = lengthLabel;
    }

    public JTextField getContentTypeTextfield() {
        return contentTypeTextfield;
    }

    public void setContentTypeTextfield(JTextField contentTypeTextfield) {
        this.contentTypeTextfield = contentTypeTextfield;
    }

    public JTextField getFormatTextfield() {
        return formatTextfield;
    }

    public void setFormatTextfield(JTextField formatTextfield) {
        this.formatTextfield = formatTextfield;
    }

    public JTextField getSubtitlesFileNameTextfield() {
        return subtitlesFileNameTextfield;
    }

    public void setSubtitlesFileNameTextfield(JTextField subtitlesFileNameTextfield) {
        this.subtitlesFileNameTextfield = subtitlesFileNameTextfield;
    }

    public JTextField getNameTextfield() {
        return nameTextfield;
    }

    public void setNameTextfield(JTextField nameTextfield) {
        this.nameTextfield = nameTextfield;
    }

    public JTextField getLengthTextfield() {
        return lengthTextfield;
    }

    public void setLengthTextfield(JTextField lengthTextfield) {
        this.lengthTextfield = lengthTextfield;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JButton getReset() {
        return reset;
    }

    public void setReset(JButton reset) {
        this.reset = reset;
    }
}