package ch.bzz.view.component;

import ch.bzz.facade.MainFacade;
import ch.bzz.facade.SwitchListModel;
import ch.bzz.interfaces.ViewListener;
import ch.bzz.util.ColorCodes;
import layout.TableLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Generates 2 Lists, where Data can be switched from one to the other side by klicking
 *
 * @author Kevin
 * @version 1.3
 * @since 21.06.2022
 */
public class SwitchList extends JPanel implements ViewListener {
    public static final int MODE_FUNCTION = 0;
    public static final int MODE_TEAM = 1;
    private final int mode;
    private final JLabel title = new JLabel();
    private final JLabel leftTitle = new JLabel();
    private final JLabel rightTitle = new JLabel();
    private final JList<String> leftList = new JList<>();
    private final JList<String> rightList = new JList<>();
    private boolean input = true;
    private String uuid;
    private JScrollPane leftScrollPane;
    private JScrollPane rightScrollPane;

    /**
     * creates the SwichList
     *
     * @param uuid ot the person tho show data from
     * @param mode of the gui (Constants)
     */
    public SwitchList(String uuid, int mode) {
        this.uuid = uuid;
        this.mode = mode;

        init();
    }

    /**
     * inits the dui and all subcomponents
     */
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

    /**
     * manages updates in the gui
     */
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
     * changes values
     *
     * @param uuid of the person
     */
    @Override
    public void fireChanges(String uuid) {
        this.uuid = uuid;
        update();
    }

    /**
     * Listens, if the left side is being klicked
     *
     * @author Kevin
     * @version 1.2
     * @since 21.06.2022
     */
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

    /**
     * Listens, if the right side is being klicked
     *
     * @author Kevin
     * @version 1.2
     * @since 21.06.2022
     */
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
            if (input && rightList.getSelectedIndex() != -1) {
                input = false;
                ((SwitchListModel) leftList.getModel()).addElement(rightList.getSelectedValue());
                rightList.clearSelection();
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
            if (input && leftList.getSelectedIndex() != -1) {
                input = false;
                ((SwitchListModel) leftList.getModel()).removeElement(leftList.getSelectedValue());
                leftList.clearSelection();
                leftList.setSelectedIndex(-1);
            }
            input = true;
        }
    }
}
