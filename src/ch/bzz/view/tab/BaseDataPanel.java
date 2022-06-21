package ch.bzz.view.tab;

import ch.bzz.facade.MainFacade;
import ch.bzz.view.component.BasicList;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BaseDataPanel extends JPanel {
    private JFrame owner;
    private JPanel content = new JPanel();
    public JLabel companyLabel = new JLabel("Firma:");
    public JTextField companyField = new JTextField();
    public BasicList departmentList;
    public BasicList functionList;
    public BasicList teamList;

    public BaseDataPanel(JFrame owner) {
        this.owner = owner;
        departmentList = new BasicList(owner, BasicList.MODE_BASE_DEPARTMENT);
        functionList = new BasicList(owner, BasicList.MODE_BASE_FUNCTION);
        teamList = new BasicList(owner, BasicList.MODE_BASE_TEAM);
        companyField.setText(MainFacade.getInstance().getCompanyName());
        companyLabel.setMinimumSize(new Dimension(80,0));
        double[][] order = {{-3,-1},{-3,-2,-2,-2}};

        add(content);

        content.setLayout(new TableLayout(order));
        content.add(companyLabel, "0, 0");
        content.add(companyField, "1, 0");
        content.add(departmentList, "0, 1, 1, 1");
        content.add(functionList, "0, 2, 1, 2");
        content.add(teamList, "0, 3, 1, 3");

        departmentList.setMinimumSize(new Dimension(100,0));
        functionList.setMinimumSize(new Dimension(100,0));
        teamList.setMinimumSize(new Dimension(100,0));

        companyField.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyReleased(KeyEvent e) {
                MainFacade.getInstance().setCompanyName(companyField.getText());
            }
        });
    }
}
