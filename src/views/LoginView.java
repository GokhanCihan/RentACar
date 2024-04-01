package views;

import core.Helper;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel container;
    private JPanel container_top;
    private JLabel label_logo;
    private JLabel label_logo_2;
    private JPanel container_bottom;
    private JLabel label_username;
    private JTextField field_username;
    private JLabel label_password;
    private JTextField field_password;
    private JButton button_login;

    public LoginView() {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(400, 600);
        this.setLocation(Helper.getCenter(this.getSize()));
        this.setVisible(true);

        button_login.addActionListener(e -> {
            if (Helper.isEmpty(this.field_username) || Helper.isEmpty(this.field_password)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please fill all necessary fields to log in!",
                        "Caution",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
