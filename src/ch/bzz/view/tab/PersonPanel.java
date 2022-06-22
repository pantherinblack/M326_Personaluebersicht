package ch.bzz.view.tab;

import ch.bzz.facade.MainFacade;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.view.component.BasicList;
import ch.bzz.view.component.BasicPerson;
import layout.TableLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * PersonPanel is here to add, remove or change People.
 * @author Kevin
 * @since 21.06.2022
 * @version 1.1
 */
public class PersonPanel extends JPanel {
    private JPanel content = new JPanel();
    private BasicList basicList;
    private JPanel detailPanel = new JPanel();
    private BasicPerson basicPerson;
    private JPanel checkBoxPanel = new JPanel();

    private JLabel hrLabel = new JLabel("HR-Mitarbeiter:");
    private JLabel adminLabel = new JLabel("Administrator:");
    private JCheckBox hrCheckBox = new JCheckBox();
    private JCheckBox adminCheckBox = new JCheckBox();

    /**
     * Inits the PersonPanel and all components
     * @param owner (JFrame) of the Panel
     */
    public PersonPanel(JFrame owner) {
        basicList = new BasicList(owner, BasicList.MODE_PERSON_EDIT, null);
        basicPerson = new BasicPerson(BasicPerson.MODE_SHOW, MainFacade.getInstance().getPerson(0).getUuid());

        double[][] order1 = {{-2, -2}, {-2}};
        double[][] order2 = {{-2}, {-2, -2}};
        double[][] order3 = {{-2, -2, -2}, {-2, -2}};


        add(content);
        content.setBorder(new TitledBorder("Person bearbeiten"));
        content.setLayout(new TableLayout(order1));
        content.add(basicList, "0, 0");
        content.add(detailPanel, "1, 0");
        detailPanel.setBorder(new TitledBorder("Detail:"));
        detailPanel.setLayout(new TableLayout(order2));
        detailPanel.add(basicPerson, "0, 0");
        detailPanel.add(checkBoxPanel, "0, 1");

        checkBoxPanel.setLayout(new TableLayout(order3));
        checkBoxPanel.add(hrLabel, "0, 0");
        checkBoxPanel.add(adminLabel, "0, 1");
        checkBoxPanel.add(hrCheckBox, "1, 0");
        checkBoxPanel.add(adminCheckBox, "1, 1");

        basicList.addListSelectionListener(new PersonListListener());

        hrCheckBox.setEnabled(false);
        adminCheckBox.setEnabled(false);

        basicList.setPreferredSize(new Dimension(150, 600));
        detailPanel.setPreferredSize(new Dimension(300, 600));
        update();
    }

    /**
     * handels data-changes
     */
    public void update() {
        hrCheckBox.setSelected(false);
        adminCheckBox.setSelected(false);
        basicPerson.fireChanges(MainFacade.getInstance().getPerson(basicList.getSelectedIndex()).getUuid());
        if (MainFacade.getInstance().getPerson(basicList.getSelectedIndex()) instanceof HRPerson) {
            HRPerson hr = (HRPerson) MainFacade.getInstance().getPerson(basicList.getSelectedIndex());
            hrCheckBox.setSelected(true);
            adminCheckBox.setSelected(hr.getModus() == 1);
        }
    }

    /**
     * Listener for the list
     * @author Kevin
     * @since 21.06.2022
     */
    public class PersonListListener implements ListSelectionListener {

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
}
