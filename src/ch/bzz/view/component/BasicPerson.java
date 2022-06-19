package ch.bzz.view.component;

import ch.bzz.facade.MainFacade;
import ch.bzz.interfaces.ViewListener;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.nio.file.Files;
import java.nio.file.Path;
import static ch.bzz.util.ColorCodes.*;

public class BasicPerson extends JPanel implements ViewListener {
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameField = new JTextField("Unknown");
    private JLabel photo = new JLabel();
    private JLabel smallPhoto = new JLabel();
    private boolean addPhoto;

    public BasicPerson(String uuid, boolean addPhoto) {
        this(addPhoto);
        fireChanges(uuid);
    }
    public BasicPerson(boolean addPhoto) {
        this.addPhoto = addPhoto;
        initLayout();
        fireChanges(null);
        nameField.setEditable(false);
        nameLabel.setMinimumSize(new Dimension(80,0));
    }

    private void initLayout() {
        double[][] order = {{-3,-1,-3},{-3,-1,-3}};

        setLayout(new TableLayout(order));

        smallPhoto.setIcon(new ImageIcon(new ImageIcon("images/img.png").getImage().getScaledInstance(20, 20, Image.SCALE_FAST)));

        add(nameLabel, "0, 0, 0, 0");
        add(nameField, "1, 0, 1, 0");
        add(photo, "1, 1, 1, 2");
        if (addPhoto) {
            add(smallPhoto, "2, 2, 2, 2");
        }

        nameLabel.setBorder(FRAME_BORDER);
        nameField.setBorder(FRAME_BORDER);
        photo.setBorder(FRAME_BORDER);
        smallPhoto.setBorder(FRAME_BORDER);
        setBorder(FRAME_BORDER);
    }

    public void setPictureActionListener(MouseListener mouseListener) {
        smallPhoto.addMouseListener(mouseListener);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new BasicPerson(true));
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
