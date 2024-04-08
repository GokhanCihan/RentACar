package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static boolean isEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isEmptyAny(JTextField[] fields) {
        for (JTextField field: fields) {
            if (isEmpty(field)){
                return true;
            }
        }
        return false;
    }



    public static Point getCenter(Dimension size) {
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
        return new Point(x, y);
    }

    public static void showDialog(String type) {
        String message = null;
        String title = null;

        switch (type) {
            case "emptyField" -> {
                message = "Please fill all necessary fields to log in!";
                title = "Caution";
            }
            case "success" -> {
                message = "Operation completed!";
                title = "Successful";
            }
            case "notFound" -> {
                message = "This item could not be found!";
                title = "Not Found!";
            }
            case "error" -> {
                message = "Operation failed!";
                title = "Error!";
            }
        }
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmDelete() {
        return JOptionPane.showConfirmDialog(
                null,
                "You are about to delete this item for good. Do you want to proceed?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION
        ) == 0;

    }
}
