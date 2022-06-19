package ch.bzz.gui.dialog;

import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import static ch.bzz.util.ColorCodes.*;

public class Authentication extends JFrame {
    private JPanel content = new JPanel();
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel codeLabel = new JLabel("Code: ");
    private JComboBox nameField = new JComboBox();
    private JTextField codeField = new JTextField();
    private JPanel buttonPanel = new JPanel();
    private JButton cancelButton = new JButton("Abbrechen");
    private JButton continueButton = new JButton("Weiter");
    private JPanel nameLabelPanel = new JPanel();
    private JPanel codeLabelPanel = new JPanel();
    private JPanel nameFieldPanel = new JPanel();
    private JPanel codeFieldPanel = new JPanel();
    public Authentication() {

        setTitle("Authentifizierung");

        double[][] order = {{-3, -1}, {-3, -3, -3}};

        add(content);
        setIconImage(new ImageIcon("images/img.png").getImage());

        content.setLayout(new TableLayout(order));

        content.add(nameLabelPanel, "0, 0");
        content.add(codeLabelPanel, "0, 1");
        content.add(nameFieldPanel, "1, 0");
        content.add(codeFieldPanel, "1, 1");
        content.add(buttonPanel, "1, 2");

        nameLabelPanel.add(nameLabel, BorderLayout.WEST);
        codeLabelPanel.add(codeLabel, BorderLayout.WEST);
        nameFieldPanel.add(nameField, BorderLayout.EAST);
        codeFieldPanel.add(codeField, BorderLayout.EAST);

        nameLabelPanel.setLayout(new GridLayout());
        codeLabelPanel.setLayout(new GridLayout());
        nameFieldPanel.setLayout(new GridLayout());
        codeFieldPanel.setLayout(new GridLayout());

        nameLabelPanel.setBorder(COMPONENT_BORDER);
        codeLabelPanel.setBorder(COMPONENT_BORDER);
        nameFieldPanel.setBorder(COMPONENT_BORDER);
        codeFieldPanel.setBorder(COMPONENT_BORDER);

        buttonPanel.setBorder(COMPONENT_BORDER);


        nameLabel.setMinimumSize(new Dimension(80, 0));
        codeLabel.setMinimumSize(new Dimension(80, 0));

        buttonPanel.setLayout(new BorderLayout(10,10));
        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(continueButton, BorderLayout.EAST);

        content.setBorder(FRAME_BORDER);
        setVisible(true);

        pack();
    }

    public static void main(String[] args) {
        new Authentication();
    }
}
