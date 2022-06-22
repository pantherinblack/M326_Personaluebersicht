package ch.bzz.view.tab;

import ch.bzz.facade.MainFacade;
import ch.bzz.interfaces.ModelListener;
import ch.bzz.log.LogBook;

import javax.swing.*;
import java.awt.*;

/**
 * @author Kevin
 * @version 1.0
 * @since 19.06.2022
 */
public class LogBookPanel extends JPanel implements ModelListener {
    private final JScrollPane scrollPane;
    private final JTextArea textArea = new JTextArea();

    /**
     * Constructor inits variables, adds the Listener and loads the data
     */
    public LogBookPanel() {
        scrollPane = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        MainFacade.getInstance().addModelListener(this);
        readAllLogs();

        textArea.setEditable(false);

        setLayout(new BorderLayout());
        add(scrollPane);

        scrollPane.setPreferredSize(new Dimension(450, 600));
    }

    /**
     * Removes the listener form the mainfacade
     */
    public void remove() {
        MainFacade.getInstance().removeModelListener(this);
    }

    public void readAllLogs() {
        String log = "";
        for (int i = 0; i < LogBook.getLogBookInstance().getSize(); i++) {
            log += LogBook.getLogBookInstance().getEntry(i) + "\n";
        }
        textArea.setText(log);
    }

    /**
     * updates elements
     *
     * @param source the <code>ListModel</code> that changed, typically "this"
     * @param index0 one end of the new interval
     * @param index1 the other end of the new interval
     */
    @Override
    public void fireContentsChanged(Object source, int index0, int index1) {
        readAllLogs();
    }
}
