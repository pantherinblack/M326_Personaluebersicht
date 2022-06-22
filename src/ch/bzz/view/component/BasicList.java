package ch.bzz.view.component;

import ch.bzz.facade.DataListModel;
import ch.bzz.facade.MainFacade;
import ch.bzz.facade.ParticipationListModel;
import ch.bzz.facade.PersonNameListModel;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.util.ColorCodes;
import ch.bzz.view.dialog.CreateBaseData;
import ch.bzz.view.dialog.EditPerson;
import layout.TableLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Arrays;

import static ch.bzz.util.ColorCodes.*;

/**
 * Creates a list with data, defined by the mode
 * @author Kevin
 * @since 19.06.2022
 * @version 1.6
 */
public class BasicList extends JPanel {
    public static final int MODE_PERSON = 0;
    public static final int MODE_PERSON_EDIT = 1;
    public static final int MODE_BASE_DEPARTMENT = 2;
    public static final int MODE_BASE_FUNCTION = 3;
    public static final int MODE_BASE_TEAM = 4;
    public static final int MODE_PERSON_FUNCTION = 5;
    public static final int MODE_PERSON_TEAM = 6;
    private int mode;
    private String uuid;
    private JFrame owner;
    private JList<String> list = new JList<>();
    private JLabel topTitle = new JLabel("Title");
    private JLabel sideTitle = new JLabel("Title");
    private JScrollPane scrollPane;

    private JPanel buttonPanel = new JPanel();
    private JButton addButton = new JButton("+");
    private JButton removeButton = new JButton("X");
    private JButton editButton = new JButton("C");

    /**
     * creates the object
     * @param owner (JFrame) of the panel
     * @param mode of the Object (Constants)
     * @param uuid of the person to display data of, not always needed
     */
    public BasicList(JFrame owner, int mode, String uuid) {
        this.uuid = uuid;
        this.owner = owner;
        this.mode = mode;
        scrollPane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        initLayout();
    }

    /**
     * inits the gui and all subcomponents
     */
    private void initLayout() {

        double[][] order = {{-3, -1}, {-3, -1, -3}};

        setLayout(new TableLayout(order));
        add(topTitle, "0, 0, 1, 0");
        add(sideTitle, "0, 1");
        add(scrollPane, "1, 1");

        buttonPanel.setBorder(BorderFactory.createLineBorder(BACKGROUND, 3));
        buttonPanel.setLayout(new GridLayout(1,3,10,10));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        addButton.setBorder(COMPONENT_BORDER);
        removeButton.setBorder(COMPONENT_BORDER);
        editButton.setBorder(COMPONENT_BORDER);

        sideTitle.setVerticalAlignment(SwingConstants.TOP);
        sideTitle.setMinimumSize(new Dimension(80, 0));

        topTitle.setFont(new Font(null, Font.PLAIN, 16));
        topTitle.setBackground(Color.LIGHT_GRAY);
        topTitle.setOpaque(true);

        scrollPane.setBorder(LIGHT_BORDER);

        list.setSelectionBackground(EXTRA_LIGHT_GRAY);
        list.setSelectionForeground(DARK_RED);
        list.setForeground(DARK_RED);

        setBackground(BACKGROUND);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String titleName = "";

        switch (mode) {
            case 0:
                setTopTitleVisible(true);
                setSideTitleVisible(false);
                setButtonVisible(false);
                list.setModel(new PersonNameListModel());
                titleName = "Übersicht:";
                break;
            case 1:
                setTopTitleVisible(true);
                setSideTitleVisible(false);
                setButtonVisible(true);
                list.setModel(new PersonNameListModel());
                titleName = "Übersicht:";
                break;
            case 2:
                setTopTitleVisible(false);
                setSideTitleVisible(true);
                setButtonVisible(true);
                list.setModel(new DataListModel(DataListModel.MODE_DEPARTMENT));
                titleName = "Department:";
                break;
            case 3:
                setTopTitleVisible(false);
                setSideTitleVisible(true);
                setButtonVisible(true);
                list.setModel(new DataListModel(DataListModel.MODE_FUNCTION));
                titleName = "Funktion:";
                break;
            case 4:
                setTopTitleVisible(false);
                setSideTitleVisible(true);
                setButtonVisible(true);
                list.setModel(new DataListModel(DataListModel.MODE_TEAM));
                titleName = "Team:";
                break;
            case 5:
                setTopTitleVisible(true);
                setSideTitleVisible(false);
                setButtonVisible(false);
                list.setModel(new ParticipationListModel(uuid, ParticipationListModel.MODE_FUNCTION));
                titleName = "Funktion:";
                break;
            case 6:
                setTopTitleVisible(true);
                setSideTitleVisible(false);
                setButtonVisible(false);
                list.setModel(new ParticipationListModel(uuid, ParticipationListModel.MODE_TEAM));
                titleName = "Team:";
                break;
        }
        topTitle.setText(titleName);
        sideTitle.setText(titleName);

        addButton.addActionListener(new AddButtonActionListener());
        removeButton.addActionListener(new RemoveButtonActionListener());
        editButton.addActionListener(new EditButtonActionListener());

        list.setForeground(DARK_RED);
        list.setSelectedIndex(0);

    }

    /**
     * returns the index of selectted position the list
     * @return index
     */
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }

    /**
     * adds a SelectionListener
     * @param listener to be added
     */
    public void addListSelectionListener(ListSelectionListener listener) {
        list.addListSelectionListener(listener);
    }

    /**
     * sets visibility of the buttons
     * @param visible boolean
     */
    public void setButtonVisible(boolean visible) {
        if (visible) {
            add(buttonPanel, "1, 2");
        }
    }

    /**
     * sets visibility of the side title
     * @param visible boolean
     */
    private void setSideTitleVisible(boolean visible) {
        sideTitle.setVisible(visible);
        if (visible)
            sideTitle.setMinimumSize(new Dimension(80, 0));
        else
            sideTitle.setMinimumSize(new Dimension(0, 0));
    }

    /**
     * sets visibility of the top title
     * @param visible boolean
     */
    private void setTopTitleVisible(boolean visible) {
        topTitle.setVisible(visible);
    }

    /**
     * gets the uuid of a person in the list
     * @param index of the person
     * @return uuid
     */
    public String getUuid(int index) {
        return ((PersonNameListModel)list.getModel()).getUuid(index);
    }

    /**
     * changes the uuid
     * @param uuid of the person
     */
    public void changeUuid(String uuid) {
        this.uuid = uuid;
        initLayout();
    }




    /**
     * sets the filter for further use
     * @param name to search for
     * @param function a person needs
     * @param department a person needs
     * @param team a person need
     * @param sort, "A-Z", "Z-A", else wont be sorted
     */
    public void setFilter(String name, String function, String department, String team, String sort) {
        ((PersonNameListModel)list.getModel()).setFilter(name,function,department,team,sort);
    }

    /**
     * listens, if the add button is pressed
     * @author Kevin
     * @since 21.06.2022
     * @version 1.0
     */
    public class AddButtonActionListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                switch (mode) {
                    case 1:
                        new EditPerson(owner, EditPerson.MODE_CREATE, null);
                        break;
                    case 2:
                        new CreateBaseData(owner, CreateBaseData.FUNCTION_CREATE, CreateBaseData.MODE_DEPARTMENT, "");
                        break;
                    case 3:
                        new CreateBaseData(owner, CreateBaseData.FUNCTION_CREATE, CreateBaseData.MODE_FUNCTION, "");
                        break;
                    case 4:
                        new CreateBaseData(owner, CreateBaseData.FUNCTION_CREATE, CreateBaseData.MODE_TEAM, "");
                        break;
                }
            } catch (Exception | Error throwable) {
                JOptionPane.showMessageDialog(owner, "An Error occurred during deleting\n"+throwable.getMessage());
            }
        }
    }

    /**
     * listens, if the edit button is pressed
     * @author Kevin
     * @since 21.06.2022
     * @version 1.0
     */
    public class EditButtonActionListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedValue()!=null) {
                try {
                    switch (mode) {
                        case 1:
                            new EditPerson(owner, EditPerson.MODE_EDIT, MainFacade.getInstance().getPerson(list.getSelectedIndex()).getUuid());
                            break;
                        case 2:
                            new CreateBaseData(owner, CreateBaseData.FUNCTION_EDIT, CreateBaseData.MODE_DEPARTMENT, list.getSelectedValue());
                            break;
                        case 3:
                            new CreateBaseData(owner, CreateBaseData.FUNCTION_EDIT, CreateBaseData.MODE_FUNCTION, list.getSelectedValue());
                            break;
                        case 4:
                            new CreateBaseData(owner, CreateBaseData.FUNCTION_EDIT, CreateBaseData.MODE_TEAM, list.getSelectedValue());
                            break;
                    }
                } catch (Exception | Error throwable) {
                    JOptionPane.showMessageDialog(owner, "An Error occurred during deleting\n" + throwable.getMessage());
                }
            }
        }
    }

    /**
     * listens, if the remove button is pressed
     * @author Kevin
     * @since 21.06.2022
     * @version 1.0
     */
    public class RemoveButtonActionListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (list.getSelectedValue()!=null) {
                try {
                    switch (mode) {
                        case 1:
                            MainFacade.getInstance().removePerson(((PersonNameListModel) list.getModel()).getUuid(list.getSelectedIndex()));
                            break;
                        case 2:
                            MainFacade.getInstance().removeDepartment(list.getSelectedIndex());
                            break;
                        case 3:
                            MainFacade.getInstance().removeFunction(list.getSelectedIndex());
                            break;
                        case 4:
                            MainFacade.getInstance().removeTeam(list.getSelectedIndex());
                            break;
                    }
                } catch (Exception | Error throwable) {
                    JOptionPane.showMessageDialog(owner, "An Error occurred during deleting\n" + throwable.getMessage());
                }
            }
        }
    }
}
