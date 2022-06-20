package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Marko Joksimovic
 * @version 1.0
 * @date
 */
public class Zuordnung extends JPanel {

    private JPanel zuordnungPane;

    private JPanel personPane;
    private JScrollPane personPaneList;


    private JPanel detailPane;

    private JPanel detailPanePerson;
    private JPanel detailPanePersonL;
    private JPanel detailPanePersonLH;
    private JPanel detailPanePersonLW;
    private JPanel detailPanePersonR;
    private JPanel detailPanePersonRH;
    private JPanel detailPanePersonRW;
    private JPanel detailPanePersonH;
    private JPanel detailPanePersonW;

    JPanel personLabels = new JPanel(new GridBagLayout());
    JLabel personName = new JLabel("Name:");

    GridBagConstraints personC = new GridBagConstraints();

    JPanel detailRight = new JPanel(new GridBagLayout());

    JTextField personNameField = new JTextField();

    private JPanel detailPaneUtil;

    JPanel utilLabels = new JPanel(new GridBagLayout());
    JLabel abteilung = new JLabel("Abteilung:");
    JLabel funktion = new JLabel("Funktionen:");
    JLabel team = new JLabel("Teams:");

    GridBagConstraints utilC = new GridBagConstraints();


    JPanel utilFields = new JPanel(new GridBagLayout());
    JTextField abteilungText = new JTextField();
    JComboBox<String> funktionen = new JComboBox<>();
    JComboBox<String> teams = new JComboBox<>();

    BufferedImage personBild;


    GridBagConstraints c = new GridBagConstraints();

    public Zuordnung() {

        setLayout(new GridBagLayout());
        Startseite startseite = new Startseite();

        zuordnungPane = new JPanel(new GridBagLayout());
        detailPanePerson = new JPanel(new GridBagLayout());
        detailRight.setPreferredSize(new Dimension(350, 400));
        detailPaneUtil = new JPanel(new GridBagLayout());

        personPane = startseite.createList(personPane, "Übersicht:", personPaneList, 120, 413);
        JPanel personPaneH = new JPanel(new BorderLayout());
        JPanel personPaneW = new JPanel(new BorderLayout());
        personPaneH.add(personPane, BorderLayout.WEST);
        personPaneW.add(personPaneH, BorderLayout.NORTH);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        zuordnungPane.add(personPaneW, c);


        detailPane = new JPanel(new GridBagLayout());
        GridBagConstraints detailC = new GridBagConstraints();
        detailC.anchor = GridBagConstraints.NORTHWEST;

        personC.gridy = 0;
        personC.insets = new Insets(0,0,170,20);
        personLabels.add(personName, personC);

        try {
            personBild = ImageIO.read(new File("src/images/default.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel bildLabel = new JLabel(new ImageIcon(personBild));
        personNameField.setPreferredSize(new Dimension(222, 20));
        personNameField.setEditable(false);
        JPanel personNameFieldH = new JPanel();
        JPanel personNameFieldW = new JPanel();
        personNameFieldH.add(personNameField, BorderLayout.EAST);
        personNameFieldW.add(personNameFieldH, BorderLayout.NORTH);

        personC.gridx = 0;
        personC.weightx = 1.0;
        personC.fill = GridBagConstraints.HORIZONTAL;
        personC.insets = new Insets(0,0,0,0);
        detailRight.add(personNameFieldW, personC);
        personC.gridy = 1;

        detailRight.add(bildLabel, personC);

        personC.gridx = 0;
        personC.gridy = 0;
        detailPanePerson.add(personLabels, personC);
        personC.gridx = 1;
        detailPanePerson.add(detailRight, personC);

        detailPanePersonH = new JPanel(new BorderLayout());
        detailPanePersonW = new JPanel(new BorderLayout());
        detailPanePersonH.add(detailPanePerson, BorderLayout.WEST);
        detailPanePersonW.add(detailPanePersonH, BorderLayout.NORTH);

        abteilung.setPreferredSize(new Dimension(80, 25));
        funktion.setPreferredSize(new Dimension(80, 25));
        team.setPreferredSize(new Dimension(80, 25));

        utilC.gridy = 0;
        utilC.insets = new Insets(10,0,10,0);
        utilLabels.add(abteilung, utilC);
        utilC.gridy = 1;
        utilLabels.add(funktion, utilC);
        utilC.gridy = 2;
        utilLabels.add(team, utilC);

        abteilungText.setPreferredSize(new Dimension(223, 20));
        abteilungText.setEditable(false);
        funktionen.setPreferredSize(new Dimension(100, 20));
        teams.setPreferredSize(new Dimension(100, 20));

        utilC.gridy = 0;
        utilC.weightx = 1.0;
        utilC.fill = GridBagConstraints.HORIZONTAL;
        utilFields.add(abteilungText, utilC);
        utilC.gridy = 1;
        utilFields.add(funktionen, utilC);
        utilC.gridy = 2;
        utilFields.add(teams, utilC);

        utilC.gridy = 0;
        utilC.weightx = 0.0;
        utilC.fill = GridBagConstraints.HORIZONTAL;
        detailPaneUtil.add(utilLabels, utilC);
        utilC.gridx = 1;
        utilC.weightx = 1.0;
        utilC.insets = new Insets(0, 30, 0, 0);
        detailPaneUtil.add(utilFields, utilC);

        detailPane.setPreferredSize(new Dimension(340, 415));

        detailC.gridx = 0;
        detailC.gridy = 0;
        detailC.fill = GridBagConstraints.HORIZONTAL;
        detailC.weightx = 1.0;
        detailC.weighty = 0.0;
        detailC.anchor = GridBagConstraints.FIRST_LINE_START;
        detailC.insets = new Insets(0,0,15,0);
        detailPane.add(detailPanePerson, detailC);

        detailC.gridy = 1;
        detailPane.add(new JSeparator(JSeparator.HORIZONTAL), detailC);

        detailC.gridy = 2;
        detailC.insets = new Insets(0,0,30,0);
        detailPane.add(detailPaneUtil, detailC);

        detailPane.setBorder(new TitledBorder("Detail:"));
        JPanel detailPanelH = new JPanel(new BorderLayout());
        JPanel detailPanelW = new JPanel(new BorderLayout());
        detailPanelH.add(detailPane, BorderLayout.EAST);
        detailPanelW.add(detailPanelH, BorderLayout.NORTH);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = GridBagConstraints.REMAINDER;
        zuordnungPane.add(detailPanelW, c);

        zuordnungPane.setBorder(new TitledBorder("Personen bearbeiten: "));

        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridx = 0;
        c2.gridy = 0;
        c2.weighty = 1.0;
        c2.weightx = 0.5;
        c2.gridwidth = 2;
        c2.anchor = GridBagConstraints.BASELINE;
        this.add(zuordnungPane, c2);
    }
}
