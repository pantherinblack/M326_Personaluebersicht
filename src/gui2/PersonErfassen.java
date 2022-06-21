package gui2;

import javax.swing.*;
import java.awt.*;

/**
 * @author Morris Idahosa
 * @version 1.0
 * @date    21.06.2022
 */

public class PersonErfassen extends JFrame {

    private JLabel name;
    private JLabel foto;
    private JLabel leer;
    private JLabel hr;
    private JLabel admin;
    private JTextField nameTextField;
    private JCheckBox checkBoxHr;
    private JCheckBox checkBoxAdmin;
    private JButton abbrechen;
    private JButton speichern;
    private JPanel nameFotoPanel;
    private JPanel hrAdminPanel;
    private JPanel hrAdminLeerPanel;
    private JPanel buttonPanel;
    private JPanel leerButtonPanel;

    public PersonErfassen() {
        setTitle("Person erfassen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));

        name = new JLabel("Name:");
        foto = new JLabel("Foto:");
        leer = new JLabel("");
        hr = new JLabel("HR-Mitarbeiter:");
        admin = new JLabel("Administrator:");
        nameTextField = new JTextField("Trulli Theo");
        checkBoxHr = new JCheckBox();
        checkBoxAdmin = new JCheckBox();
        abbrechen = new JButton("Abbrechen");
        speichern = new JButton("Speichern");
        nameFotoPanel = new JPanel(new GridLayout(2, 2));
        hrAdminPanel = new JPanel(new GridLayout(2, 2));
        hrAdminLeerPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel = new JPanel(new GridLayout(1, 2));
        leerButtonPanel = new JPanel(new GridLayout(1, 2));

        nameFotoPanel.add(name);
        nameFotoPanel.add(nameTextField);
        nameFotoPanel.add(foto);
        nameFotoPanel.add(leer);

        hrAdminPanel.add(hr);
        hrAdminPanel.add(checkBoxHr);
        hrAdminPanel.add(admin);
        hrAdminPanel.add(checkBoxAdmin);
        hrAdminLeerPanel.add(hrAdminPanel);
        hrAdminLeerPanel.add(leer);

        buttonPanel.add(abbrechen);
        buttonPanel.add(speichern);
        leerButtonPanel.add(leer);
        leerButtonPanel.add(buttonPanel);

        getContentPane().add(nameFotoPanel, BorderLayout.NORTH);
        getContentPane().add(hrAdminLeerPanel, BorderLayout.CENTER);
        getContentPane().add(leerButtonPanel, BorderLayout.SOUTH);

        setSize(350, 500);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        PersonErfassen personErfassen = new PersonErfassen();
    }

}
