package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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

    private Vector<String> daten;

    public Startseite() {
    }

    public Startseite(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Übersicht", new Uebersicht());
        getContentPane().add(tabbedPane);

        setSize(new Dimension(500, 700));
        setResizable(true);
        setVisible(true);
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