package ch.bzz.view;

import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.view.dialog.Authentication;
import ch.bzz.view.tab.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * The MainFrame has all Gui components inside.
 * @author Kevin
 * @since 20.06.2022
 */
public class MainFrame extends JFrame {
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private final MainFrame self;
    private boolean isUser = true;

    /**
     * Creates the MainFrame and sets the content.
     */
    public MainFrame() {
        self = this;
        add(tabbedPane);

        setIconImage(new ImageIcon("images/img.png").getImage());

        tabbedPane.addTab("Übersicht", new OverviewPanel(self));
        tabbedPane.addTab("Zuordnung", new AssignPanel(self));
        tabbedPane.addTab("Personen", new PersonPanel(self));
        tabbedPane.addTab("Stammdaten", new BaseDataPanel(self));
        tabbedPane.addTab("Logbuch", new LogBookPanel());

        tabbedPane.addChangeListener(new MainFrameTabChangeListener());

        setTitle("I am looking for");
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
            if (MainFacade.getInstance().isLoggedIn()!=1) {
                if (isUser) {
                    int tab = tabbedPane.getSelectedIndex();
                    if (tabbedPane.getSelectedIndex() == 3) {
                        changeTab(0);
                        new Authentication(self, HRPerson.MODE_ADMIN, tab);
                    } else if (tabbedPane.getSelectedIndex() > 0 && MainFacade.getInstance().isLoggedIn()!=0) {
                        changeTab(0);
                        new Authentication(self, HRPerson.MODE_NORMAL, tab);

                    }
                }
            }
            self.pack();
        }
    }
}
