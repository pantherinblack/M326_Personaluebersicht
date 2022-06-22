package ch.bzz.view.dialog;

import ch.bzz.facade.MainFacade;
import ch.bzz.util.ColorCodes;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ch.bzz.util.ColorCodes.*;

/**
 * Dialog for creating BaseData
 * @author Kevin
 * @since 21.06.2022
 * @version 1.2
 */
public class CreateBaseData extends JDialog {
    public static final int FUNCTION_CREATE = 0;
    public static final int FUNCTION_EDIT = 1;
    public static final int MODE_DEPARTMENT = 0;
    public static final int MODE_FUNCTION = 1;
    public static final int MODE_TEAM = 2;
    private int function;
    private int mode;
    private JFrame owner;
    private String oldName;
    private JPanel container = new JPanel();
    private JLabel title = new JLabel("Title");
    private JTextField input = new JTextField();
    private JButton cancelButton = new JButton("Abbrechen");
    private JButton saveButton = new JButton("Speichern");
    private JPanel cancelButtonPanel = new JPanel();
    private JPanel saveButtonPanel = new JPanel();

    /**
     * creates the gui
     * @param owner JFrame) of the Dialog
     * @param function function of the Object (Constants)
     * @param mode mode of the Object (Constants)
     * @param oldName old name, if in editing function
     */
    public CreateBaseData(JFrame owner, int function, int mode, String oldName) {
        super(owner, true);
        this.function = function;
        this.mode = mode;
        this.oldName = oldName;
        this.owner = owner;
        input.setText(oldName);
        init();
    }


    /**
     * inits the gui and all subcomponents
     */
    private void init() {
        double[][] order = {{-3,-1,-3,-2,-2},{-1,-2,-2}};
        add(container);

        container.setLayout(new TableLayout(order));
        container.add(title, "0, 0");
        container.add(input, "1, 0, 4, 0");
        container.add(cancelButtonPanel, "3, 2");
        container.add(saveButtonPanel, "4, 2");

        cancelButtonPanel.add(cancelButton);
        saveButtonPanel.add(saveButton);

        container.setBorder(FRAME_BORDER);
        cancelButtonPanel.setBorder(COMPONENT_BORDER);
        saveButtonPanel.setBorder(COMPONENT_BORDER);

        setResizable(false);

        title.setVerticalTextPosition(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setMinimumSize(new Dimension(80, 0));

        cancelButton.addActionListener(new CancelButtonListener());
        saveButton.addActionListener(new SaveButtonListener());

        input.setForeground(DARK_RED);

        switch (mode) {
            case 0:
                title.setText("Department: ");
                break;
            case 1:
                title.setText("Funktion: ");
                break;
            case 2:
                title.setText("Team: ");
                break;
        }
        switch (function) {
            case 0:
                setTitle("Funktion erfassen");
                break;
            case 1:
                setTitle("Funktion bearbeiten");
        }

        pack();
        setVisible(true);
    }

    public void dispose() {
        super.dispose();
    }

    /**
     * listens, if the cancel button ist klicked
     * @author Kevin
     * @since 20.06.2022
     * @version 1.0
     */
    public class CancelButtonListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
     * listens, if the save button ist klicked
     * @author Kevin
     * @since 20.06.2022
     * @version 1.0
     */
    public class SaveButtonListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!input.getText().isEmpty()) {
                MainFacade mF = MainFacade.getInstance();
                try {

                    switch (function) {
                        case 0:
                            switch (mode) {
                                case 0:
                                    mF.addDepartment(input.getText());
                                    dispose();
                                    break;
                                case 1:
                                    mF.addFunction(input.getText());
                                    dispose();
                                    break;
                                case 2:
                                    mF.addTeam(input.getText());
                                    dispose();
                                    break;
                            }
                            break;
                        case 1:
                            switch (mode) {
                                case 0:
                                    mF.changeDepartmentName(oldName, input.getText());
                                    dispose();
                                    break;
                                case 1:
                                    mF.changeFunctionName(oldName, input.getText());
                                    dispose();
                                    break;
                                case 2:
                                    mF.changeTeamName(oldName, input.getText());
                                    dispose();
                                    break;
                            }
                            break;
                    }
                } catch (Exception | Error throwable) {
                JOptionPane.showMessageDialog(owner, "An Error occurred during deleting\n" + throwable.getMessage());
            }
            }
        }
    }
}
