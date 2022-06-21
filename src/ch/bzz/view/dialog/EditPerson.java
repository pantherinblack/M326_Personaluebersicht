package ch.bzz.view.dialog;

import ch.bzz.exception.NotExistentException;
import ch.bzz.facade.DataListModel;
import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.util.ColorCodes;
import ch.bzz.view.component.BasicPerson;
import layout.TableLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Arrays;

import static ch.bzz.util.ColorCodes.COMPONENT_BORDER;

public class EditPerson extends JDialog {
    public static final int MODE_CREATE = 0;
    public static final int MODE_EDIT = 1;
    private EditPerson self;
    private String uuid;
    private int mode;
    private BasicPerson personInfo;
    private JPanel checkBoxPanel = new JPanel();
    private JLabel hrLabel = new JLabel("HR-Mitarbeiter:");
    private JLabel adminLabel = new JLabel("Administrator:");
    private JCheckBox hrCheckBox = new JCheckBox();
    private JCheckBox adminCheckBox = new JCheckBox();
    private JPanel buttonPanel = new JPanel();
    private JButton cancelButton = new JButton("Abbrechen");
    private JButton saveButton = new JButton("Speichern");
    private JLabel departmentLabel = new JLabel("Department");
    private JComboBox<String> departmentBox = new JComboBox<>();
    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordField = new JPasswordField();


    public EditPerson(JFrame owner, int mode, String uuid) throws NotExistentException {
        super(owner, true);
        this.self = this;
        this.mode = mode;
        this.uuid = uuid;
        double[][] order = {{-1, -2}, {-1, -2, -2}};
        double[][] order2 = {{-2, -1}, {-2, -2, -2, -2}};
        double[][] order3 = {{-2, -2}, {-2}};
        setLayout(new TableLayout(order));

        checkBoxPanel.setLayout(new TableLayout(order2));
        checkBoxPanel.add(hrLabel, "0, 0");
        checkBoxPanel.add(adminLabel, "0, 1");
        checkBoxPanel.add(hrCheckBox, "1, 0");
        checkBoxPanel.add(adminCheckBox, "1, 1");
        checkBoxPanel.add(passwordLabel, "0, 2");
        checkBoxPanel.add(passwordField, "1, 2");
        checkBoxPanel.add(departmentLabel, "0, 3");
        checkBoxPanel.add(departmentBox, "1, 3");

        hrLabel.setBorder(COMPONENT_BORDER);
        adminLabel.setBorder(COMPONENT_BORDER);
        hrCheckBox.setBorder(COMPONENT_BORDER);
        adminCheckBox.setBorder(COMPONENT_BORDER);
        passwordLabel.setBorder(COMPONENT_BORDER);
        passwordField.setBorder(COMPONENT_BORDER);
        departmentLabel.setBorder(COMPONENT_BORDER);
        departmentBox.setBorder(COMPONENT_BORDER);

        buttonPanel.setLayout(new TableLayout(order3));
        buttonPanel.add(cancelButton, "0, 0");
        buttonPanel.add(saveButton, "1, 0");

        cancelButton.addActionListener(new CancelListener());
        saveButton.addActionListener(new SaveListener());

        hrCheckBox.addActionListener(new HRCheckBoxListener());


        departmentBox.setModel(new DataListModel(DataListModel.MODE_DEPARTMENT));
        departmentBox.setSelectedIndex(0);


        if (mode == 0) {
            setTitle("Person Erfassen");
            personInfo = new BasicPerson(BasicPerson.MODE_EDIT);

        } else if (mode == 1) {
            setTitle("Person Bearbeiten");
            personInfo = new BasicPerson(BasicPerson.MODE_EDIT, uuid);

        } else {
            throw new NotExistentException();
        }
        add(personInfo, "0, 0, 1, 0");
        add(checkBoxPanel, "0, 1, 1, 1");
        add(buttonPanel, "1, 2, 1, 2");


        adminCheckBox.setSelected(false);
        adminCheckBox.setEnabled(false);
        adminCheckBox.setVisible(false);
        adminLabel.setVisible(false);
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);

        passwordField.setForeground(ColorCodes.DARK_RED);


        hrCheckBox.setSelected(false);
        adminCheckBox.setSelected(false);

        if (uuid != null && !uuid.isEmpty()) {
            if (MainFacade.getInstance().getPersonByUuid(uuid) instanceof HRPerson) {
                int personMode = ((HRPerson)MainFacade.getInstance().getPersonByUuid(uuid)).getModus();
                hrCheckBox.setSelected(true);
                adminCheckBox.setSelected(personMode==1);
                updateCheckBoxes();
            }

            departmentBox.setSelectedIndex(
                    MainFacade.getInstance().getIndexOfDepartment(
                            MainFacade.getInstance().getDepartmentByUuid(uuid).getName()));
        }


        setResizable(false);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
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
        new EditPerson(null,0, "");


    }

    public void updateCheckBoxes() {
        if (!hrCheckBox.isSelected()) {
            adminCheckBox.setSelected(false);
            adminCheckBox.setEnabled(false);
            adminCheckBox.setVisible(false);
            adminLabel.setVisible(false);
            passwordLabel.setVisible(false);
            passwordField.setVisible(false);
        } else {
            adminCheckBox.setEnabled(true);
            adminCheckBox.setVisible(true);
            adminLabel.setVisible(true);
            passwordLabel.setVisible(true);
            passwordField.setVisible(true);
        }
    }

    public class HRCheckBoxListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            updateCheckBoxes();
        }
    }

    public class CancelListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            self.dispose();
        }
    }

    public class SaveListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            MainFacade mF = MainFacade.getInstance();

            if (mode == 0) {
                try {
                    mF.createPerson(personInfo.getName().split(" ")[0], personInfo.getName().split(" ")[1], personInfo.getPhotoPath(), departmentBox.getItemAt(departmentBox.getSelectedIndex()));
                    if (hrCheckBox.isSelected()) {
                        if (adminCheckBox.isSelected()) {
                            mF.changeToHR(mF.getAllPeople().get(mF.getAllPeople().size() - 1).getUuid(), HRPerson.MODE_ADMIN, String.valueOf(passwordField.getPassword()));
                        } else {
                            mF.changeToHR(mF.getAllPeople().get(mF.getAllPeople().size() - 1).getUuid(), HRPerson.MODE_NORMAL, String.valueOf(passwordField.getPassword()));
                        }
                    }
                    self.dispose();
                } catch (Error | Exception exception) {
                    JOptionPane.showMessageDialog(self, "An Error occurred creating this Person.\n" + exception.getMessage());
                }
            } else if (mode == 1) {
                try {
                    mF.setFullNameByUuid(uuid, personInfo.getName());
                    if (personInfo.getPhoto() != null)
                        mF.setPhotoByUuid(uuid, Paths.get(personInfo.getPhoto()));
                    mF.changeDepartmentByUuid(uuid, departmentBox.getItemAt(departmentBox.getSelectedIndex()));

                    if (hrCheckBox.isSelected()) {
                        if (adminCheckBox.isSelected()) {
                            mF.changeToHR(uuid, HRPerson.MODE_ADMIN, String.valueOf(passwordField.getPassword()));
                        } else {
                            mF.changeToHR(uuid, HRPerson.MODE_NORMAL, String.valueOf(passwordField.getPassword()));
                        }
                    }
                    self.dispose();
                } catch (Error | Exception exception) {
                    JOptionPane.showMessageDialog(self, "An Error occurred creating this Person.\n" + exception.getMessage());
                }
            }

        }
    }
}
