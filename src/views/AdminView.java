package views;

import entities.User;

import javax.swing.*;

public class AdminView extends ViewLayout {
    private JPanel container;
    private JLabel label_welcome;
    private JPanel container_welcome;
    private JTabbedPane tabbedPane1;
    private JButton button_logout;
    private JPanel pane_brand;
    private JScrollPane spane_brand;
    private JTable table_brand;
    private final User user;

    public AdminView(User user) {
        this.add(container);
        this.layoutView(1000, 1000);
        this.user = user;
        if (this.user == null){
            dispose();
        }
        this.label_welcome.setText("Welcome " + this.user.getUsername() + "!");
    }
}
