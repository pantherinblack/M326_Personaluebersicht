package ch.bzz.view.component;

import ch.bzz.facade.MainFacade;
import ch.bzz.interfaces.ViewListener;
import ch.bzz.util.ColorCodes;
import ch.bzz.util.ConfigReader;
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
    private String uuid;
    private int mode;
    private Path photoPath = Paths.get(ConfigReader.readConfig("picturePath"));
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameField = new JTextField("");
    private JLabel photo = new JLabel();
    private JLabel smallPhoto = new JLabel();

    public BasicPerson(int mode, String uuid) {
        this.self = this;
        this.mode = mode;
        this.uuid = uuid;
        if (mode == 0) {
            smallPhoto.setVisible(false);
            nameField.setEditable(false);
        }
        nameLabel.setMinimumSize(new Dimension(80, 0));
        initLayout();
        fireChanges(uuid);
    }

    public BasicPerson(int mode) {
        this(mode, null);
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
        return photoPath.toString();
    }

    private void initLayout() {
        double[][] order = {{-3, -2, -3}, {-3, -1, -3, -3}};
        photoPath = Paths.get(ConfigReader.readConfig("picturePath"));

        setLayout(new TableLayout(order));

        add(nameLabel, "0, 0");
        add(nameField, "1, 0, 2, 0");
        add(photo, "1, 1, 1, 2");
        add(smallPhoto, "2, 2");

        if (uuid != null && !uuid.isEmpty()) {
            if (MainFacade.getInstance().getPhotoByUuid(uuid) != null)
                photoPath = MainFacade.getInstance().getPhotoByUuid(uuid);
            nameField.setText(MainFacade.getInstance().getFullNameByUuid(uuid));
        }

        smallPhoto.setIcon(new ImageIcon(new ImageIcon(ConfigReader.readConfig("picturePath")).getImage().getScaledInstance(20, 20, Image.SCALE_FAST)));
        setBorder(FRAME_BORDER);
        smallPhoto.addMouseListener(new SmallPhotoListener());

        nameField.setForeground(ColorCodes.DARK_RED);


    }

    public Path getPhotoPath() {
        return photoPath;
    }

    public void fireChanges(String uuid) {
        if (!this.uuid.equals(uuid)) {
            photoPath = Paths.get(ConfigReader.readConfig("picturePath"));
            this.uuid = uuid;
        }


        if (uuid != null && !uuid.isEmpty()) {
            nameField.setText(MainFacade.getInstance().getFullNameByUuid(uuid));
            Path picture = MainFacade.getInstance().getPhotoByUuid(uuid);
            if (picture == null || !Files.exists(picture))
                photo.setIcon(new ImageIcon(new ImageIcon(photoPath.toString()).getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
            else {
                photo.setIcon(new ImageIcon(new ImageIcon(picture.toString()).getImage().getScaledInstance(100, 100, Image.SCALE_FAST)));
                photoPath = picture;
            }


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
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (fileChooser.showOpenDialog(self) == JFileChooser.APPROVE_OPTION)
                photoPath = fileChooser.getSelectedFile().toPath();
            MainFacade.getInstance().setPhotoByUuid(uuid, photoPath);
            fireChanges(null);
        }
    }
}
