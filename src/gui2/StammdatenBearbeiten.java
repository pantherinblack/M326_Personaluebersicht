package gui2;

import javax.swing.*;
import java.awt.*;

public class StammdatenBearbeiten extends JDialog {

    //Attribute
    JLabel abteilung;
    JTextField textField;
    JButton abbrechen;
    JButton speichern;
    JPanel abteilungTextfield;
    JPanel buttons;
    JPanel leer;
    JPanel buttonLeer;

    public StammdatenBearbeiten() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Funktino erfassen/bearbeiten");
        dialog.setLayout(new BorderLayout(50, 50));
        dialog.setVisible(true);

        abteilung = new JLabel();
        textField = new JTextField("Text eingeben");
        abteilungTextfield = new JPanel(new GridLayout(2, 1));
        abteilungTextfield.add(abteilung, textField);
        abbrechen = new JButton("Abbrechen");
        speichern = new JButton("Speichern");
        buttons = new JPanel(new GridLayout(2, 1));
        buttons.add(abbrechen, speichern);
        leer = new JPanel(new BorderLayout(20, 20));
        buttonLeer = new JPanel(new GridLayout(2, 1));
        buttonLeer.add(buttons, leer);
        dialog.add(abteilungTextfield, BorderLayout.NORTH);
        dialog.add(buttonLeer, BorderLayout.SOUTH);
    }
}
