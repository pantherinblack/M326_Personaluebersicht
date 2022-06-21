package ch.bzz.view.tab;

import ch.bzz.facade.DataListModel;
import ch.bzz.facade.MainFacade;
import ch.bzz.facade.ParticipationListModel;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.util.ColorCodes;
import ch.bzz.view.component.BasicList;
import ch.bzz.view.component.BasicPerson;
import ch.bzz.view.component.SwitchList;
import layout.TableLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.file.Paths;
import java.util.Arrays;

public class AssignPanel extends JPanel {
    private boolean input = true;
    private int counter = 0;
    private JFrame owner;
    private JPanel content = new JPanel();
    private JPanel detailPanel = new JPanel();
    private BasicList basicList;
    private BasicPerson basicPerson;
    private JPanel dataPanel = new JPanel();
    private JLabel departmentLabel = new JLabel("Abteilung:");
    private JComboBox<String> departmentField = new JComboBox<>();
    private JLabel functionLabel = new JLabel("Funktion:");
    private SwitchList functionSwitch;
    private JLabel teamLabel = new JLabel("Teams:");
    private SwitchList teamSwitch;

    public AssignPanel(JFrame owner) {
        this.owner = owner;
        double[][] order1 = {{-1,-3},{-3}};
        double[][] order2 = {{-2},{-2,-1}};
        double[][] order3 = {{-3,-1},{-2,-2,-1,-2}};
        basicList = new BasicList(owner, 0);
        basicList.addListSelectionListener(new MainListSelectionListener());
        basicPerson = new BasicPerson(BasicPerson.MODE_SHOW,
                MainFacade.getInstance().getPerson(0).getUuid());
        add(content);
        functionSwitch = new SwitchList(MainFacade.getInstance().getPerson(
                0).getUuid(), SwitchList.MODE_FUNCTION);
        teamSwitch = new SwitchList(MainFacade.getInstance().getPerson(
                0).getUuid(), SwitchList.MODE_TEAM);


        content.setBorder(new TitledBorder("Personen bearbeiten: "));
        content.setLayout(new TableLayout(order1));
        content.add(basicList, "0, 0");
        content.add(detailPanel, "1, 0");

        dataPanel.setBorder(ColorCodes.FRAME_BORDER);

        detailPanel.setBorder(new TitledBorder("Detail:"));
        detailPanel.setLayout(new TableLayout(order2));
        detailPanel.add(basicPerson, "0, 0");
        detailPanel.add(dataPanel, "0, 1");

        detailPanel.setMinimumSize(new Dimension(300, 600));

        dataPanel.setLayout(new TableLayout(order3));
        dataPanel.add(departmentLabel, "0, 0");
        dataPanel.add(departmentField, "1, 0");
        dataPanel.add(functionSwitch, "0, 1, 1, 1");
        dataPanel.add(teamSwitch, "0, 3, 1, 3");

        departmentField.setModel(new DataListModel(DataListModel.MODE_DEPARTMENT));
        departmentField.setSelectedIndex(MainFacade.getInstance().getIndexOfDepartment(
                MainFacade.getInstance().getDepartmentByUuid(
                        MainFacade.getInstance().getPerson(0).getUuid()).getName()));

        departmentField.setEditable(false);
        departmentLabel.setMinimumSize(new Dimension(80,0));
        functionLabel.setMinimumSize(new Dimension(80,0));
        teamLabel.setMinimumSize(new Dimension(80,0));

        departmentField.addItemListener(new DepartmenItemListener());
    }

    private void update() {
        functionSwitch.fireChanges(MainFacade.getInstance().getPerson(
                basicList.getSelectedIndex()).getUuid());

        teamSwitch.fireChanges(MainFacade.getInstance().getPerson(
                basicList.getSelectedIndex()).getUuid());

        departmentField.setSelectedIndex(MainFacade.getInstance().getIndexOfDepartment(
                MainFacade.getInstance().getDepartmentByUuid(
                        MainFacade.getInstance().getPerson(
                                basicList.getSelectedIndex()).getUuid()).getName()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setVisible(true);
        MainFacade mF = MainFacade.getInstance();
        mF.setCompany(new Company("test"));
        for (String s : Arrays.asList("TestFunction1", "TestFunction2", "TestFunction3", "TestFunction4")) {
            mF.addFunction(s);
        }
        for (String s1 : Arrays.asList("TestDepartment1", "TestDepartment2", "TestDepartment3")) {
            mF.addDepartment(new Department(s1));
        }
        for (String s : Arrays.asList("TestTeam1", "TestTeam2", "TestTeam3")) {
            mF.addTeam(s);
        }
        for (int i = 0; i < 20; i++) {
            mF.createPerson("Niklas", "Vogel", Paths.get("test.jpg"), "TestDepartment1");
            mF.addFunctionAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction1");
            mF.addTeamAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam1");
        }
        frame.add(new AssignPanel(frame));
        frame.pack();
    }

    public class MainListSelectionListener implements ListSelectionListener {

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            update();
        }
    }

    public class DepartmenItemListener implements ItemListener {

        /**
         * Invoked when an item has been selected or deselected by the user.
         * The code written for this method performs the operations
         * that need to occur when an item is selected (or deselected).
         *
         * @param e the event to be processed
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (input) {
                counter++;
                if (counter%2==0) {
                    input = false;
                    if (basicList.getSelectedIndex() != -1) {
                        MainFacade.getInstance().changeDepartmentByUuid(
                                MainFacade.getInstance().getPerson(basicList.getSelectedIndex()).getUuid(),
                                departmentField.getItemAt(departmentField.getSelectedIndex()));
                    } else {
                        MainFacade.getInstance().changeDepartmentByUuid(
                                MainFacade.getInstance().getPerson(0).getUuid(),
                                departmentField.getItemAt(departmentField.getSelectedIndex()));
                    }
                    input = true;
                }
            }
        }
    }
}
