package gui2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Morris Idahosa
 * @version 1.0
 * @date    21.06.2022
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
        private JPanel nameCodePanel;
        private JPanel leerPanel1;
        private JPanel combinedPanel;
        private JPanel buttonPanel;
        private JPanel leerPanel2;
        private JPanel buttonLeerPanel;

        public Authentifizierung() {
                setTitle("Authentifizierung");
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
                nameCodePanel = new JPanel(new GridLayout(2,2));
                leerPanel1 = new JPanel(new BorderLayout(20, 20));
                combinedPanel = new JPanel(new GridLayout(1, 2));
                buttonPanel = new JPanel(new GridLayout(1,2));
                leerPanel2 = new JPanel(new BorderLayout(20, 20));
                buttonLeerPanel = new JPanel(new GridLayout(1, 2));

                nameCodePanel.add(name);
                nameCodePanel.add(comboBox);
                nameCodePanel.add(code);
                nameCodePanel.add(codeTextField);
                leerPanel1.add(leer);
                combinedPanel.add(leer);
                combinedPanel.add(nameCodePanel);

                buttonPanel.add(abbrechen);
                buttonPanel.add(weiter);
                leerPanel2.add(leer);
                buttonLeerPanel.add(leer);
                buttonLeerPanel.add(buttonPanel);

                getContentPane().add(combinedPanel, BorderLayout.CENTER);
                getContentPane().add(buttonLeerPanel, BorderLayout.SOUTH);

                abbrechen.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                dispose();
                        }
                });

                /**
                weiter.addActionListener(new ActionListener() {
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
                Authentifizierung authentifizierung = new Authentifizierung();
        }
}
