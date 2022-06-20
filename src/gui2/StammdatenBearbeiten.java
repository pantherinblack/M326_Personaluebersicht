package gui2;

import javax.swing.*;
import java.awt.*;

public class StammdatenBearbeiten extends JFrame {

    //Attribute
    JLabel abteilung;
    JLabel leer;
    JTextField textField;
    JButton abbrechen;
    JButton speichern;
    JPanel abteilungTextfieldPanel;
    JPanel leerPanel;
    JPanel buttonsPanel;
    JPanel leerButtonsPanel;

    public StammdatenBearbeiten() {
        setTitle("Funktion erfassen/bearbeiten");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        setSize(400, 130);

        abteilung = new JLabel("Abteilung:");
        textField = new JTextField("Text eingeben", 35);
        abteilungTextfieldPanel = new JPanel(new GridLayout(2, 1));
        abteilungTextfieldPanel.add(abteilung, textField);

        abbrechen = new JButton("Abbrechen");
        speichern = new JButton("Speichern");
        buttonsPanel = new JPanel(new GridLayout(2, 1));
        buttonsPanel.add(abbrechen, speichern);

        leer = new JLabel("");
        leerPanel = new JPanel(new BorderLayout(5, 5));
        leerPanel.add(leer);

        leerButtonsPanel = new JPanel(new GridLayout(2,1));
        leerButtonsPanel.add(leerPanel, buttonsPanel);

        getContentPane().add(abteilungTextfieldPanel, BorderLayout.CENTER);
        getContentPane().add(leerButtonsPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    public static void main(String[] args) {
        StammdatenBearbeiten  stammdatenBearbeiten = new StammdatenBearbeiten();
    }
}
