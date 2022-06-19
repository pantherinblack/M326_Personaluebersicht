package ch.bzz.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ColorCodes {
    public static final Color DARK_RED = new Color(180,0,0);
    public static final Color EXTRA_LIGHT_GRAY = new Color(230,230,230);
    public static final Color BACKGROUND = Color.WHITE;
    public static final Border FRAME_BORDER = BorderFactory.createLineBorder(UIManager.getColor("Panel.background"), 6);
    public static final Border LIGHT_BORDER = BorderFactory.createLineBorder(Color.GRAY, 1);
    public static final Border COMPONENT_BORDER = BorderFactory.createLineBorder(UIManager.getColor("Panel.background"), 3);
}
