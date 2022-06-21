package ch.bzz.view;

import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.view.dialog.Authentication;
import ch.bzz.view.tab.AssignPanel;
import ch.bzz.view.tab.BaseDataPanel;
import ch.bzz.view.tab.LogBookPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.nio.file.Paths;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private final MainFrame self;
    private boolean isUser = true;

    public MainFrame() {
        self = this;
        add(tabbedPane);

        setIconImage(new ImageIcon("images/img.png").getImage());

        tabbedPane.addTab("Übersicht", new JLabel("Test"));
        tabbedPane.addTab("Zuordnung", new AssignPanel(self));
        tabbedPane.addTab("Personen", new JLabel("Test"));
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
                    if (tabbedPane.getSelectedIndex() == 4) {
                        changeTab(0);
                        new Authentication(self, HRPerson.MODE_ADMIN, tab);
                    } else if (tabbedPane.getSelectedIndex() > 0 && MainFacade.getInstance().isLoggedIn()!=0) {
                        changeTab(0);
                        new Authentication(self, HRPerson.MODE_NORMAL, tab);

                    }
                }
            }
            pack();
        }
    }
    public static void main(String[] args) {

        MainFacade mF = MainFacade.getInstance();

        /*
        for (String s : Arrays.asList("TestFunction1", "TestFunction2", "TestFunction3")) {
            mF.addFunction(s);
        }
        for (String s1 : Arrays.asList("TestDepartment1", "TestDepartment2", "TestDepartment3")) {
            mF.addDepartment(new Department(s1));
        }
        for (String s : Arrays.asList("TestTeam1", "TestTeam2", "TestTeam3")) {
            mF.addTeam(s);
        }
        for (int i = 0; i < 2; i++) {
            mF.createPerson("Niklas", "Vogel", Paths.get("test.jpg"), "TestDepartment1");
            mF.addFunctionAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction1");
            mF.addTeamAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam1");
            mF.changeToHR(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), HRPerson.MODE_ADMIN, "1234");
        }

         */


        new MainFrame();
    }
}
