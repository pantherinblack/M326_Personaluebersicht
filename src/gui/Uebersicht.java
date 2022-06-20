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

    //panel for the right-lower part (filter menu)
    private JPanel filterPane;
    private JPanel filterComponentPane;

    //panel for the left-lower part (sort menu)
    private JPanel sortierPane;
    private JPanel sortierPaneH;
    private JPanel sortierPaneW;
    private JPanel sortierButtonPane;


    private JPanel personPane;
    private JScrollPane personPaneList;

    //panel for the right-upper part (view of the chosen person)
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

    //panel für funktionenliste
    private JPanel funktionPane;
    private JScrollPane funktionPaneList;

    //panel für teamliste
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

    //panel for the left-upper part of übersicht (worker list, lens, searchbar)
    private JPanel uebersichtPane = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();


    /**
     * Constructor of the class.
     */
    public Uebersicht() {

        setLayout(new GridBagLayout());
        Startseite startseite = new Startseite();

        //main panel
        uebersichtPane = new JPanel(new GridBagLayout());


        //dropdowns of filter menu
        abteilungen = new JComboBox<>(new Vector<>());
        abteilungen.setPreferredSize(new Dimension(150, 20));
        funktionen = new JComboBox<>(new Vector<>());
        teams = new JComboBox<>(new Vector<>());

        //all components of the filter menu
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

        //all components of the sort menu
        //H & W mean height & width, used for nested component
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

        //creates panel with title and list of workers
        //H & W mean height & width, used for nested component
        personPane = startseite.createList(personPane, "Übersicht:", personPaneList, 120, 360);
        JPanel personPaneH = new JPanel(new BorderLayout());
        JPanel personPaneW = new JPanel(new BorderLayout());
        personPaneH.add(personPane, BorderLayout.WEST);
        personPaneW.add(personPaneH, BorderLayout.NORTH);

        //adds workerlist to the left-upper part
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        uebersichtPane.add(personPaneW, c);

        //adds lens icon to the left-upper part
        try {
            lupeBild = ImageIO.read(new File("src/images/lens.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel lupe = new JLabel(new ImageIcon(lupeBild));
        c.gridy = 1;
        uebersichtPane.add(lupe, c);

        //adds search bar to the left-upper part
        JTextField suche = new JTextField();
        c.gridy = 2;
        uebersichtPane.add(suche, c);

        //detailPane is the right-upper part
        detailPane = new JPanel(new GridLayout(2,1));

        //detailPanePerson is upper half with name, image and abteilung
        //labels and textfields + image in separate Borderlayout (detailPanePersonL + detailPanePersonR)
        detailPanePerson = new JPanel(new BorderLayout());
        detailPanePerson.setPreferredSize(new Dimension(290, 420));
        detailPanePersonL = new JPanel(new BorderLayout());
        detailPanePersonL.setPreferredSize(new Dimension(70, 170));
        detailPanePersonR = new JPanel(new BorderLayout());
        detailPanePersonR.setPreferredSize(new Dimension(170, 170));

        //H & W mean height & width, used for nested component
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

        //H & W mean height & width, used for nested component
        JTextField detailNameText = new JTextField();
        detailNameText.setEditable(false);
        JTextField detailAbteilungText = new JTextField();
        detailAbteilungText.setEditable(false);
        detailPanePersonR.add(detailNameText, BorderLayout.NORTH);
        detailPanePersonR.add(detailAbteilungText, BorderLayout.SOUTH);
        detailPanePersonR.add(bildLabel, BorderLayout.WEST);
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

        //funktionPane includes list with functions of chosen person
        funktionPane = startseite.createList(funktionPane, "Funktion:", funktionPaneList, 100, 160);
        JPanel funktionPanelH = new JPanel(new BorderLayout());
        JPanel funktionPanelW = new JPanel(new BorderLayout());
        funktionPanelH.add(funktionPane, BorderLayout.WEST);
        funktionPanelW.add(funktionPanelH, BorderLayout.NORTH);

        //teamPane includes list with teams of chosen person
        teamPane = startseite.createList(teamPane, "Teams:", teamPaneList, 100, 160);
        JPanel teamPanelH = new JPanel(new BorderLayout());
        JPanel teamPanelW = new JPanel(new BorderLayout());
        teamPanelH.add(teamPane, BorderLayout.WEST);
        teamPanelW.add(teamPanelH, BorderLayout.NORTH);

        //panel for combination of functionlist and teamlist
        funktionTeamPane = new JPanel(new GridLayout(1,2));
        funktionTeamPane.add(funktionPanelW);
        funktionTeamPane.add(teamPanelW);

        //adds upper and lower half of detail panel
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
        uebersichtPane.add(detailPanelW, c);

        uebersichtPane.setBorder(new TitledBorder("Personen:"));

        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridx = 0;
        c2.gridy = 0;
        c2.gridwidth = 2;
        c2.anchor = GridBagConstraints.NORTHWEST;
        this.add(uebersichtPane, c2);

        c2.gridy = 1;
        c2.gridwidth = GridBagConstraints.RELATIVE;
        c2.anchor = GridBagConstraints.SOUTHWEST;
        this.add(sortierPaneW, c2);

        c2.gridx = 1;
        c2.gridwidth = GridBagConstraints.REMAINDER;
        c2.anchor = GridBagConstraints.SOUTHEAST;
        this.add(filterPane, c2);

        setSize(new Dimension(500,500));
    }
}