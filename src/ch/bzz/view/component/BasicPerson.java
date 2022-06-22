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

/**
 * shows basic information from a person
 *
 * @author Kevin
 * @version 1.3
 * @since 21.06.2022
 */
public class BasicPerson extends JPanel implements ViewListener {
    public static final int MODE_SHOW = 0;
    public static final int MODE_EDIT = 1;
    public BasicPerson self;
    private String uuid;
    private final int mode;
    private Path photoPath = Paths.get(ConfigReader.readConfig("picturePath"));
    private final JLabel nameLabel = new JLabel("Name: ");
    private final JTextField nameField = new JTextField("");
    private final JLabel photo = new JLabel();
    private final JLabel smallPhoto = new JLabel();

    /**
     * creates the object
     *
     * @param mode to be used (Constants)
     * @param uuid of the person
     */
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


    /**
     * gives back the name, written in by the use
     *
     * @return name
     */
    public String getName() {
        return nameField.getText();
    }

    /**
     * gives back the path of the photo as a string
     *
     * @return string-path
     */
    public String getPhoto() {
        return photoPath.toString();
    }

    /**
     * inits the layout and all subcomponents
     */
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

    /**
     * gives back the photoPath as Path
     *
     * @return path
     */
    public Path getPhotoPath() {
        return photoPath;
    }

    /**
     * acts, the person displayed changes
     *
     * @param uuid of the person
     */
    public void fireChanges(String uuid) {
        if (uuid != null && !this.uuid.equals(uuid)) {
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
