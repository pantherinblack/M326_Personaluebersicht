package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Marko Joksimovic
 * @version 1.0
 * @date
 */
public class Personen extends JPanel {

    private JPanel bearbeitPane;

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
    BufferedImage personBild;

    private JPanel checkboxen = new JPanel(new GridBagLayout());
    private JCheckBox hrTick = new JCheckBox("HR-Mitarbeiter: ");
    private JCheckBox adminTick = new JCheckBox("Administrator:    ");
    private GridBagConstraints checkC = new GridBagConstraints();

    private JButton hinzufuegen;
    private JButton loeschen;
    private JButton bearbeiten;
    private Icon plus;
    private Icon kreuz;
    private Icon stift;
    private JPanel buttons = new JPanel(new GridBagLayout());
    private GridBagConstraints buttonC = new GridBagConstraints();

    GridBagConstraints c = new GridBagConstraints();

    public Personen() {

        setLayout(new GridBagLayout());
        Startseite startseite = new Startseite();

        bearbeitPane = new JPanel(new GridBagLayout());
        detailPanePerson = new JPanel(new GridBagLayout());
        detailRight.setPreferredSize(new Dimension(350, 400));

        personPane = startseite.createList(personPane, "Übersicht:", personPaneList, 120, 413);
        JPanel personPaneH = new JPanel(new BorderLayout());
        JPanel personPaneW = new JPanel(new BorderLayout());
        personPaneH.add(personPane, BorderLayout.WEST);
        personPaneW.add(personPaneH, BorderLayout.NORTH);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        bearbeitPane.add(personPaneW, c);

        plus = new ImageIcon("src/images/plus.png");
        hinzufuegen = new JButton(plus);
        hinzufuegen.setPreferredSize(new Dimension(40, 32));
        kreuz = new ImageIcon("src/images/kreuz.png");
        loeschen = new JButton(kreuz);
        loeschen.setPreferredSize(new Dimension(40, 32));
        stift = new ImageIcon("src/images/stift.png");
        bearbeiten = new JButton(stift);
        bearbeiten.setPreferredSize(new Dimension(40, 32));

        buttonC.gridx = 0;
        buttons.add(hinzufuegen, buttonC);
        buttonC.gridx = 1;
        buttons.add(loeschen, buttonC);
        buttonC.gridx = 2;
        buttons.add(bearbeiten, buttonC);

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

        hrTick.setHorizontalTextPosition(SwingConstants.LEFT);
        adminTick.setHorizontalTextPosition(SwingConstants.LEFT);
        checkC.gridy = 0;
        checkC.anchor = GridBagConstraints.WEST;
        checkboxen.add(hrTick, checkC);
        checkC.gridy = 1;
        checkboxen.add(adminTick, checkC);

        detailPane.setPreferredSize(new Dimension(340, 415));

        detailC.gridx = 0;
        detailC.gridy = 0;
        detailC.fill = GridBagConstraints.HORIZONTAL;
        detailC.weightx = 1.0;
        detailC.weighty = 0.0;
        detailC.anchor = GridBagConstraints.FIRST_LINE_START;
        detailC.insets = new Insets(0,0,16,0);
        detailPane.add(detailPanePerson, detailC);

        detailC.gridy = 1;
        detailPane.add(new JSeparator(JSeparator.HORIZONTAL), detailC);

        detailC.gridy = 2;
        detailC.anchor = GridBagConstraints.WEST;
        detailC.fill = GridBagConstraints.NONE;
        detailC.insets = new Insets(0,0,120,0);
        detailPane.add(checkboxen, detailC);

        detailPane.setBorder(new TitledBorder("Detail:"));
        JPanel detailPanelH = new JPanel(new BorderLayout());
        JPanel detailPanelW = new JPanel(new BorderLayout());
        detailPanelH.add(detailPane, BorderLayout.EAST);
        detailPanelW.add(detailPanelH, BorderLayout.NORTH);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = GridBagConstraints.REMAINDER;
        bearbeitPane.add(detailPanelW, c);

        c.gridx = 0;
        c.gridy = 1;
        bearbeitPane.add(buttons, c);

        bearbeitPane.setBorder(new TitledBorder("Personen bearbeiten: "));

        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridx = 0;
        c2.gridy = 0;
        c2.weighty = 1.0;
        c2.weightx = 0.5;
        c2.gridwidth = 2;
        c2.anchor = GridBagConstraints.BASELINE;
        this.add(bearbeitPane, c2);
    }
}
