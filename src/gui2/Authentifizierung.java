package gui2;

import javax.swing.*;
import java.awt.*;

/**
 * @author Morris Idahosa
 * @version 1.0
 * @date    06.04.2022
 */
public class Authentifizierung extends JFrame {

        //Attribute
        private JLabel name;
        private JLabel code;
        private JLabel leer;
        private JComboBox comboBox;
        private JTextField codeTextField;
        private JButton abbrechen;
        private JButton weiter;
        private JPanel combinedPanel;
        private JPanel buttonPanel;
        private JPanel leerPanel;
        private JPanel buttonLeerPanel;

        public Authentifizierung() {
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new BorderLayout(20, 20));

                String comboBoxListe[] = {"ABC" , "DEF"};

                name = new JLabel("Name:");
                code = new JLabel("Code:");
                leer = new JLabel("");
                comboBox = new JComboBox(comboBoxListe);
                codeTextField = new JTextField("...", 35);
                abbrechen = new JButton("Abbrechen");
                weiter = new JButton("Weiter");
                combinedPanel = new JPanel(new GridLayout(2,2));
                buttonPanel = new JPanel(new GridLayout(1,2));
                leerPanel = new JPanel(new BorderLayout(20, 20));
                buttonLeerPanel = new JPanel(new GridLayout(1, 2));

                combinedPanel.add(name);
                combinedPanel.add(comboBox);
                combinedPanel.add(code);
                combinedPanel.add(codeTextField);

                buttonPanel.add(abbrechen);
                buttonPanel.add(weiter);
                leerPanel.add(leer);
                buttonLeerPanel.add(leer);
                buttonLeerPanel.add(buttonPanel);

                getContentPane().add(combinedPanel, BorderLayout.CENTER);
                getContentPane().add(buttonLeerPanel, BorderLayout.SOUTH);


                setSize(415, 130);
                setResizable(false);
                setVisible(true);
        }

        public static void main(String[] args) {
                Authentifizierung authentifizierung = new Authentifizierung();
        }
}
