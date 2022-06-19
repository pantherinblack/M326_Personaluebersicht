package ch.bzz.view.component;

import ch.bzz.facade.DataListModel;
import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import layout.TableLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Arrays;

import static ch.bzz.util.ColorCodes.*;

public class BasicList extends JPanel {
    private String usage;
    private String arg;
    private JList<String> list = new JList<>();
    private JLabel topTitle = new JLabel("Title");
    private JLabel sideTitle = new JLabel("Title");
    private JScrollPane scrollPane;

    private JPanel buttonPanel = new JPanel();
    private JButton addButton = new JButton("+");
    private JButton removeButton = new JButton("X");
    private JButton editButton = new JButton("C");

    public BasicList(String usage, String arg) {
        this.usage = usage;
        this.arg = arg;

        scrollPane = new JScrollPane(list,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        initLayout();
    }

    private void initLayout() {

        double[][] order = {{-3, -1}, {-3, -1, -3}};

        this.setLayout(new TableLayout(order));
        this.add(topTitle, "0, 0, 1, 0");
        this.add(sideTitle, "0, 1");
        this.add(buttonPanel, "1, 2");
        this.add(scrollPane, "1, 1");

        buttonPanel.setBorder(BorderFactory.createLineBorder(BACKGROUND, 3));
        buttonPanel.setLayout(new GridLayout(1,3,10,10));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        addButton.setBorder(LIGHT_BORDER);
        removeButton.setBorder(LIGHT_BORDER);
        editButton.setBorder(LIGHT_BORDER);
        topTitle.setBorder(LIGHT_BORDER);

        sideTitle.setVerticalAlignment(SwingConstants.TOP);
        sideTitle.setMinimumSize(new Dimension(80, 0));

        topTitle.setFont(new Font(null, Font.PLAIN, 16));
        topTitle.setBackground(Color.LIGHT_GRAY);
        topTitle.setOpaque(true);

        scrollPane.setBorder(LIGHT_BORDER);

        list.setModel(new DataListModel(DataListModel.MODE_DEPARTMENT));
        list.setSelectionBackground(EXTRA_LIGHT_GRAY);
        list.setSelectionForeground(DARK_RED);
        list.setForeground(DARK_RED);

        setBackground(BACKGROUND);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void addListSelectionListener(ListSelectionListener listener) {
        list.addListSelectionListener(listener);
    }

    public void removeListSelectionListener(ListSelectionListener listener) {
        list.removeListSelectionListener(listener);
    }

    public void setButtonVisible(boolean visible) {
        buttonPanel.setVisible(visible);
    }

    public void setListSelectionMode(int listSelectionMode) {
        list.setSelectionMode(listSelectionMode);
    }

    public void setSideTitleVisible(boolean visible) {
        sideTitle.setVisible(visible);
        if (visible)
            sideTitle.setMinimumSize(new Dimension(80, 0));
        else
            sideTitle.setMinimumSize(new Dimension(0, 0));
    }

    public void setTopTitleVisible(boolean visible) {
        topTitle.setVisible(visible);
    }

    public void setModel(ListModel<String> listModel) {
        list.setModel(listModel);
    }

    public ListModel getModel(ListModel<String> listModel) {
        return list.getModel();
    }

    public void addButtonAddActionListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void addButtonRemoveActionListener(ActionListener actionListener) {
        addButton.removeActionListener(actionListener);
    }

    public void removeButtonAddActionListener(ActionListener actionListener) {
        removeButton.addActionListener(actionListener);
    }

    public void removeButtonRemoveActionListener(ActionListener actionListener) {
        removeButton.removeActionListener(actionListener);
    }

    public void editButtonAddActionListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    public void editButtonRemoveActionListener(ActionListener actionListener) {
        editButton.removeActionListener(actionListener);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new BasicList("", ""));
        frame.setVisible(true);
        MainFacade mF = MainFacade.getInstance();
        mF.setCompany(new Company("test"));
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
        }
        frame.pack();
    }
}
