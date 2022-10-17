import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ControllerCreateContent {
    private ModelCreateContent model;
    private ViewCreateContent view;

    public ControllerCreateContent(ModelCreateContent m, ViewCreateContent v) {
        model = m;
        view = v;
        initView();
    }

    public void initView() {
        view.getContentTypeTextfield().setText(model.getContentType());
        view.getFormatTextfield().setText(model.getFormat());
        view.getSubtitlesFileNameTextfield().setText(model.getSubtitlesFileName());
        view.getNameTextfield().setText(model.getName());
        view.getLengthTextfield().setText(model.getLength());
    }

    public void initController() {
        // view.getUsernameTextfield().addActionListener(e -> saveUsername());
        // view.getPasswordTextfield().addActionListener(e -> savePassword());
        // view.getUsernameSaveButton().addActionListener(e -> saveUsername());
        // view.getPasswordSaveButton().addActionListener(e -> savePassword());
        view.getSubmit().addActionListener(e -> pressSubmit());
        view.getReset().addActionListener(e -> pressReset());
    }

    public void cleanFields() {
        view.getContentTypeTextfield().setText("");
        view.getFormatTextfield().setText("");
        view.getSubtitlesFileNameTextfield().setText("");
        view.getNameTextfield().setText("");
        view.getLengthTextfield().setText("");

        updateModel();
    }

    public void updateModel() {
        model.setContentType(view.getContentTypeTextfield().getText());
        model.setFormat(view.getFormatTextfield().getText());
        model.setSubtitlesFileName(view.getSubtitlesFileNameTextfield().getText());
        model.setName(view.getNameTextfield().getText());
        model.setLength(view.getLengthTextfield().getText());
    }

    private void pressSubmit() {
        updateModel();
        // view.getUsernameTextfield().setText("");
        // view.getPasswordTextfield().setText("");
        // JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your
        // password is: " + model.getPassword(), "Info",
        // JOptionPane.INFORMATION_MESSAGE);
    }

    private void pressReset() {
        cleanFields();
        // JOptionPane.showMessageDialog(null, "Hello " + model.getUsername() + ", your
        // password is: " + model.getPassword(), "Info",
        // JOptionPane.INFORMATION_MESSAGE);
    }
}