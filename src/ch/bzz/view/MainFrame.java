package ch.bzz.view;

import ch.bzz.model.employees.HRPerson;
import ch.bzz.view.dialog.Authentication;
import ch.bzz.view.tab.LogBookPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private final MainFrame self;
    private boolean isUser = true;

    public MainFrame() {
        self = this;
        add(tabbedPane);

        setIconImage(new ImageIcon("images/img.png").getImage());

        tabbedPane.addTab("Übersicht", new JLabel("Test"));
        tabbedPane.addTab("Zuordnung", new JLabel("Test"));
        tabbedPane.addTab("Personen", new JLabel("Test"));
        tabbedPane.addTab("Stammdaten", new JLabel("Test"));
        tabbedPane.addTab("Logbuch", new LogBookPanel());

        tabbedPane.addChangeListener(new MainFrameTabChangeListener());

        setTitle("I am looking for");
        pack();
        setVisible(true);
    }

    /**
     * changes the tab to the indicated
     * @param tab to change to
     */
    public void changeTab(int tab) {
        isUser = false;
        tabbedPane.setSelectedIndex(tab);
        isUser = true;
    }

    /**
     * Check, if the Tab is changes
     * @author Kevin
     * @since 19.06.2022
     * @version 1.0
     */
    public class MainFrameTabChangeListener implements ChangeListener {

        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            if (isUser) {
                int tab = tabbedPane.getSelectedIndex();
                if (tabbedPane.getSelectedIndex() == 4) {
                    changeTab(0);
                    new Authentication(self, HRPerson.MODE_ADMIN, tab);
                } else if (tabbedPane.getSelectedIndex() > 0) {
                    changeTab(0);
                    new Authentication(self, HRPerson.MODE_NORMAL, tab);

                }
                pack();
            }
        }
    }
    public static void main(String[] args) {
        new MainFrame();
    }
}
