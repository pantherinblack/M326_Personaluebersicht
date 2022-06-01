package gui2;

import javax.swing.*;

public class Authentifizierung  extends JFrame {

    //Deklaration der Attribute
    private JLabel name;
    private JLabel code;
    private JComboBox comboBox;
    private JTextField textField;
    private JButton abbrechen;
    private JButton weiter;
    private JPanel panel;

    public Authentifizierung() {
        super("Athentifizierung");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        this.pack();
        this.setVisible(true);
    }

    private void init() {
        //Label name und code erstellen
        name = new JLabel("Name:");
        code = new JLabel("Code:");
        //
        //Panel erstellen
        panel = new JPanel();

    }
}
