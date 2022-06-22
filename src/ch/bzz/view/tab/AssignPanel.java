package ch.bzz.view.tab;

import ch.bzz.facade.DataListModel;
import ch.bzz.facade.MainFacade;
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

public class AssignPanel extends JPanel {
    private boolean input = true;
    private int counter = 0;
    private final JFrame owner;
    private final JPanel content = new JPanel();
    private final JPanel detailPanel = new JPanel();
    private final BasicList basicList;
    private final BasicPerson basicPerson;
    private final JPanel dataPanel = new JPanel();
    private final JLabel departmentLabel = new JLabel("Abteilung:");
    private final JComboBox<String> departmentField = new JComboBox<>();
    private final JLabel functionLabel = new JLabel("Funktion:");
    private final SwitchList functionSwitch;
    private final JLabel teamLabel = new JLabel("Teams:");
    private final SwitchList teamSwitch;

    /**
     * inits the AssignPanel and all its subcomponents
     *
     * @param owner (JFrame)
     */
    public AssignPanel(JFrame owner) {
        this.owner = owner;
        double[][] order1 = {{-1, -3}, {-3}};
        double[][] order2 = {{-2}, {-2, -1}};
        double[][] order3 = {{-3, -1}, {-2, -2, -1, -2}};
        basicList = new BasicList(owner, 0, null);
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
        departmentLabel.setMinimumSize(new Dimension(80, 0));
        functionLabel.setMinimumSize(new Dimension(80, 0));
        teamLabel.setMinimumSize(new Dimension(80, 0));

        departmentField.addItemListener(new DepartmenItemListener());

        departmentField.setForeground(ColorCodes.DARK_RED);
        basicList.setPreferredSize(new Dimension(150, 500));
    }

    /**
     * manages updates in the data
     */
    private void update() {
        functionSwitch.fireChanges(MainFacade.getInstance().getPerson(
                basicList.getSelectedIndex()).getUuid());

        teamSwitch.fireChanges(MainFacade.getInstance().getPerson(
                basicList.getSelectedIndex()).getUuid());

        departmentField.setSelectedIndex(MainFacade.getInstance().getIndexOfDepartment(
                MainFacade.getInstance().getDepartmentByUuid(
                        MainFacade.getInstance().getPerson(
                                basicList.getSelectedIndex()).getUuid()).getName()));
        basicPerson.fireChanges(MainFacade.getInstance().getPerson(
                basicList.getSelectedIndex()).getUuid());
    }

    /**
     * Registers, when the selected index of the list changes
     *
     * @author Kevin
     * @version 1.0
     * @since 21.06.2022
     */
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
                if (counter % 2 == 0) {
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
