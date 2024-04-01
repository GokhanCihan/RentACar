import core.DBConnection;
import views.LoginView;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();
        LoginView loginView = new LoginView();
    }
}
