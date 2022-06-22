package ch.bzz.view.tab;

import ch.bzz.facade.DataListModel;
import ch.bzz.facade.MainFacade;
import ch.bzz.model.employees.Person;
import ch.bzz.util.ColorCodes;
import ch.bzz.view.component.BasicList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * @author Marko Joksimovic
 * @version 1.0
 * @date
 */
public class OverviewPanel extends JPanel {

    BufferedImage personBild;
    BufferedImage lupeBild;
    JComboBox<String> abteilungen;
    JComboBox<String> funktionen;
    JComboBox<String> teams;
    GridBagConstraints c = new GridBagConstraints();
    private JPanel uebersichtPane;
    private JPanel filterPane;
    private JPanel filterComponentPane;
    private JPanel sortierPane;
    private JPanel sortierPaneH;
    private JPanel sortierPaneW;
    private JPanel sortierButtonPane;
    private BasicList personPane;
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
    private BasicList funktionPane;
    private JScrollPane funktionPaneList;
    private BasicList teamPane;
    private JScrollPane teamPaneList;
    private JPanel funktionTeamPane;
    private JRadioButton keine;
    private JRadioButton aToZ;
    private JRadioButton zToA;
    private JPanel uebersichtPersonenPane = new JPanel(new GridBagLayout());
    private ButtonGroup radioButtons = new ButtonGroup();
    private JTextField detailNameText;
    private JTextField suche = new JTextField();
    private JLabel bildLabel;

    public OverviewPanel(JFrame owner) {

        setLayout(new GridBagLayout());

        uebersichtPane = new JPanel(new GridBagLayout());

        abteilungen = new JComboBox<>(new DataListModel(DataListModel.MODE_DEPARTMENT));
        funktionen = new JComboBox<>(new DataListModel(DataListModel.MODE_FUNCTION));
        teams = new JComboBox<>(new DataListModel(DataListModel.MODE_TEAM));

        filterPane = new JPanel();
        filterComponentPane = new JPanel(new GridLayout(3, 2, 10, 0));
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
        sortierButtonPane = new JPanel(new GridLayout(3, 1));
        keine = new JRadioButton("keine", true);
        aToZ = new JRadioButton("A-Z");
        zToA = new JRadioButton("Z-A");
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

        personPane = new BasicList(null, BasicList.MODE_PERSON, null);
        personPane.setPreferredSize(new Dimension(100, 350));
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
        lupe.addMouseListener(new SearchListener());
        c.gridy = 1;
        uebersichtPersonenPane.add(lupe, c);

        c.gridy = 2;
        uebersichtPersonenPane.add(suche, c);

        detailPane = new JPanel(new GridLayout(2, 1));

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
        bildLabel = new JLabel(new ImageIcon(personBild));


        detailNameText = new JTextField(MainFacade.getInstance().getPerson(0).getFullName());
        detailNameText.setEditable(false);
        JTextField detailAbteilungText = new JTextField(
                MainFacade.getInstance().getDepartmentByUuid(
                MainFacade.getInstance().getPerson(0).getUuid()).getName());
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

        funktionPane = new BasicList(owner, BasicList.MODE_PERSON_FUNCTION, MainFacade.getInstance().getPerson(0).getUuid());
        funktionPane.setPreferredSize(new Dimension(150,150));
        JPanel funktionPanelH = new JPanel(new BorderLayout());
        JPanel funktionPanelW = new JPanel(new BorderLayout());
        funktionPanelH.add(funktionPane, BorderLayout.WEST);
        funktionPanelW.add(funktionPanelH, BorderLayout.NORTH);

        teamPane = new BasicList(owner, BasicList.MODE_PERSON_TEAM, MainFacade.getInstance().getPerson(0).getUuid());
        teamPane.setPreferredSize(new Dimension(150,150));
        JPanel teamPanelH = new JPanel(new BorderLayout());
        JPanel teamPanelW = new JPanel(new BorderLayout());
        teamPanelH.add(teamPane, BorderLayout.WEST);
        teamPanelW.add(teamPanelH, BorderLayout.NORTH);

        funktionTeamPane = new JPanel(new GridLayout(1, 2));
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

        abteilungen.setForeground(ColorCodes.DARK_RED);
        funktionen.setForeground(ColorCodes.DARK_RED);
        teams.setForeground(ColorCodes.DARK_RED);
        detailNameText.setForeground(ColorCodes.DARK_RED);
        detailAbteilungText.setForeground(ColorCodes.DARK_RED);
        suche.setForeground(ColorCodes.DARK_RED);

        setSize(new Dimension(500, 500));

        personPane.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Person person = MainFacade.getInstance().getPersonByUuid(personPane.getUuid(personPane.getSelectedIndex()));
                detailNameText.setText(person.getFullName());
                detailAbteilungText.setText(MainFacade.getInstance().getDepartmentByUuid(person.getUuid()).getName());
                funktionPane.changeUuid(person.getUuid());
                teamPane.changeUuid(person.getUuid());
                if (person.getPhoto()!=null && Files.exists(person.getPhoto())) {
                    bildLabel.setIcon(new ImageIcon(new ImageIcon(person.getPhoto().toString()).getImage().getScaledInstance(personBild.getWidth(), personBild.getHeight(), Image.SCALE_FAST)));
                } else {
                    bildLabel.setIcon(new ImageIcon(personBild));
                }
            }
        });
    }

    /**
     * SearchListener registers when a search is made
     * @author Kevin
     * @since 21.06.2022
     * @version 1.0
     */
    public class SearchListener extends MouseAdapter {
        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            String sort = null;
            if (aToZ.isSelected()) sort = aToZ.getText();
            if (zToA.isSelected()) sort = zToA.getText();
            if (keine.isSelected()) sort = keine.getText();

            String funktionString = null;
            if (funktionen.getSelectedIndex()>=0 && funktionen.getSelectedIndex()<funktionen.getItemCount())
                funktionString = funktionen.getItemAt(funktionen.getSelectedIndex());

            String teamsString = null;
            if (teams.getSelectedIndex()>=0 && teams.getSelectedIndex()<teams.getItemCount())
                teamsString = teams.getItemAt(teams.getSelectedIndex());

            String abteilungString = null;
            if (abteilungen.getSelectedIndex()>=0 && abteilungen.getSelectedIndex()<abteilungen.getItemCount())
                abteilungString = abteilungen.getItemAt(abteilungen.getSelectedIndex());


            personPane.setFilter(suche.getText(), funktionString, abteilungString, teamsString, sort);
        }
    }
}
