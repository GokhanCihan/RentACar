package business;

import dao.UserDao;
import entities.User;

public class UserManager {
    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User login(String username, String password){
        return this.userDao.login(username, password);
    }

}
