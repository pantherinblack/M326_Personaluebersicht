package ch.bzz.view.component;

import ch.bzz.facade.MainFacade;
import ch.bzz.interfaces.ViewListener;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ch.bzz.util.ColorCodes.FRAME_BORDER;

public class BasicPerson extends JPanel implements ViewListener {
    public static final int MODE_SHOW = 0;
    public static final int MODE_EDIT = 1;
    public BasicPerson self;
    private int mode;
    private Path photoPath = Paths.get("images/img.png");
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameField = new JTextField("Unknown");
    private JLabel photo = new JLabel();
    private JLabel smallPhoto = new JLabel();

    public BasicPerson(int mode, String uuid) {
        this(mode);
        fireChanges(uuid);
    }

    public BasicPerson(int mode) {
        this.self = this;
        this.mode = mode;
        if (mode == 0) {
            smallPhoto.setVisible(false);
            nameField.setEditable(false);
        }
        initLayout();
        fireChanges(null);
        nameLabel.setMinimumSize(new Dimension(80, 0));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new BasicPerson(BasicPerson.MODE_SHOW));
        frame.setVisible(true);
        frame.pack();
    }

    public String getName() {
        return nameField.getText();
    }

    public String getPhoto() {
        return null;
    }

    private void initLayout() {
        double[][] order = {{-3, -2, -3}, {-3, -1, -3,-3}};

        setLayout(new TableLayout(order));

        add(nameLabel, "0, 0");
        add(nameField, "1, 0, 2, 0");
        add(photo, "1, 1, 1, 2");
        add(smallPhoto, "2, 2");

        smallPhoto.setIcon(new ImageIcon(new ImageIcon("images/img.png").getImage().getScaledInstance(20, 20, Image.SCALE_FAST)));
        setBorder(FRAME_BORDER);
        smallPhoto.addMouseListener(new SmallPhotoListener());
    }

    public Path getPhotoPath() {
        return photoPath;
    }

    public void fireChanges(String uuid) {
        if (uuid != null && !uuid.isEmpty()) {
            nameField.setText(MainFacade.getInstance().getFullNameByUuid(uuid));
            Path picture = MainFacade.getInstance().getPhotoByUuid(uuid);
            if (picture == null || !Files.exists(picture))
                photo.setIcon(new ImageIcon(new ImageIcon(photoPath.toString()).getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
            else
                photo.setIcon(new ImageIcon(new ImageIcon(picture.toString()).getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
        } else {
            photo.setIcon(new ImageIcon(new ImageIcon(photoPath.toString()).getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
        }
    }

    public class SmallPhotoListener extends MouseAdapter {

        /**
         * Invoked when the mouse button has been clicked (pressed
         * and released) on a component.
         *
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("test");
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (fileChooser.showOpenDialog(self) == JFileChooser.APPROVE_OPTION)
                photoPath = fileChooser.getSelectedFile().toPath();
            System.out.println(photoPath);
            fireChanges(null);
        }
    }
}
