package views;

import business.UserManager;
import core.Helper;
import entities.User;

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
    private final UserManager userManager;

    public LoginView() {
        this.add(container);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(400, 600);
        this.setLocation(Helper.getCenter(this.getSize()));
        this.setVisible(true);
        this.userManager = new UserManager();

        button_login.addActionListener(e -> {
            if (Helper.isEmpty(this.field_username) || Helper.isEmpty(this.field_password)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please fill all necessary fields to log in!",
                        "Caution",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                User loggedInUser = this.userManager.login(this.field_username.getText(), this.field_password.getText());
                if(loggedInUser == null){
                    JOptionPane.showMessageDialog(
                            null,
                            "User not found!",
                            "Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }else {
                    System.out.println(loggedInUser.toString());
                }
            }
        });
    }
}
