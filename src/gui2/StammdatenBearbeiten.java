package gui2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.im.InputMethodRequests;

/**
 * @author Morris Idahosa
 * @version 1.0
 * @date    21.06.2022
 */

public class StammdatenBearbeiten extends JFrame {

    private JLabel abteilung;
    private JLabel leer;
    private JTextField textField;
    private JButton abbrechen;
    private JButton speichern;
    private JPanel abteilungTextPanel;
    private JPanel buttonPanel;
    private JPanel leerButtonPanel;

    public StammdatenBearbeiten() {
        setTitle("Funktion erfassen/bearbeiten");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        abteilung = new JLabel("Abteilung:");
        leer = new JLabel("");
        textField = new JTextField("Text eingeben", 35);
        abbrechen = new JButton("Abbrechen");
        speichern = new JButton("Speichern");
        abteilungTextPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel = new JPanel(new GridLayout(1, 2));
        leerButtonPanel = new JPanel(new GridLayout(1, 2));

        abteilungTextPanel.add(abteilung);
        abteilungTextPanel.add(textField);

        buttonPanel.add(abbrechen);
        buttonPanel.add(speichern);

        leerButtonPanel.add(leer);
        leerButtonPanel.add(buttonPanel);

        getContentPane().add(abteilungTextPanel, BorderLayout.CENTER);
        getContentPane().add(leerButtonPanel, BorderLayout.SOUTH);

        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Schliesst das Fenster
                dispose();
            }
        });

        /**
        speichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        */

        setSize(415, 130);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        StammdatenBearbeiten stammdatenBearbeiten = new StammdatenBearbeiten();
    }
}


