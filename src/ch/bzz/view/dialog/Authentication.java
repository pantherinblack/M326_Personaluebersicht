package ch.bzz.view.dialog;

import ch.bzz.facade.HRPersonBoxModel;
import ch.bzz.facade.MainFacade;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.view.MainFrame;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ch.bzz.util.ColorCodes.*;

/**
 * Dialog for authorising by logging in
 *
 * @author Kevin
 * @version 1.4
 * @since 19.06.2022
 */
public class Authentication extends JDialog {
    private static int tries = 0;
    private final int mode;
    private final int tab;
    private final MainFrame owner;
    private final JPanel content = new JPanel();
    private final JLabel nameLabel = new JLabel("Name:");
    private final JLabel codeLabel = new JLabel("Code: ");
    private final JComboBox nameField = new JComboBox();
    private final JPasswordField codeField = new JPasswordField();
    private final JPanel buttonPanel = new JPanel();
    private final JButton cancelButton = new JButton("Abbrechen");
    private final JButton continueButton = new JButton("Weiter");
    private final JPanel nameLabelPanel = new JPanel();
    private final JPanel codeLabelPanel = new JPanel();
    private final JPanel nameFieldPanel = new JPanel();
    private final JPanel codeFieldPanel = new JPanel();
    private final Authentication self;

    /**
     * Inits the Dialog and all subcomponents
     *
     * @param owner (JFRAME) of the dialog
     * @param mode  mode of the Object (Constants)
     * @param tab   to change to, if successful
     */
    public Authentication(MainFrame owner, int mode, int tab) {
        super(owner, true);

        this.mode = mode;
        this.owner = owner;
        this.tab = tab;

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        self = this;

        setTitle("Authentifizierung");

        double[][] order = {{-3, -1}, {-3, -3, -3}};

        add(content);
        setIconImage(new ImageIcon("images/img.png").getImage());

        content.setLayout(new TableLayout(order));

        content.add(nameLabelPanel, "0, 0");
        content.add(codeLabelPanel, "0, 1");
        content.add(nameFieldPanel, "1, 0");
        content.add(codeFieldPanel, "1, 1");
        content.add(buttonPanel, "1, 2");

        nameLabelPanel.add(nameLabel, BorderLayout.WEST);
        codeLabelPanel.add(codeLabel, BorderLayout.WEST);
        nameFieldPanel.add(nameField, BorderLayout.EAST);
        codeFieldPanel.add(codeField, BorderLayout.EAST);

        nameLabelPanel.setLayout(new GridLayout());
        codeLabelPanel.setLayout(new GridLayout());
        nameFieldPanel.setLayout(new GridLayout());
        codeFieldPanel.setLayout(new GridLayout());

        nameLabelPanel.setBorder(COMPONENT_BORDER);
        codeLabelPanel.setBorder(COMPONENT_BORDER);
        nameFieldPanel.setBorder(COMPONENT_BORDER);
        codeFieldPanel.setBorder(COMPONENT_BORDER);

        buttonPanel.setBorder(COMPONENT_BORDER);

        nameLabel.setMinimumSize(new Dimension(80, 0));
        codeLabel.setMinimumSize(new Dimension(80, 0));

        buttonPanel.setLayout(new BorderLayout(10, 10));
        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(continueButton, BorderLayout.EAST);

        content.setBorder(FRAME_BORDER);

        continueButton.addActionListener(new AuthenticationContinueActionListener());
        cancelButton.addActionListener(new AuthenticationCancelActionListener());

        nameField.setModel(new HRPersonBoxModel());
        nameField.setSelectedIndex(0);
        nameField.setForeground(DARK_RED);
        codeField.setForeground(DARK_RED);

        pack();
        setVisible(true);
    }

    /**
     * Ends the Dialog
     */
    public void end() {
        dispose();
    }

    /**
     * Is being executed if the cancel button is being clicked
     *
     * @author Kevin
     * @version 1.0
     * @since 19.06.2022
     */
    public class AuthenticationCancelActionListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            owner.changeTab(0);
            end();
        }
    }

    /**
     * Is being executed if the continue button is being clicked
     *
     * @author Kevin
     * @version 1.0
     * @since 19.06.2022
     */
    public class AuthenticationContinueActionListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tries < 3) {
                HRPersonBoxModel model = (HRPersonBoxModel) nameField.getModel();
                if (!model.getPasswordAt(nameField.getSelectedIndex()).equals(String.valueOf(codeField.getPassword())) ||
                        model.getModeAt(nameField.getSelectedIndex()) < mode) {
                    tries++;
                    JOptionPane.showMessageDialog(self, "Login failed try another combination.");
                    if (tries >= 3) {
                        JOptionPane.showMessageDialog(self, "You tried to many times to login.");
                        owner.changeTab(0);
                        end();
                    }
                } else {
                    MainFacade.getInstance().setHrPerson(
                            (HRPerson) MainFacade.getInstance().getPersonByUuid(
                                    model.getUuidAt(nameField.getSelectedIndex())));
                    owner.changeTab(tab);
                    end();
                }
            } else {
                JOptionPane.showMessageDialog(self, "You tried to many times to login.");
                owner.changeTab(0);
                end();
            }
        }
    }
}
