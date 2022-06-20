package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
}