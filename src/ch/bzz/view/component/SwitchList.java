package ch.bzz.view.component;

import ch.bzz.facade.MainFacade;
import ch.bzz.facade.SwitchListModel;
import ch.bzz.interfaces.ViewListener;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.util.ColorCodes;
import layout.TableLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Paths;
import java.util.Arrays;

public class SwitchList extends JPanel implements ViewListener {
    public static final int MODE_FUNCTION = 0;
    public static final int MODE_TEAM = 1;
    private boolean input = true;
    private String uuid;
    private int mode;
    private JLabel title = new JLabel();
    private JLabel leftTitle = new JLabel();
    ;
    private JLabel rightTitle = new JLabel();

    private JScrollPane leftScrollPane;
    private JScrollPane rightScrollPane;

    private JList<String> leftList = new JList<>();
    private JList<String> rightList = new JList<>();

    public SwitchList(String uuid, int mode) {
        this.uuid = uuid;
        this.mode = mode;

        init();
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
        for (int i = 0; i < 2; i++) {
            mF.createPerson("Niklas", "Vogel", Paths.get("test.jpg"), "TestDepartment1");
            mF.addFunctionAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction1");
            mF.addTeamAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam1");
        }
        frame.add(new SwitchList(mF.getPerson(0).getUuid(), SwitchList.MODE_TEAM));
        frame.pack();
    }

    private void init() {
        leftTitle.setText(MainFacade.getInstance().getFullNameByUuid(uuid));
        rightTitle.setText("Übrige");

        switch (mode) {
            case 0:
                title.setText("Funktion");
                break;
            case 1:
                title.setText("Team");
                break;
        }

        leftScrollPane = new JScrollPane(leftList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightScrollPane = new JScrollPane(rightList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        leftList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        rightList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        double[][] order = {{-3, -3}, {-2, -2, -2}};
        setLayout(new TableLayout(order));
        add(title, "0, 0, 1, 0");
        add(leftTitle, "0, 1");
        add(rightTitle, "1, 1");
        add(leftScrollPane, "0, 2");
        add(rightScrollPane, "1, 2");
        update();


        rightList.addMouseListener(new RightListMouseListener());
        leftList.addMouseListener(new LeftListMouseListener());

        leftTitle.setMinimumSize(new Dimension(120, 0));
        rightTitle.setMinimumSize(new Dimension(120, 0));
        rightList.setMinimumSize(new Dimension(120, 0));
        leftList.setMinimumSize(new Dimension(120, 0));

        leftList.setForeground(ColorCodes.DARK_RED);
        rightList.setForeground(ColorCodes.DARK_RED);
    }

    private void update() {
        //Sets or updates the model
        if (leftList.getModel() instanceof SwitchListModel) {
            ((SwitchListModel) leftList.getModel()).setUuid(uuid);
        } else {
            leftList.setModel(new SwitchListModel(SwitchListModel.SIDE_LEFT, mode, uuid));
        }

        if (rightList.getModel() instanceof SwitchListModel) {
            ((SwitchListModel) rightList.getModel()).setUuid(uuid);
        } else {
            rightList.setModel(new SwitchListModel(SwitchListModel.SIDE_RIGHT, mode, uuid));
        }
    }

        /**
         * @param uuid
         */
        @Override
        public void fireChanges (String uuid){
            this.uuid = uuid;
            update();
        }

        public class LeftListSelectionListener implements ListSelectionListener {

            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (input && !leftList.isSelectedIndex(-1)) {
                    input = false;
                    ((SwitchListModel) leftList.getModel()).removeElement(leftList.getSelectedValue());

                    leftList.setSelectedIndex(-1);
                    rightList.setSelectedIndex(-1);

                }
                input = true;
            }
        }

        public class RightListSelectionListener implements ListSelectionListener {

            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (input && !rightList.isSelectedIndex(-1)) {
                    input = false;
                    ((SwitchListModel) leftList.getModel()).addElement(rightList.getSelectedValue());
                    rightList.setSelectedIndex(-1);
                    leftList.setSelectedIndex(-1);
                }
                input = true;
            }
        }

        public class RightListMouseListener extends MouseAdapter {
            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (input) {
                    input = false;
                    ((SwitchListModel) leftList.getModel()).addElement(rightList.getSelectedValue());
                    rightList.setSelectedIndex(-1);
                }
                input = true;
            }
        }

    public class LeftListMouseListener extends MouseAdapter {
        /**
         * Invoked when a mouse button has been released on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (input) {
                input = false;
                ((SwitchListModel) leftList.getModel()).removeElement(leftList.getSelectedValue());
                leftList.setSelectedIndex(-1);
            }
            input = true;
        }
    }
    }
