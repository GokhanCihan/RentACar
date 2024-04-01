package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static boolean isEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static Point getCenter(Dimension size) {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
        return new Point(x, y);
    }
}
