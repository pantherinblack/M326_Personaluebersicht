package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author Marko Joksimovic
 * @version 1.0
 * @date
 */
public class Stammdaten extends JPanel {

    private JLabel firma;
    private JTextField firmaField;

    private JPanel stammdatenPane;

    private JPanel abteilung;
    private JPanel funktion;
    private JPanel teams;

    public Stammdaten() {
        Startseite startseite = new Startseite();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        firma = new JLabel("Firma:");
        firma.setPreferredSize(new Dimension(90, 20));
        firmaField = new JTextField();
        firmaField.setPreferredSize(new Dimension(200, 20));

        abteilung = startseite.createPanel("Abteilungen:", abteilung);
        funktion = startseite.createPanel("Funktion:", funktion);
        teams = startseite.createPanel("Teams:", teams);

        stammdatenPane = new JPanel(new GridBagLayout());

        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 10, 0);
        stammdatenPane.add(abteilung, c);
        c.gridy = 1;
        stammdatenPane.add(funktion, c);
        c.gridy = 2;
        stammdatenPane.add(teams, c);

        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0.0;
        c.weightx = 0.0;
        c.insets = new Insets(10,0,10,0);
        add(firma, c);

        c.gridx = 1;
        add(firmaField, c);

        c.insets = new Insets(0,0,0,0);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(stammdatenPane, c);
    }
}
