package ch.bzz.view.tab;

import ch.bzz.facade.MainFacade;
import ch.bzz.interfaces.ViewListener;
import ch.bzz.log.LogBook;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kevin
 * @since 19.06.2022
 * @version 1.0
 */
public class LogBookPanel extends JPanel implements ViewListener {
    private JScrollPane scrollPane;
    private JTextArea textArea = new JTextArea();

    /**
     * Constructor inits variables, adds the Listener and loads the data
     */
    public LogBookPanel() {
        scrollPane = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        MainFacade.getInstance().addViewListener(this);
        readAllLogs();

        textArea.setEditable(false);

        setLayout(new GridLayout(1,1));
        add(scrollPane);
    }

    /**
     * Removes the listener form the mainfacade
     */
    public void remove() {
        MainFacade.getInstance().removeViewListener(this);
    }

    public void readAllLogs() {
        String log = "test";
        for (int i = 0; i < LogBook.getLogBookInstance().getSize(); i++) {
            log += LogBook.getLogBookInstance().getEntry(i);
        }
        textArea.setText(log);
    }

    /**
     * acts, when something changes
     * @param uuid ignored
     */
    @Override
    public void fireChanges(String uuid) {
        readAllLogs();
    }
}
