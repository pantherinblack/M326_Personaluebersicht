package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Vector;


/**
 * @author Marko Joksimovic
 * @version 1.0
 * @date
 */
public class Uebersicht extends JPanel {

    private Personal personal;

    private JPanel uebersichtPane;


    private JPanel filterPane;
    private JPanel filterComponentPane;

    private JPanel sortierPane;
    private JPanel sortierPaneH;
    private JPanel sortierPaneW;
    private JPanel sortierButtonPane;


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

    private JPanel funktionPane;
    private JScrollPane funktionPaneList;

    private JPanel teamPane;
    private JScrollPane teamPaneList;

    private JPanel funktionTeamPane;

    BufferedImage personBild;
    BufferedImage lupeBild;

    JComboBox<String> abteilungen;
    JComboBox<String> funktionen;
    JComboBox<String> teams;

    private JRadioButton keine;
    private JRadioButton aToZ;
    private JRadioButton zToA;

    private JPanel uebersichtPersonenPane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    public Uebersicht() {

        setLayout(new GridBagLayout());
        Startseite startseite = new Startseite();

        uebersichtPane = new JPanel(new GridBagLayout());

        abteilungen = new JComboBox<>(new Vector<>());
        funktionen = new JComboBox<>(new Vector<>());
        teams = new JComboBox<>(new Vector<>());

        filterPane = new JPanel();
        filterComponentPane = new JPanel(new GridLayout(3,2,10,0));
        filterComponentPane.add(new JLabel("Abteilung:"));
        filterComponentPane.add(abteilungen);
        filterComponentPane.add(new JLabel("Funktion:"));
        filterComponentPane.add(funktionen);
        filterComponentPane.add(new JLabel("Team:"));
        filterComponentPane.add(teams);
        filterPane.add(filterComponentPane);
        filterPane.setPreferredSize(new Dimension(340, 125));
        filterPane.setBorder(new TitledBorder("Filter:"));

        sortierPane = new JPanel();
        sortierPaneH = new JPanel();
        sortierPaneW = new JPanel();
        sortierButtonPane = new JPanel(new GridLayout(3,1));
        keine = new JRadioButton("keine", true);
        aToZ = new JRadioButton("A-Z");
        zToA = new JRadioButton("Z-A");
        ButtonGroup radioButtons = new ButtonGroup();
        radioButtons.add(keine);
        radioButtons.add(aToZ);
        radioButtons.add(zToA);
        sortierButtonPane.add(keine);
        sortierButtonPane.add(aToZ);
        sortierButtonPane.add(zToA);
        sortierPane.add(sortierButtonPane, BorderLayout.CENTER);
        sortierPane.setPreferredSize(new Dimension(100, 82));
        sortierPaneH.add(sortierPane, BorderLayout.EAST);
        sortierPaneW.add(sortierPaneH, BorderLayout.NORTH);
        sortierPaneW.setBorder(new TitledBorder("Sortierung:"));

        personPane = startseite.createList(personPane, "Übersicht:", personPaneList, 120, 360);
        JPanel personPaneH = new JPanel(new BorderLayout());
        JPanel personPaneW = new JPanel(new BorderLayout());
        personPaneH.add(personPane, BorderLayout.WEST);
        personPaneW.add(personPaneH, BorderLayout.NORTH);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        uebersichtPersonenPane.add(personPaneW, c);

        try {
            lupeBild = ImageIO.read(new File("src/images/lens.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel lupe = new JLabel(new ImageIcon(lupeBild));
        c.gridy = 1;
        uebersichtPersonenPane.add(lupe, c);

        JTextField suche = new JTextField();
        c.gridy = 2;
        uebersichtPersonenPane.add(suche, c);

        detailPane = new JPanel(new GridLayout(2,1));

        detailPanePerson = new JPanel(new BorderLayout());
        detailPanePerson.setPreferredSize(new Dimension(290, 420));
        detailPanePersonL = new JPanel(new BorderLayout());
        detailPanePersonL.setPreferredSize(new Dimension(70, 170));
        detailPanePersonR = new JPanel(new BorderLayout());
        detailPanePersonR.setPreferredSize(new Dimension(170, 170));

        JLabel detailName = new JLabel("Name:");
        detailName.setAlignmentX(CENTER_ALIGNMENT);
        JLabel detailAbteilung = new JLabel("Abteilung:");
        detailPanePersonL.add(detailName, BorderLayout.NORTH);
        detailPanePersonL.add(detailAbteilung, BorderLayout.SOUTH);
        detailPanePersonLH = new JPanel(new BorderLayout());
        detailPanePersonLW = new JPanel(new BorderLayout());
        detailPanePersonLH.add(detailPanePersonL, BorderLayout.WEST);
        detailPanePersonLW.add(detailPanePersonLH, BorderLayout.NORTH);

        try {
            personBild = ImageIO.read(new File("src/images/default.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel bildLabel = new JLabel(new ImageIcon(personBild));

        JTextField detailNameText = new JTextField();
        JTextField detailAbteilungText = new JTextField();
        detailPanePersonR.add(detailNameText, BorderLayout.NORTH);
        detailPanePersonR.add(detailAbteilungText, BorderLayout.SOUTH);
        detailPanePersonR.add(bildLabel, BorderLayout.CENTER);
        detailPanePersonRH = new JPanel(new BorderLayout());
        detailPanePersonRW = new JPanel(new BorderLayout());
        detailPanePersonRH.add(detailPanePersonR, BorderLayout.EAST);
        detailPanePersonRW.add(detailPanePersonRH, BorderLayout.NORTH);

        detailPanePerson.add(detailPanePersonLW, BorderLayout.WEST);
        detailPanePerson.add(detailPanePersonRW, BorderLayout.EAST);

        detailPanePersonH = new JPanel(new BorderLayout());
        detailPanePersonW = new JPanel(new BorderLayout());
        detailPanePersonH.add(detailPanePerson, BorderLayout.WEST);
        detailPanePersonW.add(detailPanePersonH, BorderLayout.NORTH);

        funktionPane = startseite.createList(funktionPane, "Funktion:", funktionPaneList, 100, 160);
        JPanel funktionPanelH = new JPanel(new BorderLayout());
        JPanel funktionPanelW = new JPanel(new BorderLayout());
        funktionPanelH.add(funktionPane, BorderLayout.WEST);
        funktionPanelW.add(funktionPanelH, BorderLayout.NORTH);

        teamPane = startseite.createList(teamPane, "Teams:", teamPaneList, 100, 160);
        JPanel teamPanelH = new JPanel(new BorderLayout());
        JPanel teamPanelW = new JPanel(new BorderLayout());
        teamPanelH.add(teamPane, BorderLayout.WEST);
        teamPanelW.add(teamPanelH, BorderLayout.NORTH);

        funktionTeamPane = new JPanel(new GridLayout(1,2));
        funktionTeamPane.add(funktionPanelW);
        funktionTeamPane.add(teamPanelW);

        detailPane.add(detailPanePersonW);
        detailPane.add(funktionTeamPane);
        detailPane.setPreferredSize(new Dimension(340, 415));
        detailPane.setBorder(new TitledBorder("Detail:"));
        JPanel detailPanelH = new JPanel(new BorderLayout());
        JPanel detailPanelW = new JPanel(new BorderLayout());
        detailPanelH.add(detailPane, BorderLayout.EAST);
        detailPanelW.add(detailPanelH, BorderLayout.NORTH);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = GridBagConstraints.REMAINDER;
        uebersichtPersonenPane.add(detailPanelW, c);

        uebersichtPersonenPane.setBorder(new TitledBorder("Personen:"));

        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 2;
        c2.anchor = GridBagConstraints.NORTHWEST;
        this.add(uebersichtPersonenPane, c2);

        c2.gridy = 1;
        c2.gridwidth = GridBagConstraints.RELATIVE;
        c2.anchor = GridBagConstraints.SOUTHWEST;
        this.add(sortierPaneW, c2);

        c2.gridx = 1;
        c2.gridwidth = GridBagConstraints.REMAINDER;
        c2.anchor = GridBagConstraints.SOUTHEAST;
        this.add(filterPane, c2);
    }

}
