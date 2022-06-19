package ch.bzz.gui.component;

import ch.bzz.facade.MainFacade;
import ch.bzz.interfaces.ViewListener;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static ch.bzz.util.ColorCodes.*;

public class BasicPerson extends JPanel implements ViewListener {
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameField = new JTextField("Unknown");
    private JLabel photo = new JLabel();

    public BasicPerson(String uuid) {
        this();
        fireChanges(uuid);
    }
    public BasicPerson() {
        initLayout();
        fireChanges(null);
        nameField.setEditable(false);
        nameLabel.setMinimumSize(new Dimension(80,0));
    }

    private void initLayout() {
        double[][] order = {{-3,-1},{-3,-1}};

        this.setLayout(new TableLayout(order));

        this.add(nameLabel, "0, 0, 0, 0");
        this.add(nameField, "1, 0, 1, 0");
        this.add(photo, "1, 1, 1, 1");

        nameLabel.setBorder(FRAME_BORDER);
        nameField.setBorder(FRAME_BORDER);
        photo.setBorder(FRAME_BORDER);
        this.setBorder(FRAME_BORDER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new BasicPerson());
        frame.setVisible(true);
        frame.pack();
    }

    public void fireChanges(String uuid) {
        if (uuid != null && !uuid.isEmpty()) {
            nameField.setText(MainFacade.getInstance().getFullNameByUuid(uuid));
            Path picture = MainFacade.getInstance().getPhotoByUuid(uuid);
            if (picture == null || !Files.exists(picture))
                photo.setIcon(new ImageIcon(new ImageIcon("images/img.png").getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
            else
                photo.setIcon(new ImageIcon(new ImageIcon(picture.toString()).getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
        } else {
            photo.setIcon(new ImageIcon(new ImageIcon("images/img.png").getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
        }
    }
}
