package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Vector;

/**
 * @author Marko Joksimovic
 * @version 1.0
 * @date
 */
public class Startseite extends JFrame {

    JTabbedPane tabbedPane;
    JPanel background;

    private Vector<String> daten;

    public Startseite() {
    }

    public Startseite(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        background = new JPanel(new BorderLayout());
        tabbedPane.addTab("Übersicht", new Uebersicht());
        tabbedPane.addTab("Zuordnung", new Zuordnung());
        tabbedPane.addTab("Personen", new Personen());
        tabbedPane.addTab("Stammdaten", new Stammdaten());
        background.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(background, BorderLayout.CENTER);


        setResizable(true);
        setVisible(true);
        pack();

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 1) {
                }
            }
        });
    }


    /**
     * method to create a list with all workers
     * @param pane panel with all components inside, is returned and saved in a variable
     * @param title title is saved in a lable that is used as a title for the list
     * @param scrollPane scrollpanel in which list with workers is added
     * @param width preferred width of the whole panel
     * @param height preferred height of the whole panel
     * @return
     */
    public JPanel createList(JPanel pane, String title, JScrollPane scrollPane, int width, int height) {
        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel(title);
        daten = new Vector<>();
        for (int i = 0; i < 10; i++) {
            daten.add("Text");
        }
        JList<String> liste = new JList<>(daten);
        scrollPane = new JScrollPane(liste);
        pane.add(label);
        pane.add(scrollPane);
        pane.setPreferredSize(new Dimension(width, height));
        return pane;
    }

    /**
     * Methode erstellt ein Panel der Stammdaten-View
     * @param titel titel-Parameter wird für das Label benutzt
     * @param component component-Parameter wird zurückgegeben und in einer Variable gespeichert
     * @return
     */
    public JPanel createPanel(String titel, JPanel component) {
        GridBagConstraints c = new GridBagConstraints();
        JPanel scrollButton = new JPanel(new GridBagLayout());

        JLabel label = new JLabel(titel);
        label.setPreferredSize(new Dimension(90, 20));

        Icon plus = new ImageIcon("src/images/plus.png");
        Icon kreuz = new ImageIcon("src/images/kreuz.png");
        Icon stift = new ImageIcon("src/images/stift.png");
        JButton hinzufuegen = new JButton(plus);
        hinzufuegen.setPreferredSize(new Dimension(40, 32));
        JButton loeschen = new JButton(kreuz);
        loeschen.setPreferredSize(new Dimension(40, 32));
        JButton bearbeiten = new JButton(stift);
        bearbeiten.setPreferredSize(new Dimension(40, 32));

        Vector<String> daten = new Vector<>();
        for (int i = 0; i < 10; i++) {
            daten.add("Text");
        }
        JList<String> liste = new JList<>(daten);
        JScrollPane scrollPane =  new JScrollPane(liste);
        scrollPane.setPreferredSize(new Dimension(120,100));

        c.gridx = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.ABOVE_BASELINE;
        scrollButton.add(scrollPane, c);

        c.gridwidth = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.BASELINE;
        scrollButton.add(hinzufuegen, c);

        c.gridx = 1;
        scrollButton.add(loeschen, c);

        c.gridx = 2;
        scrollButton.add(bearbeiten, c);

        component = new JPanel(new GridBagLayout());

        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        component.add(label, c);
        c.gridx = 1;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        component.add(scrollButton, c);
        return component;
    }
}