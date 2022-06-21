package gui2;

import javax.swing.*;

/**
 * @author Morris Idahosa
 * @version 1.0
 * @date    21.06.2022
 */

public class Logbuch extends JPanel {

    private JPanel logbuch;
    private JScrollPane jScrollPane;

    public Logbuch() {

        logbuch = new JPanel();
        jScrollPane = new JScrollPane();

        logbuch.add(jScrollPane);

        setSize(500, 500);
        setVisible(true);
    }

}
