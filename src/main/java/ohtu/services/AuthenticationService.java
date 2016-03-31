package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (checkLogIn(username, password, user)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLogIn(String username, String password, User user) {
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (invalid(username, password)) {
            return false;
        }
        userDao.add(new User(username, password));
        return true;
    }

    private boolean invalid(String username, String password) {
        if (userAndPassword(username, password)) {
            return true;
        }
        if (eiKirjainta(password,0)) {
            return true;
        }
        return false;
    }

    private boolean userAndPassword(String username, String password) {
        return username.matches("[a-z]+") == false && username.length() < 3 && password.length() < 8;
    }

    private boolean eiKirjainta(String salasana, Integer luku) {
        for (int i = 0; i < salasana.length(); i++) {
            luku = check(salasana, luku, i);
        }
        if (luku == 0) {
            return true;
        } else {
            return false;
        }
    }

    private Integer check(String salasana, Integer luku, Integer i) {
        if (Character.isDigit(salasana.charAt(i)) == true || Character.isLetter(salasana.charAt(i)) == false) {
            luku++;
        }
        return luku;
    }

}
