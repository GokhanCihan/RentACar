import business.UserManager;
import core.DBConnection;
import views.AdminView;
import views.LoginView;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();
        //LoginView loginView = new LoginView();
        UserManager userManager = new UserManager();
        AdminView adminView = new AdminView(userManager.login("admin","admin"));
    }
}
